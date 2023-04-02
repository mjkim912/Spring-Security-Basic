package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 .loginProcessingUrl("/login") 으로
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 자동으로 실행.
// @Service에 의해 PrincipalDetailsService가 등록이 되어서 loadUserByUsername이 호출이 된다.

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	// 시큐리티 session => Athentication => UserDetails
	// 리턴 값이 Athentication(UserDetails) 이렇게 내부로 들어간다.
	// 그리고 session 내부에는 Athentication(UserDetails)가 들어간다.
	// 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 회원가입 폼의 name="username" 으로 적어야 파라미터로 넘어온다.
		// 이름이 다르면 못 받음.
		// 다르게 하려면 시큐리티 설정에서 .usernameParameter("") 에서 바꿔줘야 함.
		
		System.err.println("username : " + username);
		
		User userEntity = userRepository.findByUsername(username);
		
		if (userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		
		return null;
	}

}
