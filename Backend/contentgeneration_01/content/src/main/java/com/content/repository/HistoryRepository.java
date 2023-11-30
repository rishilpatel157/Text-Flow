package com.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer>{

}
