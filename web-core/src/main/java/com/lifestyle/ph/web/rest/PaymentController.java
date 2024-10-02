package com.lifestyle.ph.web.rest;

import com.lifestyle.ph.web.builder.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/payment/success")
    public String checkoutSuccessWebhook(
            @RequestBody Object payload,
            @RequestHeader("X-PayMaya-Signature") String signature) {
        // Log the payload and signature for debugging
        System.out.println("Received payload: " + payload);
        System.out.println("Received signature: " + signature);

        // Add signature verification and processing logic here
        // Verify the signature if applicable

        // Process the payload (e.g., save to database, trigger actions, etc.)

        return "Webhook received and processed";
    }

    @PostMapping("/payment/failed")
    public String checkoutFailedWebhook(
            @RequestBody Object payload,
            @RequestHeader("X-PayMaya-Signature") String signature) {
        // Log the payload and signature for debugging
        System.out.println("Received payload: " + payload);
        System.out.println("Received signature: " + signature);

        // Add signature verification and processing logic here
        // Verify the signature if applicable

        // Process the payload (e.g., save to database, trigger actions, etc.)

        return "Webhook received and processed";
    }

    @PostMapping("/payment/canceled")
    public String checkoutCanceleddWebhook(
            @RequestBody Object payload,
            @RequestHeader("X-PayMaya-Signature") String signature) {
        // Log the payload and signature for debugging
        System.out.println("Received payload: " + payload);
        System.out.println("Received signature: " + signature);

        // Add signature verification and processing logic here
        // Verify the signature if applicable

        // Process the payload (e.g., save to database, trigger actions, etc.)

        return "Webhook received and processed";
    }
}
