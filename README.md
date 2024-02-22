# Test Assignment
`Java 11 ☕️` `Spring Boot 2.5.6 🍀` `H2 💿` `GitHub API 🐱`

<hr/>

This project calls GitHub API to get repositories under Apache organization, sorts and filters out top 5 repositories. It also filters top 10 contributors to each repository. All the data is saved in H2 in-memory database using Hibernate.

This project saves the data in two tables:
* Repository
* Contributor

Thus, making a one-to-many relationship between repository and contributor.

## Running the project

After running the project, following log message would show up in the logs:

    Repository and Contributors data is loaded!!!

Once this message is logged, data should have been saved in H2.

Making a GET request on `/api/v1/repo` should return all the data. 

Happy Coding 😴