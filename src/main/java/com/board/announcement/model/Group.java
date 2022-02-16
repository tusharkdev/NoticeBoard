package com.board.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "group")
@Component
public class Group {

    @Transient
    public static final String SEQUENCE_NAME = "group_sequence";

    @Id
    private String groupId;
    String groupName;
    String description;
    String SocietyId;
    List<String> subscribers;

}
