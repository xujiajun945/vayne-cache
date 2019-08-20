package com.dabanjia.vayne.cache.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

	@Autowired
	private CacheTest cacheTest;

	@Test
	public void test() {
		cacheTest.test();
	}

	@Test
	public void test2() {
		cacheTest.test02();
	}

	@Test
	public void getUser() {
		User user = cacheTest.test03();
		System.out.println(user);
	}

	@Test
	public void invalidCache() {
		cacheTest.test04();
	}
}
