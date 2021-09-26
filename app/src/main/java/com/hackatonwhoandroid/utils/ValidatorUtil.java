package com.hackatonwhoandroid.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ValidatorUtil {
    public static Pattern domainValidatorPattern = Pattern.compile("^((?!-)[A-Za-z0-9\\p{IsCyrillic}-]{1,63}(?<!-)\\.)+[A-Za-z\\p{IsCyrillic}]{2,6}$");
    public static List<String> validDomains = Arrays.asList("rs", "срб", "net", "com");
}
