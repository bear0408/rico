package com.rico.comm.service;

import com.alibaba.fastjson2.JSONObject;

import com.rico.comm.RedisService;
import com.rico.comm.constant.RuleConstant;


import com.rico.comm.entity.BlackList;
import com.rico.comm.service.impl.IRuleCacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Set;

/**
 * 规则缓存实现业务类
 * @author rico
 */
@Service
public class RuleCacheServiceImpl implements IRuleCacheService {

    @Autowired
    private RedisService redisService;

    @Override
    public Set<Object> getBlackList(String ip) {
        return redisService.sGet(RuleConstant.getBlackListCacheKey(ip));
    }

    @Override
    public Set<Object> getBlackList() {
        return redisService.sGet(RuleConstant.getBlackListCacheKey());
    }

    @Override
    public void setBlackList(BlackList blackList) {
        String key = StringUtils.isNotBlank(blackList.getIp()) ? RuleConstant.getBlackListCacheKey(blackList.getIp())
                : RuleConstant.getBlackListCacheKey();
        redisService.sSet(key, JSONObject.toJSONString(blackList));
    }

    @Override
    public void deleteBlackList(BlackList blackList) {
        String key = StringUtils.isNotBlank(blackList.getIp()) ? RuleConstant.getBlackListCacheKey(blackList.getIp())
                : RuleConstant.getBlackListCacheKey();
        redisService.setRemove(key, JSONObject.toJSONString(blackList));
    }
}
