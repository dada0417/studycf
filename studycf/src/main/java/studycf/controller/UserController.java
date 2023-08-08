package studycf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.dto.GoodsManagement;
import studycf.dto.User;
import studycf.service.GoodsManagementService;
import studycf.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	public final UserService userService;
	public final GoodsManagementService goodsManagementService;
	
	public UserController(UserService userService, GoodsManagementService goodsManagementService) {
		this.userService	=	userService;
		this.goodsManagementService	=	goodsManagementService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	//회원 비밀번호 변경
	@PostMapping("/modifyUserPw")
	public String modifyUserPw(User user
							  ,Model model
							  ,@RequestParam(name="nowUserPwCheck") String nowUserPwCheck) {
		
		log.info("비밀번호 변경 회원정보:{}", user);
		
		User userCheck = userService.getUserInfoById(user.getUserId());
		
		if(nowUserPwCheck.equals(userCheck.getUserPw())) {
			userService.modifyUser(user);
			return "redirect:/user/userDetail";
			
		}else {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("result", "비밀번호가 일치하지 않습니다.");
			
			return "user/modifyUserPw";
		}
		
	}
	
	//회원 비밀번호 변경 페이지 이동
	@GetMapping("/modifyUserPw")
	public String modifyUserPw(Model model
							  ,@RequestParam(name="userId",required = false) String userId) {
		
		User user = userService.getUserInfoById(userId);
		
		log.info("회원 비밀번호 변경 아이디 : {}", userId);
		
		model.addAttribute("title", "비밀번호 변경");
		model.addAttribute("user", user);
		
		return "user/modifyUserPw";
	}
	
	//회원정보수정
	@PostMapping("/userDetail")
	public String modifyUser(User user) {
		log.info("수정 내용", user);
		userService.modifyUser(user);
		return "user/userDetail";
	}
	
	//회원상세정보
	@GetMapping("/userDetail")
	public String getUserInfoById(HttpSession session, Model model
								,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		String sessionId = (String)session.getAttribute("SID");
		
		log.info("회원정보조회 아이디 : {}", sessionId);
		User user = userService.getUserInfoById(sessionId);
		List<GoodsManagement> availableGoodsList = goodsManagementService.availableGoodsListById(sessionId);
		Map<String, Object> resultMap = goodsManagementService.usageListById(currentPage);
		
		
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("usageList",		resultMap.get("usageList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("title", "회원상세정보");
		model.addAttribute("user", user);
		model.addAttribute("availableGoodsList", availableGoodsList);
		return "user/userDetail";
	}
	
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
