package studycf.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import studycf.dto.User;

//시큐리티가 로그인 주소 요청이 오면 낚아채서 로그인 진행
//로그인 진행이 완료되면 시큐리티 session만듦(security contextHolder)
//오브젝트 타입 - Authentication 타입 객체
//user오브젝트 타입 - userDetails타입 객체

//security Session - authentication - userDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	private User user;//콤포지션
	private Map<String, Object> attributes;
	
	//일반로그인
	public PrincipalDetails(User user) {
		System.out.println("principal: " + user);
		this.user = user;
	}
	//OAuth로그인
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	//해당 유저의 권한 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		
		
		return collect;
	}

	@Override
	public String getPassword() {
		
		return user.getUserPw();
	}

	@Override
	public String getUsername() {
		
		return user.getUserId();
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
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {

		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
	

}
