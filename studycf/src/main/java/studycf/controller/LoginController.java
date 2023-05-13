package studycf.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import studycf.dto.User;
import studycf.service.UserService;

@Controller
public class LoginController {
	//생성자 주입
	private final UserService userService;
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	
	//로그인
	@PostMapping("/")
	public String login( @RequestParam(name = "userId", required = false) String userId
						,@RequestParam(name = "userPw", required = false) String userPw
						,HttpSession session) {
		
		User user = userService.getUserInfoById(userId);
		
		if(user != null) {
			String userPwCheck = user.getUserPw();
			if(user != null && userPwCheck.equals(userPw)) {
				session.setAttribute("SID"		, userId);
				session.setAttribute("SNAME"	, user.getUserNm());
				
				// 이전 destination 불러오기
				// response.sendRedirect("/");        
				Object dest = (String)session.getAttribute("dest");
				log.info("dest : {}", dest);
				if(dest != null) {
					// 이전 destination으로 리디렉트
					return "redirect:"+dest;
				}
				return "/";
			}
		}
		
		return "/";
	}
	
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("title", "로그인");
		return "/";
	}
	
}
