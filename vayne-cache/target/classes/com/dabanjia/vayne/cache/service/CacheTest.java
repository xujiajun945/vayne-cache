package com.dabanjia.vayne.cache.service;

import com.dabanjia.vayne.cache.annotation.Cacheable;
import com.dabanjia.vayne.cache.annotation.InvalidateCache;
import com.dabanjia.vayne.cache.connector.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@Component
public class CacheTest {

	@Autowired
	private RedisCache<String, User> redisCache;

	public void test() {
		User user = new User();
		user.setId(1L);
		user.setName("xujiajun Test");
		user.setRemarks("service cache");
		redisCache.put("service", user, 100L);
	}

	public void test02() {
		User user = redisCache.get("service", User.class);
		System.out.println(user);
	}

	@Cacheable(name = "user", key = "1", expire = 60)
	public User test03() {
		User user = new User();
		user.setId(0L);
		user.setName("徐家俊");
		user.setRemarks("码畜");
		return user;
	}

	@InvalidateCache(name = "user", key = "1")
	public void test04() {
		System.out.println("remove cache");
	}

}
