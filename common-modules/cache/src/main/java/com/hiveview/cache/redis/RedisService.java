package com.hiveview.cache.redis;


import com.hiveview.cache.utils.ProtostuffSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * jedis工具类
 * Created by mike on 16-4-21.
 */
public class RedisService {
    private static Log log = LogFactory.getLog(RedisService.class);

    private static final String CHARSET_NAME = "UTF-8";

    private ShardedJedisPool shardedJedisPool;

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    /**
     * 设置 字符串类型缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @returngetkeyget
     */
    public void setString(String key, String value, int cacheSeconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            if (cacheSeconds != 0 ) {
                shardedJedis.expire(key, cacheSeconds);

            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 如果当前有key  重新设值 返回原来的value
     * 如果没有 返回null
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(String key,String value){
        ShardedJedis shardedJedis = null;
        String result=null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result=shardedJedis.getSet(key, value);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * set if not exists
     * 当前redis不存在这个key时 可以设置 返回true
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(String key,String value){
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = shardedJedisPool.getResource();
            Long result=shardedJedis.setnx(key, value);
            if(result>0){
                return true;
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return false;
    }

    /**
     * 设置超时时间
     * @param key
     * @param cacheSeconds
     */
    public void expire(String key, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (cacheSeconds != 0 ) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 设置 字符串类型缓存
     * @param key
     * @param value
     */
    public void setString(String key, String value){
        setString(key,value,0);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            String value = shardedJedis.get(key);
            return value;
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 向缓存中设置对象
     * @param key
     * @return
     */
    public <T> void setObject(String key,T obj){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(getBytesKey(key), ProtostuffSerializer.serialize(obj));
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 直接存 字节
     * @param key
     * @param bytes
     */
    public void setObjectBytes(String key,byte[] bytes){
        setObjectBytes(key,bytes,0);
    }

    /**
     * 直接存 字节
     * @param key
     * @param bytes
     * @param cacheSeconds
     */
    public void setObjectBytes(String key,byte[] bytes, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(getBytesKey(key), bytes);
            if(cacheSeconds != 0){
                shardedJedis.expire(getBytesKey(key), cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 取字节
     * @param key
     * @return
     */
    public byte[] getObjectBytes(String key){
        byte[] result=null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result=shardedJedis.get(getBytesKey(key));
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public <T> void setObject(String key, T obj, int cacheSeconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(getBytesKey(key), ProtostuffSerializer.serialize(obj));
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 根据key 获取对象
     * @param key
     * @return
     */
    public <T> T getObject(String key,Class<T> targetClass){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            byte[] bytes = shardedJedis.get(getBytesKey(key));
            return ProtostuffSerializer.deserialize(bytes, targetClass);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 缓存 对象集合
     * @param key
     * @param list
     */
    public <T> void setListObj(String key, List<T> list){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(getBytesKey(key), ProtostuffSerializer.serializeList(list));
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 缓存对象集合
     * @param key
     * @param list
     * @param cacheSeconds
     */
    public <T> void setListObj(String key, List<T> list, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(getBytesKey(key), ProtostuffSerializer.serializeList(list));
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 从缓存中获取 List 对象
     * @param key
     * @param targetClass
     * @return
     */
    public <T> List<T> getListObj(String key, Class<T> targetClass){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            byte[] bytes = shardedJedis.get(getBytesKey(key));
            return ProtostuffSerializer.deserializeList(bytes, targetClass);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
            result = shardedJedis.rpush(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 设置List缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                shardedJedis.del(key);
            }
            List<byte[]> list = new ArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][])list.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public long listAdd(String key, String... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.rpush(key, value);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public long listObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            List<byte[]> list = new ArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][])list.toArray());
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Set<String> getSet(String key) {
        Set<String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.smembers(key);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public <T> Set<T> getObjectSet(String key,Class<T> targetClass) {
        Set<T> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = new HashSet();
                Set<byte[]> set = shardedJedis.smembers(getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setSet( String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
            result = shardedJedis.sadd(key, (String[])value.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }


    /**
     * 设置Set缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                shardedJedis.del(key);
            }
            Set<byte[]> set = new HashSet();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = shardedJedis.sadd(getBytesKey(key), (byte[][])set.toArray());
            if (cacheSeconds != 0) {
                shardedJedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public long setSetAdd(String key, String... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.sadd(key, value);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     * @param key 键
     * @param value 值
     * @return
     */
    public long setSetObjectAdd(String key, Object... value) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Set<byte[]> set = new HashSet<>();
            for (Object o : value){
                set.add(toBytes(o));
            }
            result = shardedJedis.rpush(getBytesKey(key), (byte[][])set.toArray());
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public List<String> getList(String key) {
        List<String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.lrange(key, 0, -1);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取Collection
     * @param key
     * @param targetClass
     * @return
     */
    public <T> Collection<T> getCollection(String key,Class<T> targetClass){
        Collection<T> result=Collections.emptyList();
        ShardedJedis shardedJedis = null;
        try {

            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                Collection<byte[]> bytes=shardedJedis.hvals(getBytesKey(key));
                bytes.forEach(b->{
                    result.add(toObject(b, targetClass));
                });
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public <T> List<T> getObjectList(String key,Class<T> targetClass) {
        List<T> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                List<byte[]> list = shardedJedis.lrange(getBytesKey(key), 0, -1);
                value = new ArrayList<>();
                for (byte[] bs : list){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public byte[] getByte(byte[] key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            byte[] value = shardedJedis.get(key);
            return value;
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public Map<String, String> getMap(String key) {
        Map<String, String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                value = shardedJedis.hgetAll(key);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取某一个hash中所有的值
     * @param key 键
     * @return 值
     */
    public <T> Map<String, T> getHashAllObj(String key,Class<T> targetClass) {
        Map<String, T> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = new HashMap();
                Map<byte[], byte[]> map = shardedJedis.hgetAll(getBytesKey(key));
                for (Map.Entry<byte[], byte[]> e : map.entrySet()){
                    value.put(toString(e.getKey()), toObject(e.getValue(),targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取 String类型 hash
     * @param key
     * @return
     */
    public Map<String,String> getHashAllString(String key){
        Map<String, String> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value= shardedJedis.hgetAll(key);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }


    /**
     * 获取 hash 长度
     * @param mapKey
     * @return
     */
    public Long getHashLen(String mapKey){
        ShardedJedis shardedJedis = null;
        Long result=null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(mapKey))) {
                result=shardedJedis.hlen(getBytesKey(mapKey));
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 从hash中获取对象
     * @param mapKey
     * @param field
     * @param targetClass
     * @return
     */
    public <T> T getHashObject(String mapKey, String field,Class<T> targetClass){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if(shardedJedis.exists(mapKey)){
                byte[] bytes=shardedJedis.hget(getBytesKey(mapKey), getBytesKey(field));
                if(null != bytes){
                    return toObject(bytes, targetClass);
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    public <T> void setHashObject(String mapKey, String fieldKey, T value){
        setHashObject(mapKey, fieldKey, value, 0);
    }

    /**
     * 设置byte到map
     * @param mapKey
     * @param fieldKey
     * @param bytes
     * @param cacheSeconds
     */
    public void setHashBytes(String mapKey, String fieldKey, byte[] bytes,int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(getBytesKey(mapKey), getBytesKey(fieldKey), bytes);
            if(cacheSeconds != 0){
                shardedJedis.expire(mapKey, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 从Map中取出byte
     * @param mapKey
     * @param fieldKey
     * @return
     */
    public byte[] getHashBytes(String mapKey, String fieldKey){
        byte[] result=null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result=shardedJedis.hget(getBytesKey(mapKey), getBytesKey(fieldKey));
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 设置Map缓存
     * @param mapKey  map键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public <T> void setHashObject(String mapKey, String fieldKey,T value, int cacheSeconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(getBytesKey(mapKey), getBytesKey(fieldKey), toBytes(value));
            if(cacheSeconds != 0){
                shardedJedis.expire(mapKey, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 设置List对象到map中
     * @param mapKey
     * @param fieldKey
     * @param list
     * @param cacheSeconds
     * @param <T>
     */
    public <T> void setHashListObj(String mapKey, String fieldKey,List<T> list, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(getBytesKey(mapKey), getBytesKey(fieldKey), ProtostuffSerializer.serializeList(list));
            if(cacheSeconds != 0){
                shardedJedis.expire(mapKey, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 从map中获取list
     * @param mapKey
     * @param fieldKey
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> List<T> getHashListObj(String mapKey, String fieldKey, Class<T> targetClass){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if(shardedJedis.hexists(mapKey,fieldKey)){
                byte[] bytes=shardedJedis.hget(getBytesKey(mapKey), getBytesKey(fieldKey));
                if(null != bytes){
                    return ProtostuffSerializer.deserializeList(bytes,targetClass);
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return null;
    }

    /**
     * 设置多条记录到map中
     * @param mapKey
     * @param byteMap
     * @param cacheSeconds
     */
    public void setHashBytes(String mapKey,Map<String,byte[]> byteMap, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            for(String fieldKey:byteMap.keySet()){
                shardedJedis.hset(getBytesKey(mapKey), getBytesKey(fieldKey), byteMap.get(fieldKey));

            }
            if(cacheSeconds != 0){
                shardedJedis.expire(mapKey, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    public void setHashString(String mapKey, String field, String value){
        setHashString(mapKey, field, value, 0);
    }

    /**
     * hash设置字符串
     * @param mapKey
     * @param field
     * @param value
     * @param cacheSeconds
     */
    public void setHashString(String mapKey, String field, String value, int cacheSeconds){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(mapKey, field, value);
            if(cacheSeconds != 0){
                shardedJedis.expire(mapKey, cacheSeconds);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 删除map中的 数据
     * @param mapKey  hashmap在redis中的key
     * @param fields  map中的 键值
     * @return
     */
    public Long mapRemove(String mapKey, String ...fields ) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.hdel(mapKey, fields);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 移除Map缓存中的值
     * @param key 键
     * @param mapKey 值
     * @return
     */
    public long mapObjectRemove(String key, String mapKey) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.hdel(getBytesKey(key), getBytesKey(mapKey));
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 返回MAP KEYS
     * @param mapkey
     * @return
     */
    public Set<String> mapGetKeys(String mapkey) {
        Set<String> set = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            set = shardedJedis.hkeys(mapkey);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return set;
    }
    /**
     * lww
     * 返回通配符 KEYS
     * 通过通配符返回存在的key Set
     * @param keys
     * @return
     */
    public Set<String> getKeys(String keys) {
        Set<String> set = new HashSet<>();
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            for (Jedis shard : shardedJedis.getAllShards()) {
                set.addAll(shard.keys(keys));
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return set;
    }

    /**
     * 判断Map缓存中的Key是否存在
     * @param key 键
     * @return
     */
    public boolean mapExists(String key, String fieldKey) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.hexists(key, fieldKey);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }


    /**
     * 删除缓存
     * @param key 键
     * @return
     */
    public long deleteByKey(String key) {
        long result = 0;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)){
                result = shardedJedis.del(key);
            } else {
                log.error("Don't Exists");
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 缓存是否存在
     * @param key 键
     * @return
     */
    public boolean exists(String key) {
        boolean result = false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;
    }


    /**
     * 缓存分页对象集合
     * @param key
     * @param categoryMap
     */
    public void setListPageObj(String key, Map<byte[], Double> categoryMap){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.zadd(getBytesKey(key),categoryMap);
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 获取排序分页集合
     * @param key
     * @param start
     * @param end
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> Set<T> getRangePageObj(String key, int start, int end, Class<T> targetClass ) {
        LinkedHashSet<T> value = new LinkedHashSet<>();
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                Set<byte[]> set = shardedJedis.zrange(getBytesKey(key), start, end);
                for (byte[] bs : set){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }


    /**
     * 获取反向排序分页集合
     * @param key
     * @param start
     * @param end
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> Set<T> getRevrangePageObj(String key, int start, int end, Class<T> targetClass ) {
        LinkedHashSet<T> value = new LinkedHashSet<>();
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                Set<byte[]> set = shardedJedis.zrevrange(getBytesKey(key), start, end);
                for (byte[] bs : set){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 从缓存中获取分页对象
     * @param key
     * @param targetClass
     * @return
     */
    public <T> List<T> getPageObj(String key, int start, int end, Class<T> targetClass ) {
        List<T> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                List<byte[]> list = shardedJedis.lrange(getBytesKey(key), start, end);
                value = new ArrayList<>();
                for (byte[] bs : list){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }


    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public <T> Set<T> getPageObjSet(String key,Class<T> targetClass) {
        LinkedHashSet<T> value = null;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = new LinkedHashSet<>();
                Set<byte[]> set = shardedJedis.smembers(getBytesKey(key));
                for (byte[] bs : set){
                    value.add(toObject(bs,targetClass));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 获取页集合
     * @param key
     * @return
     */
    public long getPageObjCount(String key) {
        ShardedJedis shardedJedis = null;
        long value = -1;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = shardedJedis.zcard(key);
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * 删除页集合元素
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public <T> long remPageObjElement(String key, T target) {
        ShardedJedis shardedJedis = null;
        long value = -1;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(getBytesKey(key))) {
                value = shardedJedis.zrem(getBytesKey(key), toBytes(target));
            }
        } catch (Exception e) {
            log.error("",e);
        }finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public <T> T toObject(final byte[] bytes,Class<T> targetClass){
        return ProtostuffSerializer.deserialize(bytes,targetClass);
    }

    /**
     * 获取byte[]类型Key
     * @return
     */
    public byte[] getBytesKey(final String str){
        return getBytes(str);
    }

    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public byte[] toBytes(final Object object){
        return ProtostuffSerializer.serialize(object);
    }
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
        if (str != null){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 转换为字节数组
     * @return
     */
    public static String toString(byte[] bytes){
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 如果对象为空，则使用defaultVal值
     * 	see: ObjectUtils.toString(obj, defaultVal)
     * @param obj
     * @param defaultVal
     * @return
     */
    public static String toString(final Object obj, final String defaultVal) {
        return obj == null ? defaultVal : obj.toString();
    }

}

