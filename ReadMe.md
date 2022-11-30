# musinsa_homework_강지현

### Description

- 무신사 인게이지먼트팀 지원 과제입니다. 

  <br/><br/>

### 실행 방법

- gradle : liberica 1.8 ver 설치
- ./gradlew
-
- homeworkApplication 실행


### 참고사항

- 각 메소드에 간단히 주석을 기재하였습니다.
- 공통 response spec 을 구현하여 data 객체 안에 response 결과를 얻을 수 있습니다.
- Exception handler 를 구현하였지만 시간관계 상 핸들링을 하지 못해 동작하지 않습니다.
- 초반에 jpa 와 queryDSL 을 모두 사용할 목적으로 queryDSL setting 도 하였으나 JPA 쿼리로 충분하여 queryDSL 은 실제로 사용되지 않았습니다.
- h2.console 확인 시 properties에 적힌 파일경로가 아닌 run console에 h2-console/ 이하 url로 접속가능합니다.
  (ex : 'jdbc:h2:mem:f4fb8b9b-34c4-4ae3-93d8-5b12327a4bc1' )
- api 테스트는 postman 등의 Api 플랫폼으로 테스트 해주시길 부탁드립니다.

# Api 명세
 ### 1. 포인트 지급 요청
- [POST] localhost:8080/api/point/add
* 로그인 기능을 구현하지 않아 자유롭게 임의의 userSeq 값을 기입해 주시기 바랍니다.
 ````
// case 1. userSeq만 기입할 시 오늘 날짜로 요청됩니다.
{
    "userSeq": 10
}

// case 2. 날짜로 테스트 할 경우 과거의 날짜부터 파라미터 값으로 넘겨 테스트 부탁드립니다.(format :YYYYMMDD)

{
    "userSeq": 10,
    "regDate" : "20221127"
}


// 응답 예시

{
    "meta": {
        "code": 0,
        "message": "SUCCESS"
    },
    "data": "매일 00 시 00 분 00 초 선착순 10 명 100 포인트 지급!!!"
}
````

### 2. 해당 날짜에 포인트 지급 이력 조회
- [GET] localhost:8080/api/point/list/${date}?order=${order} 
 
* ex1) localhost:8080/api/point/list/20221130
* ex2) localhost:8080/api/point/list/20221130?order=desc (defalut = desc)
* ex3) localhost:8080/api/point/list/20221130?order=asc

```
// 응답 예
{
    "meta": {
        "code": 0,
        "message": "SUCCESS"
    },
    "data": [
        {
            "pointSeq": 2,
            "pointLevel": 1,
            "point": 100,
            "userSeq": 10,
            "regDate": "2022-11-30"
        },
        {
            "pointSeq": 1,
            "pointLevel": 1,
            "point": 100,
            "userSeq": 22,
            "regDate": "2022-11-30"
        }
    ]
}




```

### 3.  point 키값으로 포인트 지급이력 상세 조회
-  [GET] localhost:8080/api/point/${pointSeq}



```
//  [GET] localhost:8080/api/point/1  응답 예시
{
    "meta": {
        "code": 0,
        "message": "SUCCESS"
    },
    "data": [
        {
            "pointSeq": 1,
            "pointLevel": 1,
            "point": 100,
            "userSeq": 22,
            "regDate": "2022-11-30"
        }
    ]
}

```

### Stack
    Spring-boot
    JPA
    hibernate
    MySQL


### 개발환경
```
IDE : IntelliJ IDEA Ultimate
OS : Mac OS X
SpringBoot 1.5.9
Java11
Gradle
```
<br/><br/>


감사합니다.