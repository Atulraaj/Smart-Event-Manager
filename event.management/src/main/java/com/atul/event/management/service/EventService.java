package com.atul.event.management.service;

import com.atul.event.management.entity.Event;
import com.atul.event.management.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final String DEFAULT_STATUS = "ACTIVE";

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        if (event.getStatus() == null || event.getStatus().isBlank()) {
            event.setStatus(DEFAULT_STATUS);
        }
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
