package com.hitit.hititassignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Contributor {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonProperty("login")
    private String username;

    @JsonProperty("contributions")
    private int contributions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "repository_id")
    private Repository repository;

    public Contributor() {
    }

    public Contributor(String username, int contributions) {
        this.username = username;
        this.contributions = contributions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", contributions=" + contributions +
                ", repository=" + repository +
                '}';
    }
}
