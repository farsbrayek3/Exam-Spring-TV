package com.exam.tvapp.service;

import com.exam.tvapp.entity.*;
import com.exam.tvapp.entity.enums.Profession;
import com.exam.tvapp.entity.enums.Thematique;
import com.exam.tvapp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TvService {
    private final UtilisateurRepository utilisateurRepo;
    private final ProgrammeRepository programmeRepo;
    private final ChaineRepository chaineRepo;

    public Utilisateur ajouterUtilisateur(Utilisateur u) {
        return utilisateurRepo.save(u);
    }

    @Transactional
    public Programme ajouterProgrammeEtChaine(Programme p) {
        if (p.getChaine() != null) {
            chaineRepo.save(p.getChaine());
        }
        return programmeRepo.save(p);
    }

    public Programme ajouterProgrammeEtAffecterChaine(Programme p, Long chId) {
        Chaine chaine = chaineRepo.findById(chId).orElseThrow();
        p.setChaine(chaine);
        return programmeRepo.save(p);
    }

    public void affecterProgrammeAUtilisateur(String prNom, String usrNom) {
        Utilisateur u = utilisateurRepo.findByNom(usrNom);
        Programme p = programmeRepo.findByNom(prNom);
        u.getProgrammesFavoris().add(p);
        utilisateurRepo.save(u);
    }

    public void desaffecterProgrammeDeUtilisateur(String prNom, String usrNom) {
        Utilisateur u = utilisateurRepo.findByNom(usrNom);
        Programme p = programmeRepo.findByNom(prNom);
        u.getProgrammesFavoris().remove(p);
        utilisateurRepo.save(u);
    }

    public List<Utilisateur> recupererUtilisateurs(Profession p, Date d, Thematique t) {
        return utilisateurRepo.findAll().stream()
                .filter(u -> u.getProfession() == p && u.getDateInscription().after(d))
                .filter(u -> u.getProgrammesFavoris().stream()
                        .anyMatch(prog -> prog.getChaine().getTheme() == t))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 20000)
    public void ordonnerChaines() {
        Map<Chaine, Long> countMap = new HashMap<>();

        for (Programme p : programmeRepo.findAll()) {
            if (p.getUtilisateurs() != null) {
                countMap.merge(p.getChaine(), (long) p.getUtilisateurs().size(), Long::sum);
            }
        }

        countMap.entrySet().stream()
                .sorted(Map.Entry.<Chaine, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.println("Chaine : " + entry.getKey().getNom() +
                            " | Favoris : " + entry.getValue());
                });
    }
}
