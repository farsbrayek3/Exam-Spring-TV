package com.exam.tvapp.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // S'exécute après toute méthode dont le nom commence par "ajouter"
    @AfterReturning("execution(* com.exam.tvapp.service.*.ajouter*(..))")
    public void logAfterAjoutMethods() {
        System.out.println("✅ Exécution Réussie !");
    }
}
