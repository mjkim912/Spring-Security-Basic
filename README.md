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


---

PrincipalDetailsService 에서 PrincipalDetails로 리턴을 하고

PrincipalOauth2UserService 에서 PrincipalDetails로 리턴을 하면

Athentication 에 PrincipalDetails가 저장된다.

---

OAuth2-Client 는 provider 를 제공한다. (구글, 페이스북, 트위터 ... 등)

### 스프링부트 기본 로그인 + OAuth2.0 로그인 = 통합해서 구현



---

# JWT

JWT = Json Web Token

xxxxx.yyyyy.zzzzz
header.payload.signature

header : HS256
payload : { username : ssar }
signature : header + payload + 서버 키값(코스)

각각을 암호환한다.




