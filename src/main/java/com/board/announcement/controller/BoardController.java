package com.board.announcement.controller;

import com.board.announcement.model.Channel;
import com.board.announcement.model.Message;
import com.board.announcement.model.User;
import com.board.announcement.repository.ChannelRepository;
import com.board.announcement.repository.MessageRepository;
import com.board.announcement.repository.UserRepository;
import com.board.announcement.service.DbSequenceGenr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
