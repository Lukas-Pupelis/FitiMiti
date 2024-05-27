package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Exercise;
import com.example.fitimiti.entities.Member;
import com.example.fitimiti.services.ExerciseService;
import com.example.fitimiti.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Controller
@RequestScope
public class ExerciseController {

   private final ExerciseService exerciseService;
   private final MemberService memberService;

   @Autowired
   public ExerciseController(ExerciseService exerciseService, MemberService memberService) {
      this.exerciseService = exerciseService;
      this.memberService = memberService;
   }

   @GetMapping("/exercises")
   public String listExercises(@AuthenticationPrincipal OAuth2User principal, Model model) {
      String email = principal.getAttribute("email");
      Member member = memberService.getMemberByEmail(email);
      List<Exercise> memberExercises = exerciseService.getExercisesByMemberId(member.getId());
      List<Exercise> sharedExercises = exerciseService.getSharedExercises();

      model.addAttribute("memberExercises", memberExercises);
      model.addAttribute("sharedExercises", sharedExercises);
      model.addAttribute("exercise", new Exercise());
      return "exercises";
   }

   @PostMapping("/exercises")
   public String addExercise(@ModelAttribute Exercise exercise, @AuthenticationPrincipal OAuth2User principal) {
      String email = principal.getAttribute("email");
      exerciseService.saveExercise(exercise, email);
      return "redirect:/exercises";
   }

   @GetMapping("/exercises/delete")
   public String deleteExercise(@RequestParam Long id) {
      exerciseService.deleteExercise(id);
      return "redirect:/exercises";
   }

   @GetMapping("/exercises/edit")
   public String editExerciseForm(@RequestParam Long id, Model model) {
      Exercise exercise = exerciseService.getExerciseById(id);
      model.addAttribute("exercise", exercise);
      return "edit-exercise";
   }

   @PostMapping("/exercises/update")
   public String updateExercise(@ModelAttribute Exercise exercise, @AuthenticationPrincipal OAuth2User principal) {
      String email = principal.getAttribute("email");
      exerciseService.updateExercise(exercise, email);
      return "redirect:/exercises";
   }
}
