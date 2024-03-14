package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findEventByDateBefore(LocalDate currentDate);
}
