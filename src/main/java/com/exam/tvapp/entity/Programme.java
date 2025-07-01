package com.exam.tvapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    private Chaine chaine;

    @ManyToMany(mappedBy = "programmesFavoris", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;
}
