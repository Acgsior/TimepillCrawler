package com.acgsior.factory;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Yove on 16/07/03.
 */
public class BeanFactory {

	private static Logger log = LoggerFactory.getLogger(BeanFactory.class);

	public static void setPropertyValueSafely(Object bean, String name, Object value) {
		try {
			BeanUtils.setProperty(bean, name, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(String.format("Cannot set %s.%s with value: %s", bean.getClass().getName(), name, value));
		}
	}
}
