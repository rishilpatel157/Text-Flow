package com.content.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.content.entity.TextSummarization;

public interface TextSummRepository extends JpaRepository<TextSummarization, Integer>{

}

