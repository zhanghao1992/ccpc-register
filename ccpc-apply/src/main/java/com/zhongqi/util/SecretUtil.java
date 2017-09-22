package com.zhongqi.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

public class SecretUtil {

    public static String signTopRequest(Map<String, String> params, String secret) throws Exception {
        String[] keys = (String[])params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        String[] var4 = keys;
        int var5 = keys.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String key = var4[var6];
            String value = (String)params.get(key);
            if (StrUtil.areNotEmpty(key, value)) {
                query.append(key).append(value);
            }
        }

        query.append(secret);
        byte[] bytes = encryptMD5(query.toString());
        return byte2hex(bytes);
    }

    public static String signBody(String path, String body, String timestamp, String secret) throws Exception {
        String oriString = path + body + timestamp + secret;
        byte[] bytes = encryptMD5(oriString);
        return byte2hex(bytes);
    }

    public static String signGetParameters(Map<String, String> parameters, String secret) throws Exception {
        List<Map.Entry<String, String>> list = new ArrayList(parameters.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return ((String)o1.getValue()).compareTo((String)o2.getValue());
            }
        });
        StringBuilder ret = new StringBuilder();
        Iterator var5 = list.iterator();

        while(var5.hasNext()) {
            Map.Entry<String, String> mapping = (Map.Entry)var5.next();
            ret.append((String)mapping.getKey() + "=" + (String)mapping.getValue() + "&");
        }

        ret.append("sign=" + signTopRequest(parameters, secret));
        return ret.toString();
    }

    public static String signBodyTopRequest(String path, String json, String timestamp, String secret, String signMethod) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append(path).append(json).append(timestamp);
        byte[] bytes;
        if ("HMAC".equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        Object var2 = null;

        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(data.getBytes("UTF-8"));
            return bytes;
        } catch (GeneralSecurityException var5) {
            throw new IOException(var5.toString());
        }
    }

    public static byte[] encryptMD5(String data) throws Exception {
        return encryptMD5(data.getBytes("UTF-8"));
    }

    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                sign.append("0");
            }

            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }


}
