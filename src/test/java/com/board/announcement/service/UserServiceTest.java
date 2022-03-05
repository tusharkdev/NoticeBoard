package com.board.announcement.service;

import com.board.announcement.model.User;
import com.board.announcement.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    User user1 = null;

    @BeforeAll
    void setUp() {
        User user = new User();
        user.setFirstName("Tushar");
        user.setLastName("Kamble");
        user.setMobileNumber("9090909090");
        user1 = userService.saveUser(user);
    }


    @Test
    void testFindById() {
        User user = userService.findUserById(user1.getId());
        Assertions.assertEquals("Tushar", user.getFirstName());
    }

    @AfterAll
    void cleanUp() {
        userRepository.delete(user1);
    }
}
