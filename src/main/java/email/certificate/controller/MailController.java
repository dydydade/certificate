package email.certificate.controller;

import email.certificate.dto.ApiResponse;
import email.certificate.dto.EmailCertificationRequest;
import email.certificate.dto.EmailCertificationResponse;
import email.certificate.service.MailSendService;
import email.certificate.service.MailVerifyService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailSendService mailSendService;
    private final MailVerifyService mailVerifyService;

//    @PostMapping("/send-certification")
//    public ResponseEntity<ApiResponse<EmailCertificationResponse>> sendCertificationNumber(
//            @Validated @RequestBody EmailCertificationRequest request
//    )
//            throws MessagingException, NoSuchAlgorithmException {
//        EmailCertificationResponse response = mailSendService.sendEmailForCertification(request.getEmail());
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }

    @PostMapping("/send-certification")
    public ResponseEntity<String> sendCertificationNumber(
            @Validated @RequestBody EmailCertificationRequest request
    ) throws MessagingException, NoSuchAlgorithmException {
        // 비동기 메서드 호출
        mailSendService.sendEmailForCertification(request.getEmail())
                .thenApply(response -> ResponseEntity.ok(ApiResponse.success(response)))
                .exceptionally(ex -> ResponseEntity.badRequest().body(ApiResponse.failure("이메일 인증 처리 중 에러 발생")));
        return ResponseEntity.ok("인증 코드가 메일로 발송되었습니다.");
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyCertificationNumber(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber
    ) {
        mailVerifyService.verifyEmail(email, certificationNumber);
        return ResponseEntity.ok(ApiResponse.success());
    }
}