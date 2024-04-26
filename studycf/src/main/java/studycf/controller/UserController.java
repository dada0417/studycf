package studycf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.config.SecurityConfig;
import studycf.config.auth.PrincipalDetails;
import studycf.config.auth.PrincipalOauth2UserService;
import studycf.dto.GoodsManagement;
import studycf.dto.Order;
import studycf.dto.Seat;
import studycf.dto.User;
import studycf.service.GoodsManagementService;
import studycf.service.OrderService;
import studycf.service.SeatService;
import studycf.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	public final UserService userService;
	public final GoodsManagementService goodsManagementService;
	public final OrderService orderService;
	public final SeatService seatService;
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	public UserController(UserService userService, GoodsManagementService goodsManagementService,OrderService orderService,SeatService seatService, PrincipalOauth2UserService principalOauth2UserService) {
		this.userService	=	userService;
		this.goodsManagementService	=	goodsManagementService;
		this.orderService	=	orderService;
		this.seatService	=	seatService;
		this.principalOauth2UserService	=	principalOauth2UserService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	//회원 전체 목록
	@GetMapping("/userList")
	public String userList(User user, Model model){
		List<User> userList = userService.userList(user);
		model.addAttribute("userList", userList);
		model.addAttribute("title", "회원 목록");
		return "user/userList";
	}
	
	//관리자가 회원 대신 퇴장 처리
	@PostMapping("/userDetail2")
	public String userListById(String userId, Seat seat, GoodsManagement goodsManagement, Order order) {
		goodsManagementService.modifyGM(goodsManagement);
		orderService.modifyOrder(order);
		seatService.seatSelection(seat);
		
		
		return "redirect:/user/userDetail2?userId="+userId;
	}
	
	//관리자가 회원 이용상세정보 조회
		@Secured("ROLE_MANAGER")
		@GetMapping("/userDetail2")
		public String userListById(String userId, Model model
									,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
			//이용가능 이용권 목록
			List<GoodsManagement> availableGoodsList = goodsManagementService.availableGoodsListById(userId);
			//현재 이용중인 이용내역
			GoodsManagement useById = goodsManagementService.getUseById(userId);
			
			//카페 총 이용시간
			String totalTime = goodsManagementService.getTotalTime(userId);
			
			Map<String, Object> goodsManagementMap = new HashMap<>();

			goodsManagementMap.put("userId", userId);
			//카페 이용 전체 내역
			Map<String, Object> resultMap = goodsManagementService.usageListById(currentPage, goodsManagementMap);
			
			log.info("currentPage : {}", currentPage);
			
			model.addAttribute("title", "회원이용상세정보");
			model.addAttribute("resultMap", 			resultMap);
			model.addAttribute("currentPage", 			currentPage);
			model.addAttribute("usageListById",			resultMap.get("usageListById"));
			model.addAttribute("lastPage", 				resultMap.get("lastPage"));
			model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
			model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
			model.addAttribute("totalTime", totalTime);
			model.addAttribute("availableGoodsList", availableGoodsList);
			model.addAttribute("useById", useById);
			
			log.info("map확인 : {}", goodsManagementMap);
			log.info("totalTime확인 : {}", totalTime);
			
			
			return "user/userDetail2";
		}
	
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
	public String getUserInfoById(@AuthenticationPrincipal PrincipalDetails principal, Model model
								,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		String sessionId = (String)principal.getUsername();
		

		
		log.info("회원정보조회 아이디 : {}", sessionId);
		User user = userService.getUserInfoById(sessionId);
		//이용가능한 이용권 목록
		List<GoodsManagement> availableGoodsList = goodsManagementService.availableGoodsListById(sessionId);
		
		//카페 총 이용시간
		String totalTime = goodsManagementService.getTotalTime(sessionId);
		
		Map<String, Object> goodsManagementMap = new HashMap<>();

		goodsManagementMap.put("sessionId", sessionId);
		//카페 이용내역
		Map<String, Object> resultMap = goodsManagementService.usageListById(currentPage, goodsManagementMap);
		
		log.info("currentPage : {}", currentPage);
		
		model.addAttribute("title", "회원상세정보");
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("usageListById",			resultMap.get("usageListById"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("user", user);
		model.addAttribute("totalTime", totalTime);
		model.addAttribute("availableGoodsList", availableGoodsList);
		
		log.info("map확인 : {}", goodsManagementMap);
		log.info("totalTime확인 : {}", totalTime);
		
		
		return "user/userDetail";
	}
	
	//회원 가입
	@PostMapping("/addUser")
	public String addUser(User user) {
		String rawPw = user.getUserPw();
		String encPw = principalOauth2UserService.encodedPwd().encode(rawPw);
		user.setUserPw(encPw);
		user.setRole("ROLE_ADMIN");
		log.info("회원가입폼에서 입력받은 데이터:{}", user);
		
		int result = userService.addUser(user);
		
		log.info("result : {}", result);
		
		return "redirect:/login";
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
