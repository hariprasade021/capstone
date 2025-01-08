package com.guestManagement.service;

import com.guestManagement.model.Guest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestEmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEventInvitation(Guest guest, String eventName, String eventDate, String eventLocation) throws MessagingException {
        // Validate guest email
        if (guest.getEmail() == null || guest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Guest email cannot be empty");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Compose email body
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Dear ").append(guest.getName()).append(",\n\n")
                .append("You are invited to the event: ").append(eventName).append("\n")
                .append("Date: ").append(eventDate).append("\n")
                .append("Location: ").append(eventLocation).append("\n")
                .append("Current RSVP Status: ").append(guest.getRsvpStatus()).append("\n")
                .append("\nWe look forward to seeing you there!");

        // Set email properties
        helper.setFrom(fromEmail);
        helper.setTo(guest.getEmail());
        helper.setSubject("Event Invitation: " + eventName);
        helper.setText(emailBody.toString());

        // Send the email
        mailSender.send(message);
    }
}
