package indi.jcl.magicutils.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * Created by jcl on 2019/4/28
 */
public class CookieUtil {
	/**
	 * 根据名字获取cookie
	 * @param request 请求
	 * @param cookieName cookie名
	 * @return 返回cookie的值
	 */
	public static String get(HttpServletRequest request, String cookieName) {
		String cookieValue = null;
		Cookie[] cookies = request.getCookies();
		// 如果用户是第一次访问，那么得到的cookies将是null
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(cookieName)) {
					cookieValue = cookie.getValue();
				}
			}
		}
		return cookieValue;
	}


	/**
	 * 设置cookie
	 * @param cookieName cookie名
	 * @param cookieValue cookie的值
	 * @param cookietime cookie的有效时间(单位秒)，如果时间为0或小于0，则默认当次有效
	 * @param domain 存储的域名，为空传null
	 * @param path 存储的路径，为空传null
	 * @return 返回设置状态 true or false
	 */
	public static boolean set(HttpServletResponse response, String cookieName, String cookieValue, int cookietime,
			String domain, String path) {
		try {
			// 用户访问过之后重新设置用户的访问时间，存储到cookie中，然后发送到客户端浏览器
			Cookie cookie = new Cookie(cookieName, cookieValue);
			// 判断是否创建域名
			if (domain != null) {
				cookie.setDomain(domain);
			}
			// 判断是否有路径
			if (path == null) {
				path = "/";
			}
			// 设置cookie基本信息
			cookie.setPath(path);
			// 判断是否有时效
			if (cookietime > 0) {
				cookie.setMaxAge(cookietime);
			}
			response.addCookie(cookie);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除cookie
	 * @param cookieName cookie名
	 * @param request 请求
	 * @param path 路径
	 * @param response 返回
	 * @return 返回设置状态 true or false
	 */
	public static boolean remove(HttpServletRequest request, HttpServletResponse response,
			String cookieName,String path) {
		Boolean resultState = false;
		try {
			Cookie[] cookies = request.getCookies();
			// 如果用户是第一次访问，那么得到的cookies将是null
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals(cookieName)) {
						cookie.setMaxAge(0);// 清空cookie
						cookie.setPath(path);
						response.addCookie(cookie);
						resultState = true;
					}
				}
			}
			return resultState;
		} catch (Exception e) {
			return false;
		}
	}
}
