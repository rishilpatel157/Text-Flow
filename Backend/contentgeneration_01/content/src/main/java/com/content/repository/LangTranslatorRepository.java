package com.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.entity.LanguageTranslation;

public interface LangTranslatorRepository extends JpaRepository<LanguageTranslation, Integer>{

}
