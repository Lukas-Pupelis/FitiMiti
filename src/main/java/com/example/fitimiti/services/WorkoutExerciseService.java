package com.example.fitimiti.services;

import com.example.fitimiti.entities.Workout_exercise;
import com.example.fitimiti.repositories.WorkoutExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

   private final WorkoutExerciseRepository workoutExerciseRepository;

   @Autowired
   public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository) {
      this.workoutExerciseRepository = workoutExerciseRepository;
   }

   public List<Workout_exercise> getExercisesByWorkoutId(Long workoutId) {
      return workoutExerciseRepository.findByWorkoutId(workoutId);
   }

   public void addExerciseToWorkout(Workout_exercise workoutExercise) {
      workoutExerciseRepository.save(workoutExercise);
   }
}
