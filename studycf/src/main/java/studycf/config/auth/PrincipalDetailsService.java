package studycf.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import studycf.dto.User;
import studycf.mapper.UserMapper;



//시큐리티 설정에서 loginProcessingUrl("/login")
//로그인 요청이 오면 자동으로 userDetailsService 타입으로 loc되어있는 loadUserByUsername 함수실행
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserMapper userMapper;
	
	public PrincipalDetailsService(UserMapper userMapper) {
		this.userMapper	=	userMapper;
	}
	
	//시큐리티 session(내부Authentication(내부 UserDetail))
	//함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다. 
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		User u = userMapper.getUserInfoById(username);
		System.out.println(u);
		if(u != null) {
			return new PrincipalDetails(u);
			
		}
		
		return null;
	}

}
