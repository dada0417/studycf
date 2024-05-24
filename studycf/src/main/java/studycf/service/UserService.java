package studycf.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.User;
import studycf.mapper.UserMapper;

@Service
@Transactional
public class UserService {
	
	//생성자 주입 
	private final UserMapper userMapper;

	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
		}
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	public List<User> userList(User user){
		List<User> result = userMapper.userList(user);
		return result;
	}
	
	//회원 검색
	public Map<String, Object> searchUser(String searchKey, String searchValue){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		 
		log.info("search : {}", searchKey);
		
		paramMap.put("searchKey", searchKey);
		paramMap.put("searchValue", searchValue);
		
		List<Map<String, Object>> searchList = userMapper.searchUser(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("searchList", searchList);
	
		
		return resultMap;
	}
	
	
	//회원정보 수정
	public int modifyUser(User user) {
		int result = userMapper.modifyUser(user);
		return result;
	}
	
	//회원가입
	public int addUser(User user) {
		int result = userMapper.addUser(user);
		return result;
	}
	
	//아이디 중복 체크 
	public boolean isIdCheck(String userId) {
		boolean result = userMapper.isIdCheck(userId);
		return result;
	}
	
	//비밀번호 찾기
	public String isIdCheck2(String userId) {
		log.info(userId);
		String result = userMapper.isIdCheck2(userId);
		
		return result;
	}
	
	//아이디 찾기
	public String isPhoneCheck(String userPhone) {

		String result = userMapper.isPhoneCheck(userPhone);

		return result;
	}

	//회원 상세 정보 조회
	public User getUserInfoById(String userId) {
		User user = userMapper.getUserInfoById(userId);
		log.info(userId);
		return user;
	}

}
