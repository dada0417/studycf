package studycf.mapper;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.User;

@Mapper
public interface UserMapper {
	
	//회원 아이디 찾기
	public String isPhoneCheck(String userPhone);

	//회원 비밀번호 찾기
	public String isIdCheck2(String userId);
	
	//회원정보조회
	public User getUserInfoById(String userId);
}
