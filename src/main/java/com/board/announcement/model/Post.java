package com.board.announcement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message")
@Component
public class Post {

    @Transient
    public static final String SEQUENCE_NAME = "message_sequence";

    @Id
    private String postId;

    String senderId;

    String groupId;

    String message;

    public Post(String senderId, String groupId, String message) {
        this.senderId = senderId;
        this.groupId = groupId;
        this.message = message;
    }

}