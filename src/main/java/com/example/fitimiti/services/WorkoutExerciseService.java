package com.example.fitimiti.services;

import com.example.fitimiti.entities.Workout_exercise;
import com.example.fitimiti.repositories.WorkoutExerciseRepository;
import com.example.fitimiti.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

   private final WorkoutExerciseRepository workoutExerciseRepository;
   private final WorkoutRepository workoutRepository;

   @Autowired
   public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, WorkoutRepository workoutRepository) {
      this.workoutExerciseRepository = workoutExerciseRepository;
      this.workoutRepository = workoutRepository;
   }

   public List<Workout_exercise> getExercisesByWorkoutId(Long workoutId) {
      return workoutExerciseRepository.findByWorkoutId(workoutId);
   }

   public void addExerciseToWorkout(Long workoutId, Workout_exercise workoutExercise) {
      workoutExercise.setWorkout(workoutRepository.findById(workoutId).orElseThrow(() -> new IllegalArgumentException("Invalid workout ID")));
      workoutExercise.setId(null);
      workoutExerciseRepository.save(workoutExercise);
   }
}
