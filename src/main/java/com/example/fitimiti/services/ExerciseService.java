package com.example.fitimiti.services;

import com.example.fitimiti.entities.Exercise;
import com.example.fitimiti.entities.Member;
import com.example.fitimiti.repositories.ExerciseRepository;
import com.example.fitimiti.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

   private final ExerciseRepository exerciseRepository;
   private final MemberRepository memberRepository;

   @Autowired
   public ExerciseService(ExerciseRepository exerciseRepository, MemberRepository memberRepository) {
      this.exerciseRepository = exerciseRepository;
      this.memberRepository = memberRepository;
   }

   public List<Exercise> getExercisesByMemberId(Long memberId) {
      return exerciseRepository.findByMemberId(memberId);
   }

   public List<Exercise> getSharedExercises() {
      return exerciseRepository.findBySharedTrue();
   }

   public void saveExercise(Exercise exercise, String memberEmail) {
      Optional<Member> optionalMember = memberRepository.findByEmail(memberEmail);
      if (optionalMember.isPresent()) {
         exercise.setMember(optionalMember.get());
         exerciseRepository.save(exercise);
      } else {
         throw new IllegalArgumentException("Member not found for email: " + memberEmail);
      }
   }

   public void deleteExercise(Long exerciseId) {
      exerciseRepository.deleteById(exerciseId);
   }

   public Exercise getExerciseById(Long id) {
      return exerciseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found for id: " + id));
   }

   public void updateExercise(Exercise exercise, String memberEmail) {
      Optional<Member> optionalMember = memberRepository.findByEmail(memberEmail);
      if (optionalMember.isPresent()) {
         exercise.setMember(optionalMember.get());
         exerciseRepository.save(exercise);
      } else {
         throw new IllegalArgumentException("Member not found for email: " + memberEmail);
      }
   }
}
