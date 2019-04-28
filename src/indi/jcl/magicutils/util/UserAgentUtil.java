package indi.jcl.magicutils.util;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * UserAgent工具类
 * Created by jcl on 2019/4/28
 */
public class UserAgentUtil {

    /**
     * 获取浏览器
     * @param userAgent
     * @return
     */
    public static String getBrowser(String userAgent) {
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        return ua.getBrowser().getName() + "(" + ua.getBrowserVersion().getVersion() + ")";
    }

    /**
     * 获取操作系统
     * @param userAgent
     * @return
     */
    public static String getOS(String userAgent) {
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        return ua.getOperatingSystem().getName();
    }
}
