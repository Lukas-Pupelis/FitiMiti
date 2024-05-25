package com.example.fitimiti.services;

import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.entities.Member;
import com.example.fitimiti.repositories.WorkoutRepository;
import com.example.fitimiti.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, MemberRepository memberRepository) {
        this.workoutRepository = workoutRepository;
        this.memberRepository = memberRepository;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }
    public List<Workout> getWorkoutsByMemberId(Long memberId) {
        return workoutRepository.findByMemberId(memberId);
    }
    public Workout addWorkout(String memberEmail, Workout workout) {
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new RuntimeException("Member not found"));
        workout.setMember(member);
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}