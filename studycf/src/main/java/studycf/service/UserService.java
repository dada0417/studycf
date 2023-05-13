package studycf.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import studycf.dto.User;
import studycf.mapper.UserMapper;

@Service
public class UserService {
	
	//생성자 주입 
	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
		}
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);


	/**
	 * *회원상세정보
	 * */
	public User getUserInfoById(String userId) {
		User user = userMapper.getUserInfoById(userId);
		log.info(userId);
		return user;
	}













}
