package com.rico.comm.service.impl;

import com.rico.comm.entity.BlackList;

import java.util.Set;

/**
 * 规则缓存业务
 *
 * @author rico
 */
public interface IRuleCacheService {

	/**
	 * 根据IP获取黑名单
	 *
	 * @param ip 　ip
	 * @return Set
	 */
	Set<Object> getBlackList(String ip);

	/**
	 * 查询所有黑名单
	 *
	 * @return Set
	 */
	Set<Object> getBlackList();

	/**
	 * 设置黑名单
	 *
	 * @param blackList 黑名单对象
	 */
	void setBlackList(BlackList blackList);

	/**
	 * 删除黑名单
	 *
	 * @param blackList 黑名单对象
	 */
	void deleteBlackList(BlackList blackList);
}
