package com.zxd.report.service.impl;


import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StDictionaryMapper;
import com.zxd.report.model.StDictionary;
import com.zxd.report.service.DictService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl implements DictService {

    @Resource
    private StDictionaryMapper stDictionaryMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Map> selectDepttree() {
        return stDictionaryMapper.selectDepttree();
    }

    @Override
    public int insertSelective(StDictionary record) {
        return stDictionaryMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(StDictionary record) {
        return stDictionaryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<StDictionary> selectbyfatherId(String pId) {
        return stDictionaryMapper.selectbyfatherId(pId);
    }

    @Override
    public int selectByitemcode(String itemcode,String pId) {
        return stDictionaryMapper.selectByitemcode(itemcode,pId);
    }

    @Override
    public int selectByCount(Page page,Map map) {
        return stDictionaryMapper.selectByCount(page,map);
    }

    @Override
    public List<StDictionary> selectByList(Page page, Map map) {
        return stDictionaryMapper.selectByList(page,map);
    }

    @Override
    public StDictionary selectBypandcode(String itemcode, String pitemcode) {
        return stDictionaryMapper.selectBypandcode(itemcode,pitemcode);
    }

    @Override
    public StDictionary selectByPrimaryKey(Integer ID) {
        return stDictionaryMapper.selectByPrimaryKey(ID);
    }

    @Override
    public String getDictionary(String key) {
        String dictionaryKey = "CACHE:DICTIONARY";
        if (StringUtils.isEmpty(key)) {
            return null;
        } else {
            if (redisTemplate.hasKey(dictionaryKey)) {
                if (redisTemplate.opsForHash().hasKey(dictionaryKey, key)) {
                    return redisTemplate.opsForHash().get(dictionaryKey, key).toString();
                }else{
                    List<StDictionary> typeList = stDictionaryMapper.selectDictionaryType(key);
                    if(typeList.size()>0){
                        List<StDictionary> descList = stDictionaryMapper.selectDictionaryDesc(typeList.get(0).getID());
                        StringBuffer putString = new StringBuffer("[");
                        for (StDictionary stDesc : descList) {
                            putString.append("{ITEM_CODE:'").append(stDesc.getITEM_CODE()).append("',ITEM_NAME:'").append(stDesc.getITEM_NAME()).append("'},");
                        }
                        String value = putString.toString().substring(0, putString.toString().length() - 1) + "]";
                        redisTemplate.opsForHash().put(dictionaryKey, typeList.get(0).getITEM_CODE(), value);
                        return value;
                    }else{
                        return null;
                    }
                }
            } else {
                List<StDictionary> typeList = stDictionaryMapper.selectDictionaryType(null);
                for (StDictionary stType : typeList) {
                    List<StDictionary> descList = stDictionaryMapper.selectDictionaryDesc(stType.getID());
                    StringBuffer putString = new StringBuffer("[");
                    for (StDictionary stDesc : descList) {
                        putString.append("{ITEM_CODE:'").append(stDesc.getITEM_CODE()).append("',ITEM_NAME:'").append(stDesc.getITEM_NAME()).append("'},");
                    }
                    String value = putString.toString().substring(0, putString.toString().length() - 1) + "]";
                    redisTemplate.opsForHash().put(dictionaryKey, stType.getITEM_CODE(), value);
                }
                return redisTemplate.opsForHash().get(dictionaryKey, key).toString();
            }
        }
    }
}
