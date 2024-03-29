package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findEventByDateBefore(LocalDate currentDate);
    Page<Event> findAllByDateBefore(LocalDate now, Pageable pageable);
    Event findEventByTitle(String title);
    List<Event> findAllByDateAfter(LocalDate localDate);
    Page<Event> findAllByDateAfter(LocalDate now, Pageable pageable);
}
