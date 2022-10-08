package com.rico.uaa.service;

import com.rico.comm.api.Result;
import com.rico.comm.exception.CaptchaException;


/**
 * 验证码业务类
 *
 * @author rico
 */
public interface ValidateService {


	/**
	 * 获取验证码
	 *
	 * @return Result
	 */
	Result<?> getCode();

	/**
	 * 获取短信验证码
	 *
	 * @param mobile 手机号码
	 * @return Result
	 */
	Result<?> getSmsCode(String mobile);

	/**
	 * 校验验证码
	 *
	 * @param key  　KEY
	 * @param code 验证码
	 * @throws CaptchaException 验证码异常
	 */
	void check(String key, String code) throws CaptchaException;
}
