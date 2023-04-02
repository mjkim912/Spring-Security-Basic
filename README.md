# Spring-Security-Basic

## 스프링 시큐리티

서버 세션 안에 시큐리티가 관리하는 세션이 따로 있다.

시큐리티 세션 안에는 Authentication 객체가 들어가야 한다.
그러면 컨트롤러에서 DI 할 수 있다.

Authentication 안에 들어갈 수 있는 타입은 두 가지
1. UserDetails : 일반로그인
2. OAuth2User : 구글 같은 OAuth 로그인

로그인하면 Authentication 안에 정보가 들어간다.

로그인하는 방법에 따라 받는 파라미터 종류가 다르다.

그래서 UserDetails와 OAuth2User가 상속받는 클래스를 만들어 파라미터로 정보를 받는다.

--> PrincipalDetails 클래스
