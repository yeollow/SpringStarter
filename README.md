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
>   ` mysql -u admin -p -h springboard-yeollow.cbgya6d49t4i.ap-northeast-2.rds.amazonaws.com`
>   
