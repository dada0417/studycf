package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsOrder {
	private String OrderCd;
	private String UserId;
	private String GoodsCd;
	private String Price;
	private String OrderExpirationDate;
	private String OrderInsertDate;
}
