package com.board.announcement.service;

import com.board.announcement.SpringBootTests;
import com.board.announcement.exceptions.UserNotFoundException;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.stubbing.Answer;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class GroupServiceTest extends SpringBootTests {

    @Mock
    UserRepository userRepository;

    @Mock
    GroupRepository groupRepository;

    @Mock
    CacheManager cacheManager;

    @Mock
    GroupService groupService;

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    Query query;

    @Mock
    User user;

    @Test
    public void subscribeUserToNonExistingGroupTest(){
//        Mockito.<Optional<User>>when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
//        Mockito.when(userRepository.findById(anyString())).thenThrow(new UserNotFoundException("User not found"));
//        Mockito.doNothing().when(query.addCriteria(any()));
//        Mockito.doNothing().when(mongoTemplate.findAndModify(query, any(), User.class));
//
//        assertThrows(UserNotFoundException.class, () -> {
//            groupService.subscribeUserToGroup(anyString(), anyString());
//        });
//        assertThat(groupService.subscribeUserToGroup(anyString(), anyString())).isEqualTo("User subscribed to group");
    }
}
