package com.example.gestion.moduleservice.repository;

import com.example.gestion.moduleservice.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByNiveau(String niveau);

    List<Module> findByEnseignantId(Long enseignantId);
}
