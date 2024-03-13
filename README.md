### Hexlet tests and linter status:
[![Actions Status](https://github.com/CyberXAndrew/java-project-72/workflows/hexlet-check/badge.svg)](https://github.com/CyberXAndrew/java-project-72/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/a8f9d81b86cd305b1b8d/maintainability)](https://codeclimate.com/github/CyberXAndrew/java-project-72/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a8f9d81b86cd305b1b8d/test_coverage)](https://codeclimate.com/github/CyberXAndrew/java-project-72/test_coverage)

### About project
Page Analyzer – a website that analyzes whether the specified pages are suitable for SEO.

The application checks the availability of a URL at the moment it is requested. 
Paste an interesting URL into the form on the main page, and you will receive information about the URL's status.
The request returns the following details:
1. Response status code
2. Page title 
2. HTML H1 block (if present)
3. HTML meta description block (if present)

All previous requests will be stored in the history for later reference.

### Link to application:
[Site Analizer](https://siteanalizerproject.onrender.com)

### Run
```
./gradlew run
```

### Technologies used
| Technology                  | Realization                                                       |
|-----------------------|-------------------------------------------------------------------|
| Programming language: | Java 20                                                           |
| Build automation tool | Gradle 8.3                                                        |
| Framework             | Javalin                                                           |
| HTML Template engine: | Java Template Engine                                              |
| CSS framework         | Bootstrap 5.1                                                     |
| ORM                   | eBean                                                             |
| Database              | PostgreSQL (for production) / H2 (for tests)                      |
| Test technologies     | JUnit framework / Unirest library / MockWebServer / slf4j library |
| CI                    | GithubActions / JaCoCo / Codeclimate                              |

### Demonstration
#### Main page

![Main page](https://github.com/CyberXAndrew/java-project-72/blob/main/previewscreenshots/01.png)

#### List of pages to check

![List of pages to check](https://github.com/CyberXAndrew/java-project-72/blob/main/previewscreenshots/02.png)

#### Completed check

![Completed check](https://github.com/CyberXAndrew/java-project-72/blob/main/previewscreenshots/03.png)

