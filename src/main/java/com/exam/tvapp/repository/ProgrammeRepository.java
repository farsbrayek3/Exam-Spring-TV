package com.exam.tvapp.repository;

import com.exam.tvapp.entity.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    Programme findByNom(String nom);
}
