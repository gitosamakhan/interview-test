package com.hitit.hititassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitit.hititassignment.domain.Contributor;
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
public class ContributorService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ContributorService.class);

    public void setTop10Contributors(Repository repository) {
        if (repository.getContributorsUrl().isBlank() || repository.getContributorsUrl() == null) {
            logger.debug("Cannot find contributors of repository: {} ", repository.getFullName());
            return;
        }
        List<Contributor> top10Contributors =
                getAllContributors(repository)
                .stream()
                .sorted(Comparator.comparingInt(Contributor::getContributions).reversed())
                .limit(10)
                .toList();
        top10Contributors.forEach(repository::addContributor);
    }

    private List<Contributor> getAllContributors(Repository repository) {
        ResponseEntity<String> exchange = restTemplate.exchange(
                repository.getContributorsUrl(),
                HttpMethod.GET,
                null,
                String.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            String contributorsString = exchange.getBody();
            try {
                return objectMapper.readValue(
                        contributorsString,
                        new TypeReference<List<Contributor>>() {});
            } catch (JsonProcessingException jsonProcessingException) {
                logger.error("Cannot parse contributors string to list of objets: {}", contributorsString);
            }
        }
        logger.error("Cannot get contributors for : {}", repository.getFullName());
        return new ArrayList<>();
    }
}
