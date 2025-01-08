package com.ust.eventorganizer.Repository;

import com.ust.eventorganizer.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long>
{
    List<Feedback> findAllByEvent_Id(Long eventId);
}
