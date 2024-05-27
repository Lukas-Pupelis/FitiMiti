package com.example.fitimiti.services;

import com.example.fitimiti.entities.Workout_exercise_set;
import com.example.fitimiti.repositories.WorkoutExerciseSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class WorkoutExerciseSetService {
   private final WorkoutExerciseSetRepository workoutExerciseSetRepository;

   @Autowired
   public WorkoutExerciseSetService(WorkoutExerciseSetRepository workoutExerciseSetRepository) {
      this.workoutExerciseSetRepository = workoutExerciseSetRepository;
   }

   public Workout_exercise_set save(Workout_exercise_set set) {
      return workoutExerciseSetRepository.save(set);
   }
}
