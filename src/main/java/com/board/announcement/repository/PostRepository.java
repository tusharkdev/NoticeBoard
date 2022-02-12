package com.board.announcement.repository;


import java.util.List;

import com.board.announcement.model.Post;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Post, String> {

    @Query("{ 'channelName' : ?0 }")
    List<String> findMessage(String channelName);

    @Query("{ 'id' : ?0 }")
    List<String> findAll(int senderId);

    public long count();

}
