package com.contentgeneration_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentgeneration_01.entity.LanguageTranslation;

public interface LangTranslatorRepository extends JpaRepository<LanguageTranslation, Integer>{

}
