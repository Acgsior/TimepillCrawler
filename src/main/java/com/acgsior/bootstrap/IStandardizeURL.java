package com.acgsior.bootstrap;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by mqin on 8/2/16.
 */
public interface IStandardizeURL {

    default String standardizeURL(String URL) {
        if (StringUtils.isBlank(URL)) {
            return URL;
        }
        if (!URL.contains("timepill.net")) {
            return "http://timepill.net".concat(URL);
        }
        if (!URL.startsWith("http:")) {
            return "http:".concat(URL);
        }
        return URL;
    }
}
