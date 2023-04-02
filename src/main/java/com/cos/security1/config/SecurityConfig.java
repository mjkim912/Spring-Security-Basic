package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

@Configuration
//@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다. 
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 (IndexController), preAthorized 활성화.
public class SecurityConfig {

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	/**
	 * 비밀번호 암호화.
	 * @Bean 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/user/**").authenticated()	// 인증만 되면 들어갈 수 있는 주소.
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
				// hasRole('ROLE_USER')")
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
				// hasRole('ROLE_USER')")
				.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/login")	// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행시켜준다. -> 컨트롤러에 /login을 따로 만들지 않아도 된다는 뜻.
				.defaultSuccessUrl("/")
				.and()
				.oauth2Login()
				.loginPage("/loginForm")	
				.userInfoEndpoint()				// 구글 로그인이 완료된 뒤의 후처리. Tip. 코드를 받는게 아니고 (엑세스토큰+사용자프로필정보)을 받는다.
				.userService(principalOauth2UserService);
				

		return http.build();
	}
}
