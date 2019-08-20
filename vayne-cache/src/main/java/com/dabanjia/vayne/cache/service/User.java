package com.dabanjia.vayne.cache.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xujiajun
 * @since 2019/3/22
 */
@Setter
@Getter
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 2591835044456422048L;

	private Long id;

	private String name;

	private String remarks;
}
