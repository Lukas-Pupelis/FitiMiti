package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.services.MemberService;
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
    private final MemberService memberService;


    @Autowired
    public WorkoutController(WorkoutService workoutService, MemberService memberService) {
        this.workoutService = workoutService;
        this.memberService = memberService;
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
        return "add-workout";
    }
}
