package com.contentgeneration_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentgeneration_01.entity.TextGeneration;

public interface TextGenerationRepository extends JpaRepository<TextGeneration, Integer>{

}
