# Test Assignment
`Java 11 ☕️` `Spring Boot 2.5.6 🍀` `H2 💿` `GitHub API 🐱`

<hr/>

This project calls GitHub API to get repositories under Apache organization, sorts and filters out top 5 repositories. It also filters top 10 contributors to each repository. All the data is saved in H2 in-memory database using Hibernate.

This project saves the data in two tables:
* Repository
* Contributor

Thus, making a one-to-many relationship between repository and contributor.

## Running the project

After running the service, following log message would show up in the logs:

    Repository and Contributors data is loaded in H2!!!

Once this message is logged, data should have been saved in H2.

To check data enqueued in H2, 
* goto `localhost:8080/h2-console`
* db=`jdbc:h2:mem:testdb`
* username=`sa`
* password=

Making a GET request on `/api/v1/repo` should return all the data.

A sample result after hitting `/api/v1/repo` is shown below:

    [
        {
            "id": 1,
            "name": "zookeeper",
            "contributors": [
                {
                    "id": 2,
                    "login": "phunt",
                    "contributions": 422
                },
                {
                    "id": 3,
                    "login": "breed",
                    "contributions": 145
                },
                {
                    "id": 4,
                    "login": "fpj",
                    "contributions": 90
                },
                {
                    "id": 5,
                    "login": "eolivelli",
                    "contributions": 75
                },
                {
                    "id": 6,
                    "login": "anmolnar",
                    "contributions": 71
                },
                {
                    "id": 7,
                    "login": "skamille",
                    "contributions": 67
                },
                {
                    "id": 8,
                    "login": "rgs1",
                    "contributions": 63
                },
                {
                    "id": 9,
                    "login": "rakeshadr",
                    "contributions": 48
                },
                {
                    "id": 10,
                    "login": "maoling",
                    "contributions": 47
                },
                {
                    "id": 11,
                    "login": "hanm",
                    "contributions": 44
                }
            ],
                "full_name": "apache/zookeeper",
                "contributors_url": "https://api.github.com/repos/apache/zookeeper/contributors",
                "stargazers_count": 11812
            }
    ]

Happy Coding 😴