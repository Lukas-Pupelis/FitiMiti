package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}