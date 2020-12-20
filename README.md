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
> >```
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
> >```

