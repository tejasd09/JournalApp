package com.example.JournalApp.service;

import com.example.JournalApp.apiResponse.WeatherResponse;
import com.example.JournalApp.cache.AppCache;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

//    private static final String api="http://api.weatherstack.com/current?access_key=<api_key>&query=<city>";

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city)
    {
        String finalApi=appCache.APP_CACHE.get("weather_api").replace("<city>",city).replace("api_key",apikey);
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        WeatherResponse body=response.getBody();
        return body;
    }

}
