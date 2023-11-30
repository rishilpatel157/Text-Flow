package com.contentgeneration_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentgeneration_01.entity.TextSummarization;

public interface TextSummRepository extends JpaRepository<TextSummarization, Integer>{

}
