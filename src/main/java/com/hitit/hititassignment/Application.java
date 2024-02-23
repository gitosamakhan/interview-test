package com.hitit.hititassignment;

import com.hitit.hititassignment.dao.RepositoryDao;
import com.hitit.hititassignment.domain.Repository;
import com.hitit.hititassignment.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RepositoryService repositoryService,
											   RepositoryDao repositoryDao) {
		return args -> {
			try {
				List<Repository> top5Repositories = repositoryService.getTop5Repositories();
				repositoryDao.saveAll(top5Repositories);
				logger.info("Repository and Contributors data is loaded in H2!!!");
			} catch (Exception exception) {
				logger.error("Error loading data to H2!!");
			}
		};
	}

}
