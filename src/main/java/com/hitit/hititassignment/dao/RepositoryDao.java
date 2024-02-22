package com.hitit.hititassignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDao extends JpaRepository<com.hitit.hititassignment.domain.Repository, Integer> {}
