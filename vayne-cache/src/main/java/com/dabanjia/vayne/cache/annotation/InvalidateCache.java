package com.dabanjia.vayne.cache.annotation;

import java.lang.annotation.*;

/**
 * @author xujiajun
 * @since 2019/4/2
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InvalidateCache {

	String name();

	String key() default "";
}
