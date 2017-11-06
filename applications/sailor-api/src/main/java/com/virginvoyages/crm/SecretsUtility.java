package com.virginvoyages.crm;

import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class SecretsUtility {
    private Base64.Decoder decoder = Base64.getDecoder();
    private Base64.Encoder encoder = Base64.getEncoder();
    private static Charset ENCODING_CHARSET  = StandardCharsets.ISO_8859_1;

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.err.println("Invalid arguments, please supply string to encode");
            return;
        }
        System.out.println(new SecretsUtility().encode(args[0]));
    }

    public String encode(String toEncode) {
        return UUID.randomUUID().toString().charAt(0) + encoder.encodeToString(toEncode.getBytes(ENCODING_CHARSET));
    }

    public String decode(String toDecode) {
        if (StringUtils.isEmpty(toDecode)) {
            return null;
        }
        return new String(decoder.decode(toDecode.substring(1)), ENCODING_CHARSET);
    }
}
