package com.example.fitimiti.controllers;

import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.services.MemberService;
import com.example.fitimiti.services.BodyWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @PostMapping
    public String addWeight(@AuthenticationPrincipal OAuth2User principal, @RequestParam Float weight) {
        try {
            String email = principal.getAttribute("email");
            Member member = memberService.getMemberByEmail(email);
            if (member == null) {
                return "redirect:/register";
            }

            Member_weight_entry memberWeightEntry = new Member_weight_entry();
            memberWeightEntry.setMember(member);
            memberWeightEntry.setWeight(weight);
            memberWeightEntry.setDate(new java.util.Date());

            bodyWeightService.saveBodyWeight(memberWeightEntry);
            return "redirect:/bodyWeight";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erroras controller addWeight");
            return "error";
        }
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
