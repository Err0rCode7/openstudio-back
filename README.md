# openstudio-back 1차스프린트

42 SEOUL의 교육생들을 위한 서비스 개발 프로젝트로, 과제를 같이 할 교육생을 찾는 교육생들끼리 팀을 만들어주는 웹 애플리케이션

## DB 설계(ERD)
![ERD](https://user-images.githubusercontent.com/48249549/116286994-d6912180-a7ca-11eb-84a7-08221321c559.png)

## Admin page

![화면조회_압축](https://user-images.githubusercontent.com/48249549/116287115-fc1e2b00-a7ca-11eb-954d-6329ebceef83.gif)

- [다른 기능 더보기](./docs/)

## Build

First of all, set `application-oauth.yaml` and `applciation-smtp.yaml` with yours

- Quick Run
  
    `./gradlew bootRun`
- Build with tasks
    - default profile
      
        `./gradlew build`
    - my profile

        - [dev, local] 중 택 1
        
        `./gradlew build -Pprofile=myprofile`
- Run fat jar with built profile
    ```shell
    # -P는 생략가능
    ./gradlew build -Pprofile=myprofile
    java -jar application.jar
    ```
- Run fat jar with my profile
    ```shell
    ./gradlew build
    java -Dspring.profiles.active=myprofile -jar applicaition.jar
    ```
  