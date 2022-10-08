package com.rico.comm.context;


import com.rico.comm.entity.LoginUser;


/**
 * 用户上下文
 *
 * @author rico
 */
public class UserContext {

	private static ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

	public static void setUser(LoginUser loginUser) {
		userHolder.set(loginUser);
	}

	public static LoginUser getUser() {
		return userHolder.get();
	}
}
