package com.board.announcement.service;

import com.board.announcement.model.Group;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public String subscribeUserToGroup(String userId, String groupId) {
        System.out.println(userId + " " + groupId);
        List<String> groupIds = userRepository.findById(String.valueOf(new ObjectId(userId))).get().getGroupIds();
        groupIds.add(groupId);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(String.valueOf(new ObjectId(userId))));
        Update updateQuery = new Update();
        updateQuery.set("groupIds", groupIds);
        mongoTemplate.findAndModify(query, updateQuery, User.class);
        String message = "User subscribed to group";
        return message;
    }


    public String unsubscribeUserFromGroup(String userId, String groupId) {
        System.out.println(userId + " " + groupId);
        List<String> groupIds = userRepository.findById(String.valueOf(new ObjectId(userId))).get().getGroupIds();
        groupIds.remove(groupId);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(String.valueOf(new ObjectId(userId))));
        Update updateQuery = new Update();
        updateQuery.set("groupIds", groupIds);
        mongoTemplate.findAndModify(query, updateQuery, User.class);
        String message = "User unsubscribed from group";
        return message;
    }

    public Group findGroupById(String groupId) {
        return groupRepository.findById(String.valueOf(new ObjectId(groupId))).get();
    }
}