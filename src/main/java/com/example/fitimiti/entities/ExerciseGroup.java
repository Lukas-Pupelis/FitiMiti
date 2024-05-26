package com.example.fitimiti.entities;

import java.util.List;

public class ExerciseGroup {
   private int exerciseNumber;
   private Exercise exercise;
   private List<Workout_exercise> sets;

   public ExerciseGroup(int exerciseNumber, Exercise exercise, List<Workout_exercise> sets) {
      this.exerciseNumber = exerciseNumber;
      this.exercise = exercise;
      this.sets = sets;
   }

   public int getExerciseNumber() {
      return exerciseNumber;
   }

   public Exercise getExercise() {
      return exercise;
   }

   public List<Workout_exercise> getSets() {
      return sets;
   }
}
