package studycf.config;

import org.springframework.beans.factory.annotation.Autowired;

//구글 로그인이 완료된 뒤의 후처리가 필요함.1.코드받기(인증) 2.엑세스토큰(권한) 3. 사용자프로필 정보가져옴.
//4-1.정보토대로 회원가입을 자동으로 진행 4-2. (이메일 전화번호 이름 아이디)쇼핑몰 -> 집주소 등급 


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;
import studycf.config.auth.PrincipalOauth2UserService;


@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)//secured 어노테이션 활성화 preAutorize 어노테이션 활성화
@PropertySource("classpath:application-secret.properties")
public class SecurityConfig{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
	private AuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeRequests() 
	        .antMatchers("/home").permitAll()
	        .antMatchers("/user/userDetail").authenticated() //인증 필요 로그인 한 사람만 가능
	        .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')") //인증 필요 로그인 후 해딩 권한 있는 사람만 들어옴 
	        .anyRequest().permitAll()//위 주소 외 모든 페이지 접근 권한 없음
			.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")//login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행
			.failureHandler(customAuthenticationFailureHandler)//로그인 실패 핸들러
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.loginPage("/login")//tip 코드 x (엑세스토큰+사용자프로필정보)
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
		
		return http.build();
	}

}
