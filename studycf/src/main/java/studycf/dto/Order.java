package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {
	private String orderCd;
	private String userId;
	private String goodsCd;
	private String giveTime;
	private String goodsCtgCd;
	private String price;
	private String orderExpirationDate;
	private String orderInsertDate;
	
	private GoodsManagement goodsManagement;
	
}
