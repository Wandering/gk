package cn.thinkjoy.gk.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;


public class CookieUtil {
	
	/**
	 * 生成cookie
	 * @param name cookie的名称
	 * @param value cookie的值
	 * @param expiry cookie的有效期
	 * @return
	 */
	public static final Cookie addCookie(String name, String value, int expiry ,String path){
		Cookie cookie = new Cookie(name, value);
//		cookie.setDomain("*");
		if (StringUtils.isNotBlank(path)) {
			cookie.setPath(path);
		}
		cookie.setMaxAge(expiry);
		return cookie;
	}
	public static final Cookie addCookie(String name, String value, int expiry){
		return addCookie(name,value,expiry,null);
	}

	/**
	 * 获取用户cookie信息
	 * 
	 * @return String
	 */
	public static String getCookieValue(Cookie[] cookies, String name) {
		String value = null;
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					value = cookies[i].getValue();
				}
			}
		}
		return value;
	}

}
