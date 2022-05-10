package com.greenblat.twitter.repo;

import com.greenblat.twitter.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
