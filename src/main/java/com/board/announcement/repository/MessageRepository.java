package com.board.announcement.repository;


import java.util.List;

import com.board.announcement.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MessageRepository extends MongoRepository<Message, String> {

    @Query("{name:'?0'}")
    Message findMessage(String message);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1}")
    List<Message> findAll(String sender);

    public long count();

}
