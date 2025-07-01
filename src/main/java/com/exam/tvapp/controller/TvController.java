package com.exam.tvapp.controller;

import com.exam.tvapp.entity.*;
import com.exam.tvapp.entity.enums.*;
import com.exam.tvapp.service.TvService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tv")
@RequiredArgsConstructor
public class TvController {
    private final TvService tvService;

    @PostMapping("/utilisateur")
    public Utilisateur ajouterUtilisateur(@RequestBody Utilisateur u) {
        return tvService.ajouterUtilisateur(u);
    }

    @PostMapping("/programme-chaine")
    public Programme ajouterProgrammeEtChaine(@RequestBody Programme p) {
        return tvService.ajouterProgrammeEtChaine(p);
    }

    @PostMapping("/programme-chaine/{chId}")
    public Programme ajouterProgrammeEtAffecterChaine(@RequestBody Programme p, @PathVariable Long chId) {
        return tvService.ajouterProgrammeEtAffecterChaine(p, chId);
    }

    @PostMapping("/favori")
    public void affecterProgramme(@RequestParam String prNom, @RequestParam String usrNom) {
        tvService.affecterProgrammeAUtilisateur(prNom, usrNom);
    }

    @DeleteMapping("/favori")
    public void desaffecterProgramme(@RequestParam String prNom, @RequestParam String usrNom) {
        tvService.desaffecterProgrammeDeUtilisateur(prNom, usrNom);
    }

    @GetMapping("/utilisateurs")
    public List<Utilisateur> recupererUtilisateurs(
            @RequestParam Profession p,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date d,
            @RequestParam Thematique t
    ) {
        return tvService.recupererUtilisateurs(p, d, t);
    }
}
