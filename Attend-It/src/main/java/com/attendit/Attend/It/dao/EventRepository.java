package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
