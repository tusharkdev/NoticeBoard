package com.board.announcement.service;

import com.board.announcement.exceptions.UserNotFoundException;
import com.board.announcement.model.Group;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.UserRepository;
import org.apache.kafka.common.errors.GroupIdNotFoundException;
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
    UserService userService;

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
            User user = userService.findUserById(String.valueOf(new ObjectId(userId)));
            if (user != null) {
                groupIds = user.getGroupIds();
                if (groupIds == null)
                    groupIds = new ArrayList<>();
                groupIds.add(groupId);
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(String.valueOf(new ObjectId(userId))));
                Update updateQuery = new Update();
                updateQuery.set("groupIds", groupIds);
                mongoTemplate.findAndModify(query, updateQuery, User.class);
            } else
                throw new UserNotFoundException("User does not exist");
        } catch (Exception e) {
            message = e.getMessage();
        }

        return message;
    }


    public String unsubscribeUserFromGroup(String userId, String groupId) {
        System.out.println(userId + " " + groupId);
        String message = "User unsubscribed to group";

        try {
            User user = userService.findUserById(String.valueOf(new ObjectId(userId)));
            if (user == null)
                throw new UserNotFoundException("User does not exist");
            List<String> groupIds = user.getGroupIds();
            if (groupIds.isEmpty() || !groupIds.contains(groupId))
                throw new GroupIdNotFoundException("Group not found " + groupId);
            groupIds.remove(groupId);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(String.valueOf(new ObjectId(userId))));
            Update updateQuery = new Update();
            updateQuery.set("groupIds", groupIds);
            mongoTemplate.findAndModify(query, updateQuery, User.class);
        } catch (Exception e) {
            message = e.getMessage();
        }
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
