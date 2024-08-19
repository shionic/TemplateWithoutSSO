package com.github.shionic.backendexample.utils;

import java.util.Random;

public class StringUtils {
    public static String randomString(int length) {
        return new Random().ints(97, 123)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
