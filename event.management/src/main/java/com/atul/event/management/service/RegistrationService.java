package com.atul.event.management.service;

import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.Registration;
import com.atul.event.management.entity.User;
import com.atul.event.management.repository.RegistrationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private static final String REGISTERED_STATUS = "REGISTERED";

    private final RegistrationRepository registrationRepository;

    @Transactional
    public Registration registerUserForEvent(User user, Event event) {
        if (isAlreadyRegistered(user, event)) {
            throw new IllegalStateException("You are already registered for this event.");
        }

        int registrationCount = registrationRepository.findByEvent(event).size();
        if (event.getCapacity() == null || registrationCount >= event.getCapacity()) {
            throw new IllegalStateException("This event has reached its full capacity.");
        }

        Registration registration = Registration.builder()
                .user(user)
                .event(event)
                .registeredAt(LocalDateTime.now())
                .status(REGISTERED_STATUS)
                .build();

        return registrationRepository.save(registration);
    }

    @Transactional(readOnly = true)
    public List<Registration> getRegistrationsByUser(User user) {
        return registrationRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyRegistered(User user, Event event) {
        return registrationRepository.existsByUserAndEvent(user, event);
    }
}
