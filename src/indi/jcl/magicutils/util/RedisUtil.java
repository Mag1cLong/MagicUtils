package indi.jcl.magicutils.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * redis工具类
 * Created by jcl on 2019/4/3
 */
public enum RedisUtil {
    INSTANCE;
    public static final String IP = "192.168.2.83";
    public static final int PORT = 6379;
    private JedisPool jedisPool;


    RedisUtil() {
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(500);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);

        //redis如果设置了密码：
                     /*jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,
                     JRedisPoolConfig.REDIS_PORT,
                     10000,JRedisPoolConfig.REDIS_PASSWORD);    */

        //redis未设置了密码：
        jedisPool = new JedisPool(config, IP, PORT);
    }

    /**
     * 设置key:value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String val = jedis.get(key);
        jedis.close();
        return val;
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key, seconds);
        jedis.close();
    }

    /**
     * 从list右侧弹出一个元素，没有数据则阻塞
     *
     * @param timeout 0代表一直等待
     * @param key
     * @return
     */
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.brpop(timeout, key);
        jedis.close();
        return list;
    }

    /**
     * 从list左侧推入一个元素
     *
     * @param key
     * @param val
     */
    public Long lpush(String key, String... val) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.lpush(key, val);
        jedis.close();
        return l;
    }

    /**
     * 获取列表
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public List<String> lrange(String key, int start, int stop) {
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.lrange(key, start, stop);
        jedis.close();
        return list;
    }

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    public long llen(String key) {
        Jedis jedis = jedisPool.getResource();
        long i = jedis.llen(key);
        jedis.close();
        return i;
    }


}
