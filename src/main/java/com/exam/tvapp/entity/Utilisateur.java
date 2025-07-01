package com.exam.tvapp.entity;

import com.exam.tvapp.entity.enums.Profession;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    @ManyToMany
    private List<Programme> programmesFavoris;
}
