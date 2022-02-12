package com.board.announcement.repository;

import java.util.List;

import com.board.announcement.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChannelRepository extends MongoRepository<Group, String> {

    @Query("{name:'?0'}")
    Group findChannel(String channelName);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1}")
    List<Group> findAll(String channelName);

    public long count();
}
