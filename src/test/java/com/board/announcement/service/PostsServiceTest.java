package com.board.announcement.service;

import com.board.announcement.model.Group;
import com.board.announcement.model.Post;
import com.board.announcement.model.User;
import com.board.announcement.repository.GroupRepository;
import com.board.announcement.repository.PostRepository;
import com.board.announcement.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostsServiceTest {

    @MockBean
    CacheManager cacheManager;

    @Autowired
    PostRepository postRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsService postsService;

    @Mock
    Cache cache;

    @Mock
    Cache.ValueWrapper cacheValue;

    Post post = new Post();
    Group group = new Group();
    User user1;
    Group group1;
    Post post1;

    @BeforeAll
    void setUp() {
        group.setGroupName("TestGroup");
        group.setDescription("Test group");
        group1 = groupRepository.save(group);
        User user = new User();
        user.setFirstName("Tushar");
        user.setLastName("Kamble");
        user.setMobileNumber("9090909090");
        user1 = userRepository.save(user);
        post.setGroupId(group1.getGroupId());
        post.setSenderId(user1.getId());
        post.setMessage("Farcry5");
    }

    @Test
    void testCacheMiss() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(anyString())).thenReturn(null);
        post1 = postsService.savePost(post);
        List<Post> postsList = postRepository.findPosts(post1.getGroupId());
        Assertions.assertEquals("Farcry5", postsList.get(postsList.size() - 1).getMessage());
    }

    @AfterAll
    void cleanUp() {
        userRepository.delete(user1);
        groupRepository.delete(group1);
        postRepository.delete(post1);
    }


}
