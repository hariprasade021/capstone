package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.FeedbackRepository;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Feedback;
import com.ust.eventorganizer.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService
{
    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private EventService eventService;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }




    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepo.findAll();
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteFeedback(Long id) {
        feedbackRepo.deleteById(id);
    }


    public Feedback updateFeedback(Long id, Feedback feedback) {


        if(!feedbackRepo.existsById(id))
        {
            throw new RuntimeException("Feedback with ID: "+id+" not found");
        }

        Feedback feedbackToUpdate = getFeedbackById(id);
        if(feedback.getComments()!=null)
        {
            feedbackToUpdate.setComments(feedback.getComments());
        }
        if(feedback.getRating()!=null)
        {
            feedbackToUpdate.setRating(feedback.getRating());
        }
        return feedbackRepo.save(feedbackToUpdate);
    }

    @Override
    public List<Feedback> getFeedbackByEventId(Long eventId) {
        return feedbackRepo.findAllByEvent_Id(eventId);
    }
}
