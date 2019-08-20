package com.dabanjia.vayne.cache.connector;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
public interface Cache<k, v> {

	/**
	 * 获取缓存对象
	 * @param key 缓存的key
	 * @param clazz 储存的对象的class对象
	 * @return value 缓存的对象
	 */
	v get(k key, Class<v> clazz);

	/**
	 * 存放缓存对象
	 * @param key 缓存存放的key
	 * @param value 缓存的对象
	 * @param expireTime 缓存失效时间
	 */
	void put(k key, v value, Long expireTime);

	/**
	 * 移除缓存对象
	 * @param key 缓存的key
	 */
	void del(k key);
}
