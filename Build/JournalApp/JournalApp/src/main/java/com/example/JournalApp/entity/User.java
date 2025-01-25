package com.example.JournalApp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique=true)

    @NonNull
    private String userName;

    @NonNull
    private String password;

    private String email;
    private boolean sentimateAnalysis;

    @DBRef
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;

}
