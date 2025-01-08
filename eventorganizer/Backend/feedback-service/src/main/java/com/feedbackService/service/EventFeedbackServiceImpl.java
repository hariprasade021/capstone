package com.feedbackService.service;

import com.feedbackService.dto.EventFeedbackDTO;
import com.feedbackService.model.EventFeedback;
import com.feedbackService.repository.EventFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventFeedbackServiceImpl implements EventFeedbackService {

    @Autowired
    private EventFeedbackRepository feedbackRepository;

    @Override
    public List<EventFeedbackDTO> getFeedbackByEventId(Long eventId) {
        List<EventFeedback> feedbacks = feedbackRepository.findByEventId(eventId);

        return feedbacks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EventFeedbackDTO createFeedback(EventFeedbackDTO feedbackDTO) {
        EventFeedback feedback = convertToEntity(feedbackDTO);
        feedback = feedbackRepository.save(feedback);
        return convertToDTO(feedback);
    }

    @Override
    public EventFeedbackDTO updateFeedback(Long feedbackId, EventFeedbackDTO feedbackDTO) {
        EventFeedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found with ID: " + feedbackId));

        feedback.setClientId(feedbackDTO.getClientId());
        feedback.setEventId(feedbackDTO.getEventId());
        feedback.setFeedbackText(feedbackDTO.getFeedbackText());

        feedback = feedbackRepository.save(feedback);
        return convertToDTO(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new RuntimeException("Feedback not found with ID: " + feedbackId);
        }
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    public EventFeedbackDTO getFeedbackById(Long feedbackId) {
        EventFeedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found with ID: " + feedbackId));
        return convertToDTO(feedback);
    }

    private EventFeedbackDTO convertToDTO(EventFeedback feedback) {
        EventFeedbackDTO dto = new EventFeedbackDTO();
        dto.setClientId(feedback.getClientId());
        dto.setEventId(feedback.getEventId());
        dto.setFeedbackText(feedback.getFeedbackText());
        return dto;
    }

    private EventFeedback convertToEntity(EventFeedbackDTO dto) {
        EventFeedback feedback = new EventFeedback();
        feedback.setClientId(dto.getClientId());
        feedback.setEventId(dto.getEventId());
        feedback.setFeedbackText(dto.getFeedbackText());
        return feedback;
    }
    public EventFeedback submitFeedback(Long clientId, Long eventId, String feedbackText) {
        EventFeedback feedback = new EventFeedback();
        feedback.setClientId(clientId);
        feedback.setEventId(eventId);
        feedback.setFeedbackText(feedbackText);

        return feedbackRepository.save(feedback);
    }

    }
