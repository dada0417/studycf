package studycf.mapper;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.User;

@Mapper
public interface UserMapper {
	
	//회원정보조회
	public User getUserInfoById(String userId);
}
