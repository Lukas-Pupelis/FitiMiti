package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.services.MemberService;
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

@Controller
@RequestScope
public class RegisterController {
   private final MemberService memberService;

   @Autowired
   public RegisterController(MemberService memberService) {
      this.memberService = memberService;
   }

   @GetMapping("/register")
   public String showRegistrationForm(Model model) {
      model.addAttribute("member", new Member());
      return "register";
   }

   @PostMapping("/register")
   public String registerMember(@ModelAttribute Member member, BindingResult result, @AuthenticationPrincipal OAuth2User principal) {
      if (result.hasErrors()) {
         return "register";
      }
      member.setEmail(principal.getAttribute("email"));
      memberService.saveMember(member);
      return "redirect:/hello";
   }
}
