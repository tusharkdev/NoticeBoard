package com.board.announcement.controller;

import com.board.announcement.model.Group;
import com.board.announcement.model.Note;
import com.board.announcement.model.Post;
import com.board.announcement.model.User;
import com.board.announcement.service.FirebaseMessagingService;
import com.board.announcement.service.GroupService;
import com.board.announcement.service.PostsService;
import com.board.announcement.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PropertySource("classpath:dev.properties")
@RequestMapping("/board")
public class BoardController {

    @Autowired
    PostsService postsService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;


    @PostMapping("/users")
    @NonNull
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/groups")
    @NonNull
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        try {
            return new ResponseEntity<>(groupService.saveGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/groups")
    @NonNull
    public ResponseEntity<Group> removeGroup(@RequestBody Group group) {
        try {
            return new ResponseEntity<>(groupService.deleteGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/posts/publish")
    @NonNull
    public ResponseEntity<Post> publishPost(@RequestBody Post post) {
        try {
            return new ResponseEntity<>(postsService.savePost(post), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/posts/unpublish")
    @NonNull
    public ResponseEntity<Post> unpublishPost(@RequestBody Post post) {
        try {
            return new ResponseEntity<>(postsService.deletePost(post), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/posts/{groupId}")
    public ResponseEntity<Iterable<Post>> getPosts(@PathVariable String groupId) {
        try {
            System.out.println("group is " + groupId);
            List<Post> result = postsService.getPostsByGroupId(groupId);
            System.out.println("result is " + result.size());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/groups/subscribe/userId/{userId}/groupId/{groupId}")
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

    @PostMapping("/groups/unsubscribe/userId/{userId}/groupId/{groupId}")
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
            return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable String groupId) {
        try {
            return new ResponseEntity<>(groupService.findGroupById(groupId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userPosts/{userId}")
    public ResponseEntity<Iterable<Post>> getPostsByUserId(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(postsService.getPostsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-notification")
    @ResponseBody
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(note, token);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/noticeboard")
    public String sayHello() {
        return "Swagger Hello World";
    }
}
