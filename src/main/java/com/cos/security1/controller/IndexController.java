package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //view를 리턴하겠다.
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates (prefix), .mustache (suffix) 생략가능 
		return "index";
	}
	
	@ResponseBody
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	// 스프링시큐리티가 해당 주소를 낚아챈다. -> SecurityConfig 파일 생성 후 작동 안함. 원하는 주소로 이동한다.
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		
		/**
		 * 패스워드가 암호화가 안되어 있으면 시큐리로 로그인을 할 수가 없음.
		 */
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user);
		
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN") // 특정 메소드에 간단하게 걸고 싶을 때.
	@ResponseBody
	@GetMapping("/info")
	public String info() {
		return "개인정보";
	}
	
	//@PostAuthorize() 주소가 호출된 후
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 주소가 호출되기 전에. 권한이 복수 일때.
	@ResponseBody
	@GetMapping("/data")
	public String data() {
		return "데이터정보";
	}
}
