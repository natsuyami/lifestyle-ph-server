package com.lifestyle.ph.web.rest;

import com.lifestyle.ph.common.dto.CredentialDTO;
import com.lifestyle.ph.common.exception.DataResponse;
import com.lifestyle.ph.common.exception.PaymentException;
import com.lifestyle.ph.login.service.LoginService;
import com.lifestyle.ph.payment.service.PaymentService;
import com.lifestyle.ph.web.builder.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/url")
    public ResponseEntity<String> auth() {
        return ResponseBuilder.<String>builder().success(loginService.gmailAuthPage());
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<DataResponse>> callback(@RequestParam("code") String code) throws Exception {
        CredentialDTO credentialDTO = loginService.gmailAccessToken(code);
        return paymentService.getPayment(credentialDTO.getUserName()).doOnSuccess(paymentDetails -> {
            try {
                LOGGER.info("setting the status of the payment");
                credentialDTO.setStatus(paymentDetails.getStatus());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
                .map(data -> ResponseBuilder.<DataResponse>builder().success(new DataResponse<>(credentialDTO)))
                .onErrorResume(e -> {
                    LOGGER.error("failed to emitted value in login controller", e);
                    if (e instanceof PaymentException) {
                        return Mono.just(ResponseBuilder.<DataResponse>builder().error(
                                new DataResponse<>(e.getMessage(), credentialDTO)));
                    } else {
                        throw new RuntimeException(e);
                    }
                });
    }

    @GetMapping("/refresh/token")
    public ResponseEntity<CredentialDTO> refreshToken(@RequestBody CredentialDTO credentialDTO) {
        return ResponseBuilder.<CredentialDTO>builder().success(loginService.refreshToken(credentialDTO));
    }
}
