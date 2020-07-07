package com.zxd.report.mapper;

import com.zxd.commonmodel.Page;
import com.zxd.report.model.StDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StDictionaryMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(StDictionary record);

    int insertSelective(StDictionary record);

    StDictionary selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(StDictionary record);

    int updateByPrimaryKey(StDictionary record);

    List<StDictionary> selectDictionaryType(@Param("itemCode") String itemCode);

    List<StDictionary> selectDictionaryDesc(Integer id);

    List<Map> selectDepttree();
    List<StDictionary> selectbyfatherId(@Param(value = "pId") String pId);
    int selectByitemcode(@Param(value = "itemcode") String itemcode, @Param(value = "pId") String pId);
    //获取指定itemconde的一级节点 下的子节点
    List<StDictionary> getDicsbyItem(@Param(value = "itemcode") String itemcode);
    int selectByCount(@Param("model") Page page, @Param("map") Map map);
    List<StDictionary> selectByList(@Param("model") Page page, @Param("map") Map map);
    StDictionary selectBypandcode(@Param(value = "itemcode") String itemcode, @Param(value = "pitemcode") String pitemcode);
}