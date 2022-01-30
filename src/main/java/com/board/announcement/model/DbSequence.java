package com.board.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

// Mongodb annotation
// marks a class for the domain object that we want to persist in the db
@Document(collection = "db_sequence")
// Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
// Spring stereotype annotation
@Component
public class DbSequence {

    @Id
    String id;
    // describes the field name as it will be represented in mongodb bson document
    // offers the name to be different than the field name of the class
    @Field("sequence_number")
    int sequence;
}
