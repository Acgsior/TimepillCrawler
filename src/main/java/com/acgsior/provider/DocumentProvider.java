package com.acgsior.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by mqin on 7/7/16.
 */
public class DocumentProvider {

    public static Element fetch(String URL) throws IOException {
        return Jsoup.connect(URL).get();
    }
}
