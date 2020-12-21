package com.yeollow.springboot.config.auth;

import com.yeollow.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity                  //Spring Security설정을 활성화 시켜줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

//        .csrf().disable().headers().frameOptions().disable()  -> h2-console화면을 사용하기 위해 disable함.
//        .authorizeRequests()  ->  URL별 권한 관리를 설정하는 옵션의 시작. authorizeRequests()가 설정되어 있어야 antMatchers()를 사용할 수 있음.
//        .antMatchers()    ->  URL, HTTP Method별로 권한 관리 대상을 지정가능. "/"로 지정된 URL들은 permitAll()을 통해 전체관람, POST Method이면서 /api/v1/**주소를 가진 URL은 USER권한을 가진 사람만.
//        .anyRequest() ->  설정한 값 이외의 다른 URL 뒤에 .authenticated()를 추가하여 나머지 URL들에 대해 인증된 사용자들에게만 허용하게 함.
//        .logout()     ->  logout기능에 대한 여러 설정의 진입점 -> .logoutSuccessUrl("/")을 통해 logout이 성공적으로 완료되면 "/"로 이동함.
//        .oauth2Login()    ->  OAuth2 로그인 기능에 대한 여러 설정의 진입점    ->  .userInfoEndpoint()를 통해 OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당.
//        .userService()    ->  소셜 로그인 성공 후 UserService인터페이스의 구현객체를 등록.
    }
}
