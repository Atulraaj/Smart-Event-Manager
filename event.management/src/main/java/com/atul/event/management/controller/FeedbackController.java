package com.atul.event.management.controller;

import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.User;
import com.atul.event.management.repository.UserRepository;
import com.atul.event.management.service.EventService;
import com.atul.event.management.service.FeedbackService;
import java.security.Principal;
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
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final EventService eventService;
    private final UserRepository userRepository;

    @GetMapping("/user/events/{id}/feedback")
    public String feedbackForm(@PathVariable Long id, Model model) {
        Event event = getEvent(id);
        model.addAttribute("event", event);
        return "feedback-form";
    }

    @PostMapping("/user/events/{id}/feedback")
    public String submitFeedback(
            @PathVariable Long id,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comments,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        Event event = getEvent(id);
        User user = getCurrentUser(principal);

        try {
            feedbackService.saveFeedback(user, event, rating, comments);
            redirectAttributes.addFlashAttribute("success", "Feedback submitted successfully.");
            return "redirect:/user/registrations";
        } catch (IllegalArgumentException | IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/user/events/" + id + "/feedback";
        }
    }

    @GetMapping("/admin/feedback")
    public String adminFeedback(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        return "admin-feedback";
    }

    private Event getEvent(Long id) {
        return eventService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + id));
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user was not found."));
    }
}
