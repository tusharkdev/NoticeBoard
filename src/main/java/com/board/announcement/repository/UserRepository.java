package com.board.announcement.repository;

import com.board.announcement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name:'?0'}")
    User findUser(String firstname);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1}")
    List<User> findAll(String firstname);
}
