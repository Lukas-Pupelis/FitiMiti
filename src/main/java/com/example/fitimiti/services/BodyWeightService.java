package com.example.fitimiti.services;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.repositories.BodyWeightRepository;
import com.example.fitimiti.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class BodyWeightService {
    private final BodyWeightRepository bodyWeightRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BodyWeightService(BodyWeightRepository bodyWeightRepository, MemberRepository memberRepository) {
        this.bodyWeightRepository = bodyWeightRepository;
        this.memberRepository = memberRepository;
    }
    @Transactional
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
}
