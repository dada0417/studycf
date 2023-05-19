package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsManagement {
	private String goodsManagementCd; 
	private String userId;
	private String goodsCd;
	private String deatCd;
	private String sdmissionTime;
	private String leaveTime;
	private String useTime;
	private String remainingTime;
	private String goodsEpirationDate;
	
}
