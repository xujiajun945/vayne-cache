package com.dabanjia.vayne.cache.connector;

import com.alibaba.fastjson.JSON;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@Component
public class RedisCache<k, v> implements Cache<k, v> {

	@Autowired
	private RedisClient redisClient;

	@Value("${vayne-cache.redis.key-prefix}")
	private String keyPrefix;

	@Override
	public v get(k key, Class<v> clazz) {
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		RedisAsyncCommands<String, String> commands = connect.async();
		String serialKey = keyPrefix + key;
		try {
			String value = commands.get(serialKey).get();
			v object = JSON.parseObject(value, clazz);
			return object;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			connect.close();
		}
		return null;
	}

	@Override
	public void put(k key, v value, Long expireTime) {
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		RedisAsyncCommands<String, String> commands = connect.async();
		String serialKey = keyPrefix + key;
		commands.set(serialKey, JSON.toJSONString(value));
		commands.expire(serialKey, expireTime);
		connect.close();
	}

	@Override
	public void del(k key) {
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		RedisAsyncCommands<String, String> commands = connect.async();
		String serialKey = keyPrefix + key;
		commands.del(serialKey);
		connect.close();
	}
}
