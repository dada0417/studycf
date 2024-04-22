package studycf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)//secured 어노테이션 활성화 preAutorize 어노테이션 활성화
public class SecurityConfig{
	
	@Bean
	public BCryptPasswordEncoder encodedPwd() {
		return new BCryptPasswordEncoder();
	}
	
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
			.defaultSuccessUrl("/");
        return http.build();
	}

}
