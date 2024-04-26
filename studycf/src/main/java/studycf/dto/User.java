package studycf.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
	

	private String userId;
	private String userPw;
	private String userNm;
	private String userPhone;
	private String userEmail; 
	private String userDrop;
	private String userRegDate;
	private String role;
	
	private String provider;
	private String providerId;

	@Builder
	public User (String userId, String userPw, String userNm, String userPhone, String userEmail, String userDrop,
			String userRegDate, String role, String provider, String providerId) {
		this.userId = userId;
		this.userPw = userPw;
		this.userNm = userNm;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userDrop = userDrop;
		this.userRegDate = userRegDate;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
	}




	private GoodsManagement goodsManagement;


	



	
}
