package com.example.JournalApp.repository;

import com.example.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA()
    {
        Query query=new Query();
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where(" sentimateAnalysis").is(true));
        //query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));
        List<User>  users= mongoTemplate.find(query,User.class);
        return users;
    }
}
