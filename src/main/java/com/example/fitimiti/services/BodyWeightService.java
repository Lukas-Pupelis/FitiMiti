package com.example.fitimiti.services;

import com.example.fitimiti.dtos.DateWeight;
import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.repositories.BodyWeightRepository;
import com.example.fitimiti.repositories.MemberRepository;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@EnableAsync
@Service
public class BodyWeightService {
    private final BodyWeightRepository bodyWeightRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BodyWeightService(BodyWeightRepository bodyWeightRepository, MemberRepository memberRepository) {
        this.bodyWeightRepository = bodyWeightRepository;
        this.memberRepository = memberRepository;
    }

    public List<Member_weight_entry> getBodyWeightByMemberId(Long memberId, String period) {
        try {
            return bodyWeightRepository.findByMemberId(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching body weight entries for member id: " + memberId);
        }
    }

    @Transactional
    public void addWeight(String memberEmail, Member_weight_entry weightEntry) {
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new RuntimeException("Member not found"));
        weightEntry.setMember(member);
        bodyWeightRepository.save(weightEntry);
    }

    private  CompletableFuture<List<DateWeight>> selfInvocationDateWeight(Long memberId, String period){
        LocalDate startDate = Period.getStartDateForPeriod(period);
        System.out.println(startDate + " ");
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<DateWeight> dateWeights = bodyWeightRepository.findByMemberIdOrderByDateAsc(memberId);
        return CompletableFuture.completedFuture(dateWeights.stream()
                .filter(dw -> !dw.date().before(start))
                .collect(Collectors.toList()));
    }

    // Use AopContext.currentProxy() for self-invocation
    @Async
    public CompletableFuture<List<DateWeight>> getDateWeightByMemberId(Long memberId, String period) {
        return CompletableFuture.supplyAsync(() -> {
            return selfInvocationDateWeight(memberId, period).join();
        });
    }
}

