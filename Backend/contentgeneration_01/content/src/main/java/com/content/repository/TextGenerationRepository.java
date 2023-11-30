package com.content.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.content.entity.TextGeneration;

public interface TextGenerationRepository extends JpaRepository<TextGeneration, Integer>{

}
