package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Workout_exercise_set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseSetRepository extends JpaRepository<Workout_exercise_set, Long> {
}