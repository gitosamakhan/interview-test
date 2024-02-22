package com.hitit.hititassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.hititassignment.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class RepositoryService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContributorService contributorService;

    private static final String URI = "https://api.github.com/orgs/apache/repos";
    private static final Logger logger = LoggerFactory.getLogger(RepositoryService.class);

    public List<Repository> getTop5Repositories() {
        List<Repository> repositories =
                getAllRepositories()
                .stream()
                .sorted(Comparator.comparingInt(Repository::getForksCount).reversed())
                .limit(5)
                .toList();
        repositories.forEach(repository -> contributorService.setTop10Contributors(repository));
        return repositories;
    }

    private List<Repository> getAllRepositories() {
        ResponseEntity<String> exchange = restTemplate.exchange(URI, HttpMethod.GET, null, String.class);
        if (exchange.getStatusCode().isError()) {
            logger.error("Cannot get data from api endpoint: {} ", URI);
            return new ArrayList<>();
        }
        String body = exchange.getBody();
        try {
            return objectMapper.readValue(body, new TypeReference<List<Repository>>() {});
        } catch (JsonProcessingException jpa) {
            logger.error("Cannot parse JSON to object: {} ", body);
        }
        return new ArrayList<>();
    }
}
