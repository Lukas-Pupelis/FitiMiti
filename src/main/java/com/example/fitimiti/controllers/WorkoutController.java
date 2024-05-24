package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/add-workout")
    public String showAddWorkoutForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "add-workout";
    }

    @PostMapping("/add-workout")
    public String addWorkout(@ModelAttribute Workout workout, BindingResult result, @AuthenticationPrincipal OAuth2User principal) {
        if (result.hasErrors()) {
            return "add-workout";
        }
        String email = principal.getAttribute("email");
        workoutService.addWorkout(email, workout);
        return "redirect:/hello";
    }
}
