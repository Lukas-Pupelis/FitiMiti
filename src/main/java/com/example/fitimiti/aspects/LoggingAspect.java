
package com.example.fitimiti.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

   private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

   @Before("execution(* com.example.fitimiti.services..*(..)) && !within(com.example.fitimiti.services.BodyWeightService)")
   public void logBeforeMethodExecution(JoinPoint joinPoint) {
      String username = getUsername();
      String methodName = joinPoint.getSignature().toShortString();
      String timeStamp = LocalDateTime.now().toString();

      logger.info("User: {}, Method: {}, Time: {}, Action: Executing", username, methodName, timeStamp);
   }

   @AfterReturning("execution(* com.example.fitimiti.services..*(..)) && !within(com.example.fitimiti.services.BodyWeightService)")
   public void logAfterMethodExecution(JoinPoint joinPoint) {
      String username = getUsername();
      String methodName = joinPoint.getSignature().toShortString();
      String timeStamp = LocalDateTime.now().toString();

      logger.info("User: {}, Method: {}, Time: {}, Action: Completed", username, methodName, timeStamp);
   }

   private String getUsername() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
         return ((UserDetails) principal).getUsername();
      } else {
         return principal.toString();
      }
   }
}