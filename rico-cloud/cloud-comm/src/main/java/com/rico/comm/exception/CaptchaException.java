package com.rico.comm.exception;

/**
 * 验证码异常
 *
 * @author rico
 */
public class CaptchaException extends Exception {

	private static final long serialVersionUID = -6550886498142636261L;

	public CaptchaException(String message) {
		super(message);
	}
}
