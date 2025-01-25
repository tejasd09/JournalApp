package com.example.JournalApp.schedular;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.enums.Sentiment;
import com.example.JournalApp.repository.UserRepositoryImpl;
import com.example.JournalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired


//    @Scheduled(cron = "0 0 12 ? * SUN")
//    @Scheduled(cron = "* * * * * *")
    public void fetchUsersAndSendMail()
    {
        List<User> users=userRepository.getUserForSA();;
        for(User user:users)
        {
           List<JournalEntry> journalEntries=user.getJournalEntries();
           List<Sentiment>  sentiments= journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
           Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
           for(Sentiment sentiment:sentiments)
           {
               if(sentiment!=null)
               {
                   sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
               }
           }
           Sentiment mostFrequentSentiment=null;
           int maxCount=0;
           for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
               if(entry.getValue()>maxCount){
                   maxCount=entry.getValue();
                   mostFrequentSentiment=entry.getKey();
               }
           }
           if(mostFrequentSentiment !=null)
           {
               emailService.sendMail(user.getEmail(),"Sentiment for last 7 days" , mostFrequentSentiment.toString());
           }

        }
    }
}
