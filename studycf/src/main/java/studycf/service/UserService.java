package studycf.service;


import java.util.List;

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
	
	
	/*회원정보 수정*/
	public int modifyUser(User user) {
		int result = userMapper.modifyUser(user);
		return result;
	}
	
	/* 회원가입 */
	public int addUser(User user) {
		int result = userMapper.addUser(user);
		return result;
	}
	
	
	/* 아이디 중복 체크 */
	public boolean isIdCheck(String userId) {
		boolean result = userMapper.isIdCheck(userId);
		return result;
	}
	
	
	/**
	 * 비밀번호 찾기
	 */
	
	public String isIdCheck2(String userId) {
		log.info(userId);
		String result = userMapper.isIdCheck2(userId);
		
		return result;
	}
	
	/**아이디찾기*/
	public String isPhoneCheck(String userPhone) {

		String result = userMapper.isPhoneCheck(userPhone);

		return result;
	}
	
	

	/**
	 * *회원상세정보
	 * */
	public User getUserInfoById(String userId) {
		User user = userMapper.getUserInfoById(userId);
		log.info(userId);
		return user;
	}













}
