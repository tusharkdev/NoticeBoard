package com.board.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "channel")
@Component
public class Channel {

    @Transient
    public static final String SEQUENCE_NAME = "channel_sequence";

    @Id
    private int channelId;
    @Field("channelname")
    String channelName;
    @Field("description")
    String description;
    @Field("subscribers")
    List<User> subscriberList;

}
