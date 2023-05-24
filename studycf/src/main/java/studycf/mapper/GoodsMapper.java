package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Goods;

@Mapper
public interface GoodsMapper {


	//이용권 수정
	public int modifyGoods(Goods goods);
	
	public Goods getGoodsInfoByCd(String goodsCd, String goodsCtgCd);
	
	//이용권 등록
	public int addGoods(Goods goods);

	//이용권 리스트
	public List<Goods> getGoodsList();
}
