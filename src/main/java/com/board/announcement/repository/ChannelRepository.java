package com.board.announcement.repository;

import java.util.List;

import com.board.announcement.model.Channel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChannelRepository extends MongoRepository<Channel, String> {

    @Query("{name:'?0'}")
    Channel findChannel(String channelName);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1}")
    List<Channel> findAll(String channelName);

    public long count();
}
