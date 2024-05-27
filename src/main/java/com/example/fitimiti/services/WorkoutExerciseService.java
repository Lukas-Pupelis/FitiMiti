package com.example.fitimiti.services;

import com.example.fitimiti.entities.Workout_exercise;
import com.example.fitimiti.repositories.WorkoutExerciseRepository;
import com.example.fitimiti.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

   public Workout_exercise findById(Long id) {
      Optional<Workout_exercise> workoutExerciseOptional = workoutExerciseRepository.findById(id);
      return workoutExerciseOptional.orElse(null);
   }

   public Workout_exercise getWorkoutExerciseById(Long id) {
      return workoutExerciseRepository.findById(id).orElse(null);
   }

   public void updateWorkoutExercise(Workout_exercise workoutExercise) {
      Workout_exercise existingWorkoutExercise = workoutExerciseRepository.findById(workoutExercise.getId())
              .orElseThrow(() -> new RuntimeException("Workout exercise not found"));

      workoutExercise.setExercise_number(existingWorkoutExercise.getExercise_number());
      workoutExercise.setWorkout(existingWorkoutExercise.getWorkout());

      workoutExerciseRepository.save(workoutExercise);
   }

   public void deleteWorkoutExercise(Long id) {
      workoutExerciseRepository.deleteById(id);
   }
}
