package com.board.announcement.service;

import com.board.announcement.model.Post;
import com.board.announcement.model.User;
import com.board.announcement.repository.PostRepository;
import com.board.announcement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public Post getPostById(Post post){
        Cache cache = cacheManager.getCache("postsCache");
        List<Post> posts = new ArrayList<>();
        Cache.ValueWrapper cacheValue = cache.get(post.getGroupId());
        if (cacheValue != null) {
            List<Post> tempList = (List) (cacheValue.get());
            if(tempList.size() >= 5){
                posts = new ArrayList(tempList.subList(0,5));
            }
            else
                posts=tempList;
        }
        posts.add(0, post);
        postRepository.save(post);
        cache.put(post.getGroupId(), posts);

        return post;
    }


    public List<Post> getPostsByUserId( String userId) {
        List<Post> result = new ArrayList<>();
        Cache cache = cacheManager.getCache("postsCache");
        List<Post> tempList;

        User user = userRepository.findById(String.valueOf(new ObjectId(userId))).get();

        for (String groupId : user.getGroupIds()) {
            Cache.ValueWrapper cacheValue = cache.get(groupId);
            if (cacheValue != null) {
                tempList = (List) (cacheValue.get());
            } else {
                tempList = postRepository.findPosts(groupId);
                if (!tempList.isEmpty())
                    cache.putIfAbsent(groupId, tempList);
            }
            if (!tempList.isEmpty())
                result.addAll(tempList);
        }
        return result;
    }
}