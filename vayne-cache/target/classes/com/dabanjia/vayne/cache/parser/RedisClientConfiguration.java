package com.dabanjia.vayne.cache.parser;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@Configuration
public class RedisClientConfiguration {

	@Value("${vayne-cache.redis.address}")
	private String redisUrl;

	@Value("${vayne-cache.redis.timeout}")
	private Integer timeout;

	@Value("${vayne-cache.redis.password}")
	private String password;

	@Bean
	public RedisClient redisClient() {
		RedisURI redisURI = RedisURI.create(redisUrl);
		redisURI.setPassword(password);

		RedisClient redisClient = RedisClient.create(redisURI);
		Duration duration = Duration.ofSeconds(timeout);
		redisClient.setDefaultTimeout(duration);
		return redisClient;
	}
}
