package com.example.JournalApp.cache;

import com.example.JournalApp.entity.ConfigJournalAppEntity;
import com.example.JournalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE=new HashMap<>();

    @PostConstruct
    public  void init()
    {
        List<ConfigJournalAppEntity> all=configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }

    }
}
