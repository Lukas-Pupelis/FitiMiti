package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
   List<Workout> findByMemberId(Long memberId);
}