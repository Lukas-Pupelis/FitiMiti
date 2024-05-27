package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.*;
import com.example.fitimiti.services.ExerciseService;
import com.example.fitimiti.services.MemberService;
import com.example.fitimiti.services.WorkoutExerciseService;
import com.example.fitimiti.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WorkoutController {

    private final WorkoutService workoutService;
    private final MemberService memberService;
    private final ExerciseService exerciseService;
    private final WorkoutExerciseService workoutExerciseService;

    @Autowired
    public WorkoutController(WorkoutService workoutService, MemberService memberService, ExerciseService exerciseService, WorkoutExerciseService workoutExerciseService) {
        this.workoutService = workoutService;
        this.memberService = memberService;
        this.exerciseService = exerciseService;
        this.workoutExerciseService = workoutExerciseService;
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

        List<Exercise> sharedExercises = exerciseService.getSharedExercises();
        List<Exercise> memberExercises = exerciseService.getExercisesByMemberId(member.getId());

//        // Group exercises by exercise_number and their sets by set_number
//        Map<Integer, List<Workout_exercise>> exerciseMap = exercises.stream()
//                .collect(Collectors.groupingBy(Workout_exercise::getExercise_number));
//
//        List<ExerciseGroup> exerciseGroups = exerciseMap.entrySet().stream()
//                .map(entry -> new ExerciseGroup(entry.getKey(), entry.getValue().get(0).getExercise(), entry.getValue()))
//                .sorted(Comparator.comparingInt(ExerciseGroup::getExerciseNumber))
//                .collect(Collectors.toList());


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
}
