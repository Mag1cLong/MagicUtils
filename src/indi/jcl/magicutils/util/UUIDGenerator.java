package indi.jcl.magicutils.util;

import java.util.UUID;

/**
 * 32位uuid生成器
 *
 * @author jcl
 * @since 2015-08-28
 */
public class UUIDGenerator {
    public static final String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
