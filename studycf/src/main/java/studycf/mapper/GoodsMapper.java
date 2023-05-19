package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Goods;

@Mapper
public interface GoodsMapper {

	public List<Goods> getGoodsList();
}
