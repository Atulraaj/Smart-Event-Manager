package com.atul.event.management.controller;

import com.atul.event.management.entity.Registration;
import com.atul.event.management.entity.User;
import com.atul.event.management.repository.RegistrationRepository;
import com.atul.event.management.repository.UserRepository;
import com.atul.event.management.service.QRCodeService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class QRCodeController {

    private static final int QR_CODE_SIZE = 220;

    private final QRCodeService qrCodeService;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    @GetMapping("/user/registrations/{id}/qr")
    public ResponseEntity<byte[]> registrationQRCode(
            @PathVariable Long id,
            Principal principal
    ) throws Exception {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user was not found."));
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid registration id: " + id));

        if (!registration.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        String qrContent = "REGISTRATION_ID:" + registration.getId();
        byte[] qrImage = qrCodeService.generateQRCodeImage(qrContent, QR_CODE_SIZE, QR_CODE_SIZE);

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}
