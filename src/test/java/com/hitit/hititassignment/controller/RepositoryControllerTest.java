package com.hitit.hititassignment.controller;

import com.hitit.hititassignment.dao.RepositoryDao;
import com.hitit.hititassignment.domain.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositoryControllerTest {

    @InjectMocks
    private RepositoryController underTest;

    @Mock
    private RepositoryDao repositoryDao;

    @Test
    void testGetAllRepositories() {
        List<Repository> repositories = Arrays.asList(
                new Repository("repo1", "apache/repo1", "http://repo1/contributors", 20),
                new Repository("repo2", "apache/repo2", "http://repo2/contributors", 40)
        );
        when(repositoryDao.findAll()).thenReturn(repositories);
        List<Repository> actualRepositories = underTest.getApacheRepositories();
        assertResults(repositories, actualRepositories);
    }


    private void assertResults(List<Repository> expectedRepositories,
                               List<Repository> actualRepositories) {
        actualRepositories = actualRepositories
                .stream()
                .sorted(Comparator.comparingInt(Repository::getStargazersCount))
                .collect(Collectors.toList());
        expectedRepositories = expectedRepositories
                .stream()
                .sorted(Comparator.comparingInt(Repository::getStargazersCount))
                .collect(Collectors.toList());
        for (int i = 0; i < actualRepositories.size(); i++) {
            assertEquals(expectedRepositories.get(i).getName(), actualRepositories.get(i).getName());
            assertEquals(expectedRepositories.get(i).getFullName(), actualRepositories.get(i).getFullName());
            assertEquals(expectedRepositories.get(i).getContributorsUrl(), actualRepositories.get(i).getContributorsUrl());
            assertEquals(expectedRepositories.get(i).getStargazersCount(), actualRepositories.get(i).getStargazersCount());
        }
    }
}