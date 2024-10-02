package com.lifestyle.ph.web.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class MayaSignature {

    private boolean verifySignature(String payload, String receivedSignature, String secret) {
        try {
            String computedSignature = computeSignature(payload, secret);
            return receivedSignature.equals(computedSignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String computeSignature(String payload, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] rawHmac = mac.doFinal(payload.getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }
}
