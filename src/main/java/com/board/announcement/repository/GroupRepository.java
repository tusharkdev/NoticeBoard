package com.board.announcement.repository;

import java.util.List;

import com.board.announcement.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    @Query("{name:'?0'}")
    Group findGroup(String groupName);

    @Query(value = "{category:'?0'}", fields = "{'name' : 1}")
    List<Group> findAll(String groupName);

    public long count();
}
