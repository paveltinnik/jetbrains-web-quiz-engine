package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.CompletionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionRepositoryImpl {
    @Autowired
    CompletionRepository completionRepository;

    public void addCompletion(CompletionEntity completionEntity) {
        completionRepository.save(completionEntity);
    }

    public Page<CompletionEntity> getAllCompletions(String email, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        return completionRepository.findAllByUserEmail(email, paging);
    }
}
