# springStarter

* 버전
  * Java8 
  * gradle version 4.x 
  * Spring Boot 2.1.X 
  * IntelliJ Ultimate

> IntelliJ Community Version에서는 Mustache를 지원하지 않음 \
> SpringBoot 2.2.x와 Gradle 5.x이상에서는 동작하지 않음
> > gradle version 확인 - gradle/wrapper/gradle-wrapper.properties 확인 \
> > gradle version 변경
```
 gradlew wrapper --gradle-version 4.10.2
``` 

> Spring Boot version 확인 - build.gradle에서 springBootVersion을 확인 (2.1.X.RELEASE)
```
buildscript {
    ext {
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
//        jcenter()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}
```
<hr>

#### OAuth2 소셜 로그인
* Google, Naver 등의 console에 프로젝트를 등록 후 OAuth client-id, secret을 발급
    *   application-oauth.properties file 생성 이후 client-id, client-secret 등을 spring-security 등록
    *   각 console에 승인된 URL, Redirection URL을 입력
    *   spring-security dependency 추가
        *   `compile('org.springframework.boot:spring-boot-starter-oauth2-client')`
    
<hr>

#### EC2 Putty SSH 접속 (EC2 보안 인바운드 SSH를 내IP로 변경)
* putty.exe, puttygen.exe download
    *   puttygen.exe로 .pem을 .ppk로 변환
    *   .ppk를 putty의 Connection - SSH - Auth탭에서 등록
    *   HostName에 ec2-user@EC2 EIP를 입력 후 접속
* ssh -i (.pem 위치) EC2 EIP
    *   ~에 .ssh dir을 생성하여 .pem파일을 등록 후 chomod 600
    *   .ssh dir에 config를 생성 후 chomod 700
    
```
Host ServiceName
    HostName EC2 EIP
    User ec2-user
    IdentityFile ~/.ssh/.pem file
```
<hr>

##### 이후 ssh (config에 등록한 ServiceName) 으로 접속 가능
>   EC2 Server서버 생성 시 꼭 해야하는 설정
> * JDK설치 (여기서는 Java 8)
>   *  `sudo yum install -y java-1.8.0-openjdk-devel.x86_64` 
> * timeZone 변경
>   *  `sudo rm /etc/localtime`
>   *  `sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime`
>   * 이후 `date`로 KST확인 
> * hostName 변경(여러 서버 관리의 경우 IP만으로 서비스 파악이 힘듬)
>   * `sudo vim /etc/sysconfig/network`를 열어 `HOSTNAME=원하는 이름`을 추가
>   * `sudo vim /etc/hosts`에 127.0.0.1  등록한 원하는 Hostname을 등록

<hr>

#### EC2 RDS접근 확인
*   RDS 인스턴스 생성 후 timeZone, character Set 변경
    *  character Set의 경우 utf8mb4를 보편적으로 많이 사용
*   RDS 보안 그룹에 IP를 추가
    *   보안 그룹 ID와 IP를 RDS 보안 그룹의 인바운드로 추가
*   Database Navigator Plugin 설치
    *   설치 후 Ctrl + Shift + A로 Database Browser검색 후 실행
    *   Host에는 RDS의 엔드 포인트를 등록
        *   `springboard-yeollow.cbgya6d49t4i.ap-northeast-2.rds.amazonaws.com`
    *   User는 RDS생성시 입력한 마스터 사용자 이름(기본 admin)
>   EC2에 MySql 설치 `sudo yum install mysql`
>   이후 계정, 비밀번호, host주소를 사용해 RDS접속  \
>   `mysql -u yeollow -p -h springboard-yeollow.cbgya6d49t4i.ap-northeast-2.rds.amazonaws.com`
`
<hr>

#### EC2 jar 배포 - deploy.sh 작성
*   EC2에 project clone받기
    *   `sudo yum install git`로 git설치 이후 프로젝트를 저장할 디렉토리 생성 후 `git clone`
*   배포 script만들기 (deploy.sh -> chmod +x)
    *   `#!/bin/bash`로 script파일 생성
        *   `REPOSITORY=/home/ec2-user/app/dirName`
        *   `PROJECT_NAME=projName` 으로 변수 설정
    *   `git clone` 혹은 `git pull`을 통해 최신 버전의 프로젝트를 받음
    *   Gradle이나 Maven을 통해 프로젝트 테스트와 빌드
    *   EC2 Server에서 해당 프로젝트 실행 및 재실행
>   `git pull`로 최신 프로젝트를 받아오고, `./gradlew build`로 프로젝트를 빌드.
>   *   `./gradlew build`에서 permission denied가 발생하면, `chmod +x ./gradlew`로 실행권한 부여
>      build의 결과물인 jar파일을 REPOSITORY로 복사 
>       *   `cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/` 
>       *   `JAR_NAME=$(ls -tr $REPOSITORY | grep *.jar | tail -n 1)` 을 통해 tail으로 실행할 가장 최신의 jar파일을 찾음 
>            *   spring-boot는 내장 Tomcat을 사용하여 jar파일만 있으면 바로 WAS를 실행할 수 있음 
>            *   이를 일반적으로 `java -jar` 명령어를 사용하지만, 터미널 접속이 끊기면 Application도 같이 종료 됨 
>            * 즉, 터미널을 종료해도 Application은 계속 구동될 수 있도록 nohup명령어를 사용 
>   *   `nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &` 이후 외부 Security file 등록
>       *   서버에 client-id와 client-secret을 갖고있게 하기 위해 local에서 생성했던 oauth.properties를 서버에도 생성
>       *   application-oauth.properties를 사용하도록 script file(deploy.sh)을 수정
>```
>nohup java -jar \
>    -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \ 
>        $REPOSITORY/$JAR_NAME 2>&1 &
>``` 
>   *   이후 RDS접근을 위한 설정
>       *   project 설정 
>           *   `compile("org.mariadb.jdbc:mariadb-java-client")` dependency 추가
>           *   resources/application-real.properties등록
>       *   EC2 설정
>           *   oauth와 같이 application-real-db.properties생성
>```           
>spring.jpa.hibernate.ddl-auto=none
>spring.datasource.url=jdbc:mariadb:RDS endpoint:port/database 이름
>spring.datasource.username=db계정
>spring.datasource.password=db게정 비밀번호
>spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
> ```
>   * 이후 application-real-db.properties를 사용하도록 script file(deploy.sh)을 수정
>```
>nohup java -jar \
>   -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties, /home/ec2-user/app/application-real-db.properties \
>  -Dspring.profiles.active=real \
>   $REPOSITORY/$JAR_NAME 2>&1 &
>```

##### 서버에 jar 배포 시 AWS EC2 domain (public DNS:8080)으로 접속 가능
##### 각 소셜 로그인을 위해 콘솔에서 승인된 도메인(DNS)와 승인된 리디렉션 URI를 등록(DNS:port/login/oauth/code/google, naver ...)

<hr>

#### CI/CD
*   Travis CI
    *   프로젝트에서 .travis.yml생성
    *   Travis CI와 AWS S3, CodeDeploy 연동
        *   .travis.yml에서 정의한 branch에 push하면 자동으로 배포
*   Jenkins
    *   별도의 EC2 instance가 필요함.. 여기선 다루지 않음.
    
<hr>
    
#### 무중단 배포
*   EC2 Server에 Nginx구축 후 SpringBoot Jar을 2대 사용
    *   신규배포 시 Nginx랑 연결되지 않은 SpringBoot로 연결
    *   이후 배포가 완료 되면 nginx reload
        *   Nginx는 80,443 port
        *   각 SpringBoot는 8081,8082 port
>   Nginx 설치 : `sudo yum install nginx` \
>   AWS EC2 인바운드 규칙에 80 port와 login redirection URL 추가 
>   *   Nginx - SpringBoot 연동
>       *   nginx.conf에서 server아래의 location / proxy_pass에 http://localhost:8080전달
>       *   프로젝트에 8081l,8082포트를 할당할 properties 파일 작성
>           *   기존 application.properties에 `server.port=portNum` 작성

<hr>

#### 배포 전체 구조
