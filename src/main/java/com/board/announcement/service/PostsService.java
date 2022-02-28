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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public Post savePost(Post post) {
        Cache cache = cacheManager.getCache("postsCache");
        List<Post> posts = new ArrayList<>();
        Cache.ValueWrapper cacheValue = null;
        if (cache != null) cacheValue = cache.get(post.getGroupId());
        if (cacheValue != null) {
            List tempList = (List) (cacheValue.get());
            if (!tempList.isEmpty() && tempList.size() >= 5) {
                posts = new ArrayList(tempList.subList(0, 5));
            } else posts = tempList;
        }
        posts.add(0, post);
        postRepository.save(post);
        cache.put(post.getGroupId(), posts);

        return post;
    }


    public Post deletePost(Post post) {
        Cache cache = cacheManager.getCache("postsCache");
        List<Post> posts = new ArrayList<>();
        Cache.ValueWrapper cacheValue = null;
        if (cache != null) cacheValue = cache.get(post.getGroupId());
        if (cacheValue != null) {
            List<Post> tempList = (List) (cacheValue.get());
            if (!tempList.isEmpty() && tempList.size() >= 5) {
                posts = new ArrayList(tempList.subList(0, 5));
            } else posts = tempList;
        }
        posts.remove(post);
        postRepository.delete(post);
        cache.put(post.getGroupId(), posts);

        return post;
    }


    public List<Post> getPostsByUserId(String userId) {
        List<Post> result = new ArrayList<>();
        Cache cache = cacheManager.getCache("postsCache");
        List<Post> tempList;
        Cache.ValueWrapper cacheValue = null;

        Optional<User> user = userRepository.findById(String.valueOf(new ObjectId(userId)));

        if (user.isPresent()) {
            for (String groupId : user.get().getGroupIds()) {
                if (cache != null) cacheValue = cache.get(groupId);
                if (cacheValue != null) {
                    tempList = (List) (cacheValue.get());
                } else {
                    tempList = postRepository.findPosts(groupId);
                    if (!tempList.isEmpty()) cache.putIfAbsent(groupId, tempList);
                }
                if (!tempList.isEmpty()) result.addAll(tempList);
            }
        }
        return result;
    }

    public List<Post> getPostsByGroupId(String groupId) {
        return postRepository.findPosts((new ObjectId(groupId)).toString());
    }

}
