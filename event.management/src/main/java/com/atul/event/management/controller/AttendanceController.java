package com.atul.event.management.controller;

import com.atul.event.management.entity.Registration;
import com.atul.event.management.repository.RegistrationRepository;
import com.atul.event.management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/admin/attendance")
    public String attendance(Model model) {
        model.addAttribute("registrations", registrationRepository.findAll());
        model.addAttribute("attendanceRecords", attendanceService.getAllAttendance());
        return "attendance";
    }

    @GetMapping("/admin/attendance/scan")
    public String attendanceScan() {
        return "attendance-scan";
    }

    @PostMapping("/admin/attendance/scan")
    public String scanAttendance(
            @RequestParam String qrContent,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Long registrationId = extractRegistrationId(qrContent);
            Registration registration = registrationRepository.findById(registrationId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Invalid registration id: " + registrationId
                    ));

            attendanceService.markAttendanceByQRCode(registration);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }

        return "redirect:/admin/attendance/scan";
    }

    @GetMapping("/admin/attendance/mark/{registrationId}")
    public String markAttendance(
            @PathVariable Long registrationId,
            RedirectAttributes redirectAttributes
    ) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid registration id: " + registrationId
                ));

        try {
            attendanceService.markAttendance(registration);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully.");
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }

        return "redirect:/admin/attendance";
    }

    private Long extractRegistrationId(String qrContent) {
        if (qrContent == null || qrContent.isBlank()) {
            throw new IllegalArgumentException("QR content is required.");
        }

        String value = qrContent.trim();
        if (value.startsWith("REGISTRATION_ID:")) {
            value = value.substring("REGISTRATION_ID:".length()).trim();
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid QR content.");
        }
    }
}
