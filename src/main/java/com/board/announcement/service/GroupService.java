package com.board.announcement.service;

import com.board.announcement.exceptions.UserNotFoundException;
import com.board.announcement.model.Group;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    MongoTemplate mongoTemplate;

    public String subscribeUserToGroup(String userId, String groupId) {
        List<String> groupIds;
        String message = "User subscribed to group";
        try {
            System.out.println(userId + " " + groupId);
            Optional<User> user = userRepository.findById(String.valueOf(new ObjectId(userId)));
            if (user.isPresent()) {
                groupIds = user.get().getGroupIds();
                if (groupIds == null)
                    groupIds = new ArrayList<>();
                groupIds.add(groupId);
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(String.valueOf(new ObjectId(userId))));
                Update updateQuery = new Update();
                updateQuery.set("groupIds", groupIds);
                mongoTemplate.findAndModify(query, updateQuery, User.class);
            }
            else
                throw new UserNotFoundException("User does not exist");
        }
        catch (Exception e)
        {
            message=e.getMessage();
        }

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

    public Group saveGroup(Group group) {
        groupRepository.save(group);
        return group;
    }

    public Group deleteGroup(Group group) {
        groupRepository.delete(group);
        cacheManager.getCache(group.getGroupId()).clear();
        return group;
    }
}
