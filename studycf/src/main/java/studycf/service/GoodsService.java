package studycf.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.Goods;
import studycf.mapper.GoodsMapper;


@Service
@Transactional
public class GoodsService {

	// 생성자 주입
	private final GoodsMapper goodsMapper;

	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

	//이용권 수정
	public int modifyGoods(Goods goods) {
		int result = goodsMapper.modifyGoods(goods);
		return result;
		
	}
	
	//이용권 상세조회
	public Goods getGoodsInfoByCd(String goodsCd) {
		Goods goods = goodsMapper.getGoodsInfoByCd(goodsCd);
		log.info(goods + "goodsService/getGoodsInfoByCd");
		return goods;
	}
	
	//이용권 등록
	public int addGoods(Goods goods) {
		
		int result = goodsMapper.addGoods(goods);
		return result;
	}
	
	//이용권 목록
	public List<Goods> getGoodsList(String goodsCtgCd){
		
		List<Goods> goodsList = goodsMapper.getGoodsList(goodsCtgCd);
		log.info("이용권 리스트 서비스");
	
		return goodsList;
	}
	
	


}
