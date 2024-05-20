package studycf.config.auth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException{
		String errorMessage="";
		
		
		if(exception instanceof BadCredentialsException) {
			errorMessage= "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		}else if(exception instanceof UsernameNotFoundException) {
			errorMessage = "계정이 존재하지 않습니다. 회원가입 후 로그인해주세요.";	
		}else {
			errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
		}
		
		String encode = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception=" + encode);
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
