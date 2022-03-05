package com.board.announcement.service;

import com.board.announcement.model.Group;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupServiceTest {

    @MockBean
    UserService userService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupService groupService;

    Group group = new Group();
    User sender, receiver;
    Group group1;

    @BeforeAll
    public void setUp() {
        group.setGroupName("TestGroup");
        group.setDescription("Test group");
        group1 = groupRepository.save(group);
        User user = new User();
        user.setFirstName("Tushar");
        user.setLastName("Kamble");
        user.setMobileNumber("9090909090");
        user.setGroupIds(new ArrayList<>());
        sender = userRepository.save(user);
        user.setFirstName("Prasad");
        user.setLastName("K");
        user.setMobileNumber("9090909090");
        user.setGroupIds(new ArrayList<>());
        receiver = userRepository.save(user);
    }

    @Test
    public void subscriptionTest() {
        when(userService.findUserById(sender.getId())).thenReturn(sender);
        Assertions.assertEquals("User subscribed to group", groupService.subscribeUserToGroup(sender.getId(), group.getGroupId()));
    }

    @Test
    public void subscriptionUserNotFoundTest() {
        when(userService.findUserById("123456")).thenReturn(null);
        Assertions.assertEquals("User does not exist", groupService.subscribeUserToGroup(sender.getId(), group.getGroupId()));
    }

    @Test
    public void unsubscribeTest() {
        groupService.subscribeUserToGroup(sender.getId(), group.getGroupId());
        when(userService.findUserById(sender.getId())).thenReturn(sender);
        Assertions.assertEquals("User unsubscribed to group", groupService.unsubscribeUserFromGroup(sender.getId(), group.getGroupId()));
    }

    @Test
    public void unsubscribeUserNotFoundTest() {
        when(userService.findUserById("62237dcb40d8d65795a78e35")).thenReturn(null);
        Assertions.assertEquals("User does not exist", groupService.unsubscribeUserFromGroup("62237dcb40d8d65795a78e35", group.getGroupId()));
    }

    @Test
    public void unsubscribeGroupNotFoundTest() {
        when(userService.findUserById(sender.getId())).thenReturn(sender);
        Assertions.assertEquals("Group not found 12345", groupService.unsubscribeUserFromGroup(sender.getId(), "12345"));
    }

    @AfterAll
    public void cleanUp() {
        userRepository.delete(sender);
        userRepository.delete(receiver);
        groupRepository.delete(group1);
    }
}
