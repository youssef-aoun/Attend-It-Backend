package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findEventByDateBefore(LocalDate currentDate);
    Page<Event> findAllByDateBefore(LocalDate now, Pageable pageable);
    List<Event> findEventByTitle(String title);
    List<Event> findAllByDateAfter(LocalDate localDate);
    Page<Event> findAllByDateAfter(LocalDate now, Pageable pageable);
    List<Event> findEventBySavedBy(User user);
    List<Event> findEventByAttendees(User user);
}
