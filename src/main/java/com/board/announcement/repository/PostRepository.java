package com.board.announcement.repository;


import java.util.List;

import com.board.announcement.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{ 'groupId' : ?0 }")
    List<Post> findPosts(String groupId);

    @Query("{ 'id' : ?0 }")
    List<Post> findAll(int postId);

    public long count();

}
