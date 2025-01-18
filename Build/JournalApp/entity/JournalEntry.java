package com.example.JournalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection="journal_entries")
public class JournalEntry {


    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
}
