package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.entities.Workout;
import com.example.fitimiti.services.MemberService;
import com.example.fitimiti.services.BodyWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bodyWeight")
public class BodyWeightController {
    private final BodyWeightService bodyWeightService;
    private final MemberService memberService;

    @Autowired
    public BodyWeightController(BodyWeightService bodyWeightService, MemberService memberService) {
        this.bodyWeightService = bodyWeightService;
        this.memberService = memberService;
    }
//@ModelAttribute Member_weight_entry weightEntry
    @PostMapping
    public String addWeight(@AuthenticationPrincipal OAuth2User principal, Float weight, BindingResult result) {
        System.out.println("AR JIS BLET PASIEKIA CIA AR NE NX");
        String email = principal.getAttribute("email");
        Member member = memberService.getMemberByEmail(email);
        if (member == null) {
            return "/register";
        }
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            return "/bodyWeight";
        }
        Member_weight_entry weightEntry = new Member_weight_entry();
        weightEntry.setWeight(weight);
        weightEntry.setMember(member);
        weightEntry.setDate(new java.util.Date());

        bodyWeightService.addWeight(email, weightEntry);
        return "/bodyWeight";
    }

    @GetMapping
    public String getWeights(@AuthenticationPrincipal OAuth2User principal, @RequestParam(defaultValue = "3m") String period, Model model) {
        try {
            String email = principal.getAttribute("email");
            Member member = memberService.getMemberByEmail(email);
            if (member == null) {
                return "redirect:/register";
            }

            List<Member_weight_entry> weights = bodyWeightService.getBodyWeightByMemberId(member.getId(), period);
            model.addAttribute("weights", weights);
            model.addAttribute("member", member);
            return "bodyWeight";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erroras controller get weights");

            return "error";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        System.out.println("Erroras controller Handle Exeption");
        return "error";
    }
}
