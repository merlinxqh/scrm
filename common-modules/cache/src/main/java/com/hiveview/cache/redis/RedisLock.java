package com.hiveview.cache.redis;

/**
 * redis lock
 * @author leo
 * redis实现分布式锁
 */
public class RedisLock {

    private RedisService redisService;

    /**
     * 锁的key
     */
    private String lockKey;

    /**
     * 过期时间(单位为：毫秒)
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 超时时间(单位为：毫秒)
     */
    private int timeoutMsecs = 30 * 1000;

    /**
     * 等待时间(单位为：毫秒)
     */
    private int sleepMsecs = 100;

    /**
     * 锁的标识
     * 默认false表示没有获取锁，true表示获取到锁
     */
    private boolean locked = false;

    private int parentHashCode = 0;

    public RedisLock(RedisService redisService, String lockKey) {
        this.redisService = redisService;
        this.lockKey = lockKey;
    }

    public RedisLock(RedisService redisService, String lockKey, int timeoutMsecs) {
        this(redisService, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(RedisService redisService, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisService, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public RedisLock(RedisService redisService, String lockKey, int timeoutMsecs, int expireMsecs, int sleepMsecs) {
        this(redisService, lockKey, timeoutMsecs, expireMsecs);
        this.sleepMsecs = sleepMsecs;
    }

    public RedisLock(String lockKey) {
        this(null, lockKey);
    }

    public RedisLock(String lockKey, int timeoutMsecs) {
        this(null, lockKey, timeoutMsecs);
    }

    public RedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
        this(null, lockKey, timeoutMsecs, expireMsecs);
    }

    public RedisLock(String lockKey, int timeoutMsecs, int expireMsecs, int sleepMsecs) {
        this(null, lockKey, timeoutMsecs, expireMsecs, sleepMsecs);
    }

    public String getLockKey() {
        return lockKey;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getParentHashCode() {
        return parentHashCode;
    }

    public void setParentHashCode(int parentHashCode) {
        this.parentHashCode = parentHashCode;
    }

    /**
     * 获取锁
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean acquire() throws InterruptedException {
        return acquire(redisService);
    }

    /**
     * 获取锁
     * @param redisService
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean acquire(RedisService redisService) throws InterruptedException {
        int timeout = timeoutMsecs;

        //循环判断超时时间
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);

            if (redisService.setNX(lockKey, expiresStr)) {
                //获取锁
                locked = true;
                return true;
            }

            String currentValueStr = redisService.getString(lockKey);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //锁过期
                String oldValueStr = redisService.getAndSet(lockKey, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //再次获取锁
                    locked = true;
                    return true;
                }
            }

            timeout -= sleepMsecs;
            //wait(sleepMsecs);
            Thread.sleep(sleepMsecs);
        }

        return false;
    }

    /**
     * 释放锁
     */
    public synchronized void release() {
        release(redisService);
    }

    /**
     * 释放锁
     */
    public synchronized void release(RedisService redisService) {
        if (locked) {
            redisService.deleteByKey(lockKey);
            locked = false;
        }
    }
}
