package com.hitit.hititassignment.controller;

import com.hitit.hititassignment.dao.RepositoryDao;
import com.hitit.hititassignment.domain.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/repo")
public class RepositoryController {

    @Autowired
    private RepositoryDao repositoryDao;

    @GetMapping()
    public List<Repository> getAppRepositories() {
        return repositoryDao.findAll();
    }
}
