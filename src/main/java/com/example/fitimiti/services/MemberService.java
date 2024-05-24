package com.example.fitimiti.services;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

   private final MemberRepository memberRepository;

   @Autowired
   public MemberService(MemberRepository memberRepository) {
      this.memberRepository = memberRepository;
   }

   public List<Member> getAllMembers() {
      return memberRepository.findAll();
   }

   public Member getMemberByEmail(String email) {
      Optional<Member> member = memberRepository.findByEmail(email);
      return member.orElse(null);
   }

   public void saveMember(Member member) {
      memberRepository.save(member);
   }
}
