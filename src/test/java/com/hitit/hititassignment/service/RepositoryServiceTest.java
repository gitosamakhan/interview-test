package com.hitit.hititassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.hititassignment.domain.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest {

    private static final String URI = "https://api.github.com/orgs/apache/repos";
    private static final String RESPONSE_STRING = "[{\"id\":null,\"name\":\"repo1\",\"contributors\":[],\"full_name\":\"apache/repo1\",\"contributors_url\":\"http://repo1/contributors\",\"stargazers_count\":20},{\"id\":null,\"name\":\"repo2\",\"contributors\":[],\"full_name\":\"apache/repo2\",\"contributors_url\":\"http://repo2/contributors\",\"stargazers_count\":40},{\"id\":null,\"name\":\"repo3\",\"contributors\":[],\"full_name\":\"apache/repo3\",\"contributors_url\":\"http://repo3/contributors\",\"stargazers_count\":50},{\"id\":null,\"name\":\"repo4\",\"contributors\":[],\"full_name\":\"apache/repo4\",\"contributors_url\":\"http://repo4/contributors\",\"stargazers_count\":60},{\"id\":null,\"name\":\"repo5\",\"contributors\":[],\"full_name\":\"apache/repo5\",\"contributors_url\":\"http://repo5/contributors\",\"stargazers_count\":70},{\"id\":null,\"name\":\"repo6\",\"contributors\":[],\"full_name\":\"apache/repo6\",\"contributors_url\":\"http://repo6/contributors\",\"stargazers_count\":80}]";

    @InjectMocks
    private RepositoryService underTest;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ContributorService contributorService;

    @Mock
    private Logger logger;

    @Test
    void testGetTop5Repositories() throws JsonProcessingException {
        List<Repository> repositories = Arrays.asList(
                new Repository("repo1", "apache/repo1", "http://repo1/contributors", 20),
                new Repository("repo2", "apache/repo2", "http://repo2/contributors", 40),
                new Repository("repo3", "apache/repo3", "http://repo3/contributors", 50),
                new Repository("repo4", "apache/repo4", "http://repo4/contributors", 60),
                new Repository("repo5", "apache/repo5", "http://repo5/contributors", 70),
                new Repository("repo6", "apache/repo6", "http://repo6/contributors", 80)
        );
        ResponseEntity<String> responseEntity = new ResponseEntity<>(RESPONSE_STRING, HttpStatus.OK);
        when(restTemplate.exchange(URI, HttpMethod.GET, null, String.class)).thenReturn(responseEntity);
        when(objectMapper.readValue(eq(RESPONSE_STRING), any(TypeReference.class))).thenReturn(repositories);
        List<Repository> actualTop5Repositories = underTest.getTop5Repositories();
        assertEquals(actualTop5Repositories.size(), 5);
        assertResults(repositories, actualTop5Repositories);
    }

    @Test
    void testGetTop5Repositories_responseDoesNotReturn2xxStatusCode_returnsEmptyArray() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(RESPONSE_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(URI, HttpMethod.GET, null, String.class)).thenReturn(responseEntity);
        List<Repository> actualTop5Repositories = underTest.getTop5Repositories();
        verifyNoInteractions(objectMapper);
        assertNotNull(actualTop5Repositories);
        assertEquals(actualTop5Repositories.size(), 0);
    }

    @Test
    void testGetTop5Repositories_errorParsingString_throwsException() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Corrupt String", HttpStatus.OK);
        when(restTemplate.exchange(URI, HttpMethod.GET, null, String.class)).thenReturn(responseEntity);
        when(objectMapper.readValue(eq(responseEntity.getBody()), any(TypeReference.class))).thenThrow(JsonProcessingException.class);
        List<Repository> actualTop5Repositories = underTest.getTop5Repositories();
        assertNotNull(actualTop5Repositories);
        assertEquals(actualTop5Repositories.size(), 0);
    }

    private void assertResults(List<Repository> expectedRepositories,
                               List<Repository> actualRepositories) {
        actualRepositories = actualRepositories
                .stream()
                .sorted(Comparator.comparingInt(Repository::getStargazersCount).reversed())
                .collect(Collectors.toList());
        expectedRepositories = expectedRepositories
                .stream()
                .sorted(Comparator.comparingInt(Repository::getStargazersCount).reversed())
                .collect(Collectors.toList());
        for (int i = 0; i < actualRepositories.size(); i++) {
            assertEquals(expectedRepositories.get(i).getName(), actualRepositories.get(i).getName());
            assertEquals(expectedRepositories.get(i).getFullName(), actualRepositories.get(i).getFullName());
            assertEquals(expectedRepositories.get(i).getContributorsUrl(), actualRepositories.get(i).getContributorsUrl());
            assertEquals(expectedRepositories.get(i).getStargazersCount(), actualRepositories.get(i).getStargazersCount());
        }
    }
}