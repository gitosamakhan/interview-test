package com.hitit.hititassignment.dao;

import com.hitit.hititassignment.domain.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorDao extends JpaRepository<Contributor, Integer> {}
