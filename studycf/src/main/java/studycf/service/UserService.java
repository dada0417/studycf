package studycf.service;


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
