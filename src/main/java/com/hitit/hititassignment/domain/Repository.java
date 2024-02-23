package com.hitit.hititassignment.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Repository {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("contributors_url")
    private String contributorsUrl;

    @JsonProperty("stargazers_count")
    private int stargazersCount;

    @OneToMany(mappedBy = "repository", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contributor> contributors = new ArrayList<>();

    public Repository() {}

    public Repository(String name, String fullName, String contributorsUrl, int stargazersCount) {
        this.name = name;
        this.fullName = fullName;
        this.contributorsUrl = contributorsUrl;
        this.stargazersCount = stargazersCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContributorsUrl() {
        return contributorsUrl;
    }

    public void setContributorsUrl(String collaboratorsUrl) {
        this.contributorsUrl = collaboratorsUrl;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public void addContributor(Contributor contributor) {
        if (!contributors.contains(contributor)) {
            contributors.add(contributor);
            contributor.setRepository(this);
        }
    }

    public List<Contributor> getContributors() {
        return contributors;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", collaboratorsUrl='" + contributorsUrl + '\'' +
                ", stargazersCount=" + stargazersCount +
                '}';
    }
}
