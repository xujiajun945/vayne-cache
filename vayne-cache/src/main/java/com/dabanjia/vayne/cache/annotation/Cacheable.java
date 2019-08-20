package com.dabanjia.vayne.cache.annotation;

import java.lang.annotation.*;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {

	String name();

	String key();

	long expire();
}
