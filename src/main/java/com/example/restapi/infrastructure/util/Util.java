package com.example.restapi.infrastructure.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;

public class Util {
    public static final Logger debugLogger = LogManager.getLogger("debugger");
    public static final Logger tdrLogger = LogManager.getLogger("hewanTdr");

    public static void loggingTdr(Object...args){
        Util.tdrLogger.info(toDelimiterString("|", args));
    }
    public static String toDelimiterString(String delimiter, Object...args){
        return Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(delimiter));
    }

    public static String encodeBase64(String inputString) {
        String base64 = "";
        try {
            byte[] byteArray = Base64.encodeBase64(inputString.getBytes());
            base64 = new String(byteArray);
        } catch (Exception e) {
            Util.debugLogger.error("Error while encode Base64..", e);
        }

        return base64;
    }

    public static String parseSHA256(String inputString) {
        String sha256 = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = messageDigest.digest(inputString.getBytes(StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashInBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            sha256 = stringBuilder.toString();
        } catch (Exception e) {
            Util.debugLogger.error("Error while hash SHA256..", e);
        }

        return sha256;
    }

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    public static boolean isEmptyOrNull(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String)
            return ((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return ((Collection<?>) obj).isEmpty();
        return false;
    }

    public static boolean isNotEmptyOrNull(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String)
            return !((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return !((Collection<?>) obj).isEmpty();
        return true;
    }
}
