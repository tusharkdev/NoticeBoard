package com.board.announcement.controller;

import com.board.announcement.model.Channel;
import com.board.announcement.model.Message;
import com.board.announcement.model.User;
import com.board.announcement.repository.ChannelRepository;
import com.board.announcement.repository.MessageRepository;
import com.board.announcement.repository.UserRepository;
import com.board.announcement.service.DbSequenceGenr;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    DbSequenceGenr dbSequenceGenr;


    @PostMapping("/publish")
    @CacheEvict(value = "messageCache", key = "#message.channelName", allEntries = true)
    public Message publishMessage(@RequestBody Message message) {
        message.setId(dbSequenceGenr.getNextSequence(Message.SEQUENCE_NAME));
        return messageRepository.save(message);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        user.setId(dbSequenceGenr.getNextSequence(User.SEQUENCE_NAME));
        return userRepository.save(user);
    }

    @PostMapping("/createChannel")
    public Channel createChannel(@RequestBody Channel channel) {
        channel.setChannelId(dbSequenceGenr.getNextSequence(Channel.SEQUENCE_NAME));
        return channelRepository.save(channel);
    }

    @GetMapping("/getMessages/{channel}")
    @NonNull
    @Cacheable(cacheNames = "messageCache", key = "#channel", unless = "#result==null")
    public List<String> getMessages(@PathVariable String channel) {
        List<String> result = messageRepository.findMessage(channel);
        return result;
    }

    @MessageMapping("/publish")
    @SendTo("/topic/greetings")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Stomp is working";
    }
}
