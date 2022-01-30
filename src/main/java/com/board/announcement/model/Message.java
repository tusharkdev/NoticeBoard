package com.board.announcement.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message")
@Component
public class Message {

    @Transient
    public static final String SEQUENCE_NAME = "message_sequence";

    @Id
    private int id;
    @Field("sender")
    String sender;
    @Field("channelName")
    String channelName;
    @Field("msg")
    String msg;

}
