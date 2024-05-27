package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.*;
import com.example.fitimiti.services.*;
import jakarta.websocket.server.PathParam;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestScope
public class WorkoutController {

    private final WorkoutService workoutService;
    private final MemberService memberService;
    private final ExerciseService exerciseService;
    private final WorkoutExerciseService workoutExerciseService;
    private final WorkoutExerciseSetService workoutExerciseSetService;


    @Autowired
    public WorkoutController(WorkoutService workoutService, MemberService memberService, ExerciseService exerciseService, WorkoutExerciseService workoutExerciseService, WorkoutExerciseSetService workoutExerciseSetService) {
        this.workoutService = workoutService;
        this.memberService = memberService;
        this.exerciseService = exerciseService;
        this.workoutExerciseService = workoutExerciseService;
        this.workoutExerciseSetService = workoutExerciseSetService;
    }

    @GetMapping("/add-workout")
    public String showAddWorkoutForm(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Member member = memberService.getMemberByEmail(principal.getAttribute("email"));
        model.addAttribute("member", member);
        model.addAttribute("workout", new Workout());
        model.addAttribute("workouts", workoutService.getWorkoutsByMemberId(member.getId()));
        return "add-workout";
    }

    @PostMapping("/add-workout")
    public String addWorkout(@ModelAttribute Workout workout, BindingResult result, @AuthenticationPrincipal OAuth2User principal) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return "add-workout";
        }
        String email = principal.getAttribute("email");
        workoutService.addWorkout(email, workout);
        return "redirect:/add-workout";
    }

    @GetMapping("/workouts/edit")
    public String showEditWorkoutForm(@RequestParam Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Workout workout = workoutService.getWorkoutById(id);
        Member member = memberService.getMemberByEmail(principal.getAttribute("email"));
        model.addAttribute("member", member);
        model.addAttribute("workout", workout);
        return "edit-workout";
    }

    @PostMapping("/workouts/update")
    public String updateWorkout(@ModelAttribute Workout workout, BindingResult result, @AuthenticationPrincipal OAuth2User principal) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return "edit-workout";
        }
        String email = principal.getAttribute("email");
        workoutService.updateWorkout(email, workout);
        return "redirect:/add-workout";
    }

    @GetMapping("/workouts/delete")
    public String deleteWorkout(@RequestParam Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/add-workout";
    }

    @GetMapping("/workouts/{id}/exercises")
    public String showWorkoutExercises(@PathVariable Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Workout workout = workoutService.getWorkoutById(id);
        Member member = memberService.getMemberByEmail(principal.getAttribute("email"));
//        List<Workout_exercise> exercises = workoutExerciseService.getExercisesByWorkoutId(id);
        List<Workout_exercise> workoutExercises = workoutExerciseService.getExercisesByWorkoutId(id)
                .stream()
                .sorted(Comparator.comparingInt(Workout_exercise::getExercise_number))
                .collect(Collectors.toList());

        workoutExercises.forEach(exercise -> {
            List<Workout_exercise_set> sets = exercise.getSets();
            Collections.sort(sets, Comparator.comparingInt(Workout_exercise_set::getSet_number));
        });
        List<Exercise> sharedExercises = exerciseService.getSharedExercises();
        List<Exercise> memberExercises = exerciseService.getExercisesByMemberId(member.getId());


        model.addAttribute("workout", workout);
        model.addAttribute("workoutExercises", workoutExercises);
        model.addAttribute("workoutExercise", new Workout_exercise());
        model.addAttribute("sharedExercises", sharedExercises);
        model.addAttribute("memberExercises", memberExercises);
        return "workout-exercises";
    }

    @PostMapping("/workouts/{id}/exercises/add")
    public String addExerciseToWorkout(@PathVariable Long id, @ModelAttribute Workout_exercise workoutExercise, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return "redirect:/workouts/" + id + "/exercises";
        }
        int exerciseNumber = workoutExerciseService.getExercisesByWorkoutId(id).size() + 1;
        workoutExercise.setExercise_number(exerciseNumber);

        workoutExerciseService.addExerciseToWorkout(id, workoutExercise);
        return "redirect:/workouts/" + id + "/exercises";
    }

    @GetMapping("/workouts/exercises/addset/{workoutExerciseId}")
    public String showAddSetForm(@PathVariable Long workoutExerciseId, Model model) {
        Workout_exercise workoutExercise = workoutExerciseService.findById(workoutExerciseId);
        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("workoutExerciseSet", new Workout_exercise_set());
        return "add-set-form"; // Assuming you have an HTML template named "add-set-form.html" for the form
    }

    @PostMapping("/workouts/exercises/addset/{workoutExerciseId}")
    public String addSetToWorkoutExercise(@RequestParam Long workoutExerciseId, @RequestParam Integer reps, @RequestParam Float weight) {
        Workout_exercise workoutExercise = workoutExerciseService.findById(workoutExerciseId);
        if (workoutExercise == null) {
            // Handle error if workout exercise not found
            return "redirect:/workouts/exercises"; // Redirect to the exercise list page
        }

        Long workoutId = workoutExercise.getWorkout().getId();
        List<Workout_exercise_set> sets = workoutExercise.getSets();
        int setNumber = sets.size() + 1;

        Workout_exercise_set newSet = new Workout_exercise_set();
        newSet.setSet_number(setNumber);
        newSet.setReps(reps);
        newSet.setWeight(weight);
        newSet.setWorkout_exercise(workoutExercise);

        workoutExerciseSetService.save(newSet);

        return "redirect:/workouts/" + workoutId + "/exercises"; // Redirect to the exercise list page
    }

    @GetMapping("/workouts/exercises/edit/{exerciseId}")
    public String showEditWorkoutExerciseForm(@PathVariable Long exerciseId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Workout_exercise workoutExercise = workoutExerciseService.getWorkoutExerciseById(exerciseId);
        Member member = memberService.getMemberByEmail(principal.getAttribute("email"));
        List<Exercise> sharedExercises = exerciseService.getSharedExercises();
        List<Exercise> memberExercises = exerciseService.getExercisesByMemberId(member.getId());

        model.addAttribute("workoutExercise", workoutExercise);
        model.addAttribute("sharedExercises", sharedExercises);
        model.addAttribute("memberExercises", memberExercises);
        return "edit-workout-exercise";
    }

    @PostMapping("/workouts/exercises/update")
    public String updateWorkoutExercise(@ModelAttribute Workout_exercise workoutExercise, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
            return "edit-workout-exercise";
        }
        workoutExerciseService.updateWorkoutExercise(workoutExercise);
        return "redirect:/workouts/" + workoutExercise.getWorkout().getId() + "/exercises";
    }

    @GetMapping("/workouts/exercises/delete")
    public String deleteWorkoutExercise(@RequestParam Long id) {
        Workout_exercise workoutExercise = workoutExerciseService.findById(id);
        Long workoutId = workoutExercise.getWorkout().getId();
        workoutExerciseService.deleteWorkoutExercise(id);
        return "redirect:/workouts/" + workoutId + "/exercises";
    }

    @GetMapping("/workouts/exercises/sets/edit/{setId}")
    public String showEditSetForm(@PathVariable Long setId, Model model) {
        Workout_exercise_set workoutExerciseSet = workoutExerciseSetService.getWorkoutExerciseSetById(setId);
        model.addAttribute("workoutExerciseSet", workoutExerciseSet);
        return "edit-set";
    }
    @PostMapping("/workouts/exercises/sets/update")
    public String updateSet(@ModelAttribute Workout_exercise_set workoutExerciseSet, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-set";
        }
        Workout_exercise_set existingSet = workoutExerciseSetService.getWorkoutExerciseSetById(workoutExerciseSet.getId());
        existingSet.setReps(workoutExerciseSet.getReps());
        existingSet.setWeight(workoutExerciseSet.getWeight());
        workoutExerciseSetService.updateWorkoutExerciseSet(existingSet);
        return "redirect:/workouts/" + existingSet.getWorkout_exercise().getWorkout().getId() + "/exercises";
    }

    @GetMapping("/workouts/exercises/sets/delete")
    public String deleteSet(@RequestParam Long id) {
        Workout_exercise_set set = workoutExerciseSetService.getWorkoutExerciseSetById(id);
        Long workoutId = set.getWorkout_exercise().getWorkout().getId();
        workoutExerciseSetService.deleteWorkoutExerciseSet(id);
        return "redirect:/workouts/" + workoutId + "/exercises";
    }
}
