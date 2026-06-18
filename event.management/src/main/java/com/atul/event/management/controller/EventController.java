package com.atul.event.management.controller;

import com.atul.event.management.entity.Event;
import com.atul.event.management.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/admin/events")
    public String adminEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin-events";
    }

    @GetMapping("/admin/events/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    @PostMapping("/admin/events")
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventService.saveEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/admin/events/edit/{id}")
    public String editEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + id));
        model.addAttribute("event", event);
        return "event-form";
    }

    @PostMapping("/admin/events/update/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute("event") Event event) {
        Event existingEvent = eventService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + id));

        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setStartDateTime(event.getStartDateTime());
        existingEvent.setEndDateTime(event.getEndDateTime());
        existingEvent.setCapacity(event.getCapacity());

        eventService.saveEvent(existingEvent);
        return "redirect:/admin/events";
    }

    @GetMapping("/admin/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }

    @GetMapping("/user/events")
    public String userEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "user-events";
    }

    @GetMapping("/user/events/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + id));
        model.addAttribute("event", event);
        return "event-details";
    }
}
