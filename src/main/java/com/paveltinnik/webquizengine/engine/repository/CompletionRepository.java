package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.CompletionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CompletionRepository extends JpaRepository<CompletionEntity, Integer> {
    Page<CompletionEntity> findAllByUserEmail(String userEmail, Pageable pageable);
}
