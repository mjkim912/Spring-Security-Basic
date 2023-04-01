package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

/**
 * 
 *  /login 주소 요청이 오면 시큐리티가 낚아채서 로그인을 진행시킨다.
 *  로그인 진행이 완료가 되면 시큐리티 Session을 만들어준다. (Security ContextHolder) 에 정보를 저장한다.
 *  오브젝트는 Athentication 타입의 객체여야 한다.
 *  Athentication 안에는 User 정보가 있어야 한다.
 *  User 오브젝트의 타입은 UserDetails 타입의 객체여야 한다.
 * 
 * 시큐리티가 가지고 있는 Security Session 영역에 세션 정보를 저장을 해주는데
 * 여기 들어갈 수 있는 객체는 Athentication 객체여야 한다.
 * 
 * Security Session => Athentication => UserDetails(PrincipalDetails)
 *
 */
public class PrincipalDetails implements UserDetails{

	private User user; //콤포지션 
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴하는 곳.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});
		
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 회원이 1년동안 로그인을 안하면 휴먼계정으로 하기로 함.
		// 현재시간 - 마지막 로그인 시간 => 1년 초과하면 return false;
		return true;
	}

}
