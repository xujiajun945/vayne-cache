package com.dabanjia.vayne.cache.aspect;

import com.dabanjia.vayne.cache.annotation.Cacheable;
import com.dabanjia.vayne.cache.annotation.InvalidateCache;
import com.dabanjia.vayne.cache.connector.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xujiajun
 * @since 2019/4/2
 */
@Aspect
@Component
public class CacheAspect {

	@Autowired
	private RedisCache redisCache;

	@Pointcut(value = "@annotation(com.dabanjia.vayne.cache.annotation.Cacheable)")
	private void pointcutCacheable() {}

	@Pointcut(value = "@annotation(com.dabanjia.vayne.cache.annotation.InvalidateCache)")
	private void pointcutInvalidCache() {}

	@Around(value = "pointcutCacheable()")
	public Object cacheAround(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		Class clazz = signature.getDeclaringType();
		try {
			Method method = clazz.getDeclaredMethod(signature.getName());
			Class<?> returnType = method.getReturnType();
			Cacheable cacheable = method.getAnnotation(Cacheable.class);
			String key = cacheable.key();
			String name = cacheable.name();
			Long expireTime = cacheable.expire();

			// 取
			Object o = redisCache.get(":" + name + ":" + key, returnType);
			if (o == null) {
				o = joinPoint.proceed();
				redisCache.put(":" + name + ":" + key, o, expireTime);
			}
			return o;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}

	@Around(value = "pointcutInvalidCache()")
	public Object invalidAround(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		Class clazz = signature.getDeclaringType();
		try {
			Method method = clazz.getDeclaredMethod(signature.getName());
			InvalidateCache invalidCache = method.getAnnotation(InvalidateCache.class);
			String key = invalidCache.key();
			String name = invalidCache.name();

			// 移除
			redisCache.del(":" + name + ":" + key);
			Object result = joinPoint.proceed();
			return result;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}
}
