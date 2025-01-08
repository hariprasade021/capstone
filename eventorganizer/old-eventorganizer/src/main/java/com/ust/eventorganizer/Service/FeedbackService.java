package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackService
{
    public Feedback saveFeedback(Feedback feedback);
    public List<Feedback> getAllFeedback();
    public Feedback getFeedbackById(Long id);
    public void deleteFeedback(Long id);
    public Feedback updateFeedback(Long id, Feedback feedback);
    public List<Feedback> getFeedbackByEventId(Long eventId);
}
