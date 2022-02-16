package com.board.announcement.controller;

import com.board.announcement.model.Group;
import com.board.announcement.model.Post;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.PostRepository;
import com.board.announcement.repository.UserRepository;
import com.board.announcement.service.GroupService;
import com.board.announcement.service.PostsService;
import com.board.announcement.service.UserService;
import lombok.NonNull;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

@RestController
@PropertySource("classpath:dev.properties")
public class BoardController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CacheManager cacheManager;

    @Autowired
    PostsService postsService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @Value("${dev.firstname}")
    String firstname;


    @PostMapping("/users")
    @NonNull
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/groups")
    @NonNull
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        try {
            return new ResponseEntity<Group>(groupService.saveGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Group>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/groups")
    @NonNull
    public ResponseEntity<Group> removeGroup(@RequestBody Group group) {
        try {
            return new ResponseEntity<Group>(groupService.deleteGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Group>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/publish")
    @NonNull
    public ResponseEntity<Post> publishPost(@RequestBody Post post) {
        try {
            return new ResponseEntity<Post>(postsService.savePost(post), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unpublish")
    @NonNull
    public ResponseEntity<Post> unpublishPost(@RequestBody Post post) {
        try {
            return new ResponseEntity<Post>(postsService.deletePost(post), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/posts/{groupId}")
    public ResponseEntity<Iterable<Post>> getPosts(@PathVariable String groupId) {
        try {
            System.out.println("group is " + groupId);
            List<Post> result = postRepository.findPosts((new ObjectId(groupId)).toString());
            System.out.println("result is " + result.size());
            return new ResponseEntity<Iterable<Post>>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Iterable<Post>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/subscribe/userId/{userId}/groupId/{groupId}")
    public ResponseEntity<Map<String, String>> subscribeTo(@PathVariable String userId, @PathVariable String groupId) {
        String message = "";
        try {
            Map<String, String> body = new HashMap<>();
            message = groupService.subscribeUserToGroup(userId, groupId);
            body.put("message", message);

            return new ResponseEntity<>(body, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unsubscribe/userId/{userId}/groupId/{groupId}")
    public ResponseEntity<Map<String, String>> unsubscribeTo(@PathVariable String userId, @PathVariable String groupId) {
        String message = "";
        try {
            Map<String, String> body = new HashMap<>();
            message = groupService.unsubscribeUserFromGroup(userId, groupId);
            body.put("message", message);

            return new ResponseEntity<>(body, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<User>(userService.findUserById(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable String groupId) {
        try {
            return new ResponseEntity<Group>(groupService.findGroupById(groupId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Group>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userPosts/{userId}")
    public ResponseEntity<Iterable<Post>> getPostsByUserId(@PathVariable String userId) {
        try {
            return new ResponseEntity<Iterable<Post>>(postsService.getPostsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Iterable<Post>>(HttpStatus.BAD_REQUEST);
        }
    }
}
