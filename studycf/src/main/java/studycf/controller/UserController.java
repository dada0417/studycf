package studycf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.dto.User;
import studycf.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	public final UserService userService;
	
	public UserController(UserService userService) {
		this.userService	=	userService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	//회원 가입
	@PostMapping("/addUser")
	public String addUser(User user) {
		
		log.info("회원가입폼 시작");
		
		log.info("회원가입폼에서 입력받은 데이터:{}", user);
		
		int result = userService.addUser(user);
		
		log.info("result : {}", result);
		
		return "redirect:/";
	}
	
	//회원 가입 페이지 이동
	@GetMapping ("/addUser")
	public String addUser(Model model) {
		
		model.addAttribute("title", "회원가입");
		
		return "user/addUser";
	}
	
	
	
	// 아이디 중복체크 여부
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean isIdCheck(@RequestParam(value = "userId") String userId) {
		boolean idCheck = false;
		log.info("아이디중복체크 클릭시 요청받은 userId의 값: {}", userId);
		
		boolean result = userService.isIdCheck(userId);
		if(result) idCheck = true;
		
		log.info("아이디중복체크 여부 : {}", result);
		return idCheck;
	}
	
}
