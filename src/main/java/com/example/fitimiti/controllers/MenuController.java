package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class MenuController {
   private final MemberService memberService;

   @Autowired
   public MenuController(MemberService memberService) {
      this.memberService = memberService;
   }

   @GetMapping("/hello")
   public String listWorkouts(Model model, @AuthenticationPrincipal OAuth2User principal) {
      model.addAttribute("member", memberService.getMemberByEmail(principal.getAttribute("email")));
//      model.addAttribute("members", memberService.getAllMembers());
      return "hello";
   }
}
