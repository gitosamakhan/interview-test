package com.hitit.hititassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.hititassignment.domain.Contributor;
import com.hitit.hititassignment.domain.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContributorServiceTest {

    @InjectMocks
    private ContributorService underTest;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void testSetTop10Contributors_shouldSetContributionsToRepository() throws JsonProcessingException {
        List<Contributor> contributors = Arrays.asList(
                new Contributor("user1", 10),
                new Contributor("user2", 20),
                new Contributor("user3", 30),
                new Contributor("user4", 40),
                new Contributor("user5", 50),
                new Contributor("user6", 60),
                new Contributor("user7", 70),
                new Contributor("user8", 80),
                new Contributor("user9", 90),
                new Contributor("user10", 100),
                new Contributor("user11", 110),
                new Contributor("user12", 120)
        );
        Repository repository = new Repository("repo1", "apache/repo1", "http://contributor.url", 10);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("TestString", HttpStatus.OK);
        when(restTemplate.exchange(repository.getContributorsUrl(), HttpMethod.GET, null, String.class)).thenReturn(responseEntity);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenReturn(contributors);
        underTest.setTop10Contributors(repository);
        assertEquals(repository.getContributors().size(), 10);
        assertResults(contributors, repository.getContributors());
    }

    @Test
    void testSetTop10Contributors_contributorsUrlIsBlank_shouldNotSetContributors() {
        Repository repository = new Repository("repo1", "apache/repo1", "", 10);
        underTest.setTop10Contributors(repository);
        assertEquals(repository.getContributors().size(), 0);
    }

    @Test
    void testSetTop10Contributors_contributorsUrlIsBlank_shouldThrowException_shouldNotSetContributors() throws JsonProcessingException {
        Repository repository = new Repository("repo1", "apache/repo1", "http://contributor.url", 10);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("TestString", HttpStatus.OK);
        when(restTemplate.exchange(repository.getContributorsUrl(), HttpMethod.GET, null, String.class)).thenReturn(responseEntity);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenThrow(JsonProcessingException.class);
        underTest.setTop10Contributors(repository);
        assertEquals(repository.getContributors().size(), 0);
    }

    private void assertResults(List<Contributor> expectedContributors,
                               List<Contributor> actualContributors) {
        expectedContributors = expectedContributors
                .stream()
                .sorted(Comparator.comparingInt(Contributor::getContributions).reversed())
                .collect(Collectors.toList());
        for (int i = 0; i < actualContributors.size(); i++) {
            assertEquals(expectedContributors.get(i).getUsername(), actualContributors.get(i).getUsername());
            assertEquals(expectedContributors.get(i).getContributions(), actualContributors.get(i).getContributions());
        }
    }
}