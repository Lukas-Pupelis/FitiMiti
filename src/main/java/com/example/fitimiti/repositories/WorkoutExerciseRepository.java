package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Workout_exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<Workout_exercise, Long> {
   List<Workout_exercise> findByWorkoutId(Long workoutId);
}
