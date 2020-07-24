package com.zxd.report.service.impl;/**
 * Created by Think on 2016/8/18.
 */


import com.zxd.commonmodel.Page;
import com.zxd.report.mapper.StParamsMapper;
import com.zxd.report.model.StParams;
import com.zxd.report.service.StParamsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class StParamsServiceImpl implements StParamsService {

    @Resource
    private StParamsMapper itemMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public StParams getParamsValue(String paramsKey) {
        return itemMapper.getParamsValue(paramsKey);
    }

    @Override
    public List<StParams> selectByList(Page page) {
        return itemMapper.selectByList(page);
    }

    @Override
    public int selectByCount() {
        return itemMapper.selectByCount();
    }

    @Override
    public int insert(StParams stparams) {
        return itemMapper.insertSelective(stparams);
    }

    @Override
    public int updateByPrimaryKey(StParams stparams) {
        String applyConfKey = "CACHE:PARAM";
        redisTemplate.opsForHash().put(applyConfKey,stparams.getPARM_KEY(),stparams.getPARM_VALUE());
        return itemMapper.updateByPrimaryKeySelective(stparams);
    }

    /**
     * 根据key在redis缓存中读取，如果存在就取出来，否则从数据库里面取并放入redis中
     * @param key
     * @return
     */
    @Override
    public String getParamConf(String key) {
        String applyConfKey = "CACHE:PARAM";

        if(StringUtils.isEmpty(key)){
            return null;
        }else{
            if(redisTemplate.hasKey(applyConfKey)){
                if(redisTemplate.opsForHash().hasKey(applyConfKey, key)){
                    return redisTemplate.opsForHash().get(applyConfKey, key).toString();
                }else{
                    List<StParams> stparams = itemMapper.selectForKey(key);
                    redisTemplate.opsForHash().put(applyConfKey, key, stparams.get(0).getPARM_VALUE());
                    return stparams.get(0).getPARM_VALUE();
                }
            }else{
                List<StParams> stparams = itemMapper.selectForKey(null);
                for (StParams p: stparams) {
                    //放入redis中
                    redisTemplate.opsForHash().put(applyConfKey, p.getPARM_KEY(), p.getPARM_VALUE());
                }
            }
            return  redisTemplate.opsForHash().get(applyConfKey, key).toString();
        }
    }
}
