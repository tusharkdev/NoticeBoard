package com.board.announcement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@Component
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private String id;

    String firstName;

    String lastName;

    String mobileNumber;

//    @DBRef(lazy = true)
    List<String> groupIds;

//    public User(String firstName, String lastName, String mobileNumber){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.mobileNumber = mobileNumber;
//        this.groupIds = new ArrayList<>();
//    }

    public void addGroupIds(String groupId) {
        groupIds.add(groupId);
    }
}
