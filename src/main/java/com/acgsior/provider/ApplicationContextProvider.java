package com.acgsior.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Yove on 7/7/16.
 */
public class ApplicationContextProvider {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}