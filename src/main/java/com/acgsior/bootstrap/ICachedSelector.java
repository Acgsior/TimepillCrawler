package com.acgsior.bootstrap;

/**
 * Created by Yove on 7/4/16.
 */
public interface ICachedSelector<T> {

    void cache(T value);
}