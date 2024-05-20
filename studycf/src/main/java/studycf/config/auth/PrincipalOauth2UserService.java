package studycf.config.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import studycf.config.auth.provider.GoogleUserInfo;
import studycf.config.auth.provider.KakaoUserInfo;
import studycf.config.auth.provider.NaverUserInfo;
import studycf.config.auth.provider.OAuth2UserInfo;
import studycf.dto.User;
import studycf.mapper.UserMapper;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Bean
	public BCryptPasswordEncoder encodedPwd() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private UserMapper userMapper;

	
	//구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	//함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다. 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());//어떤 OAuth로 로그인 했는지 
		System.out.println("getAccessToken : " + userRequest.getAccessToken());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		//구글 로그인 버튼클릭 -> 구글로그인 창 -> 로그인을 완료 ->code리턴(OAuth-client라이브러리) -> AccessToken요청
		//userRequest정보 -> loadUser함수 호출->구글로 부터 회원 프로필
		System.out.println("getAttributes : " + oauth2User.getAttributes());
		
		OAuth2UserInfo oAuth2UserInfo = null;
				
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		}else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>)oauth2User.getAttributes().get("response"));
        }else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
        	System.out.println("카카오 로그인 요청");
        	oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>)oauth2User.getAttributes());
        }
		
		
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String userId  = provider+ "_" + providerId;
		String userPw = encodedPwd().encode("겟인데어");
		String userNm = oAuth2UserInfo.getName();
		String userEmail = oAuth2UserInfo.getEmail();
		String role = "ROLE_ADMIN";
		
		
		
		User u = userMapper.getUserInfoById(userId);
		System.out.println(u);
		//회원 가입
		if(u == null) {
			u = User.builder()
				.userId(userId)
				.userPw(userPw)
				.userNm(userNm)
				.userEmail(userEmail)
				.role(role)
				.provider(provider)
				.providerId(providerId)
				.build();
			userMapper.addUser(u);
			
		}
		
		
		return new PrincipalDetails(u,oauth2User.getAttributes());
	}

}
