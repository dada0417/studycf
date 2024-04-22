package studycf.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//로그아웃 처리
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	//아이디 찾기
		@PostMapping("/loginId")
		@ResponseBody
		public String isPhoneCheck(@RequestParam(name = "userPhone" , required = false) String userPhone) {
			
			log.info("아이디 찾기 클릭시 요청받은 userPhone의 값: {}", userPhone);
			
			String result = userService.isPhoneCheck(userPhone);
			log.info(result);
			return result;
		}
		//아이디 찾기 화면
		@GetMapping("/loginId")
		public String loginId(Model model) {
			model.addAttribute("title", "아이디 찾기");
			return "login/loginId";
		}	
		
		//비밀번호 찾기
		@PostMapping("/loginPw")
		@ResponseBody
		public String isIdCheck2(@RequestParam(name = "userId" , required = false) String userId) {
			
			log.info("비밀번호 찾기 클릭시 요청받은 userId의 값: {}", userId);
			
			String result = userService.isIdCheck2(userId);

			return result;
		}
		//비번 찾기 화면
		@GetMapping("/loginPw")
		public String loginPw(Model model) {
			model.addAttribute("title", "비밀번호 찾기");
			return "login/loginPw";
		}	
	
	
		
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "로그인");
		return "login/login";
	}
	
}
