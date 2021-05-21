package com.c19vac.sendtelegrampmvaccine.httpclient;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Log
@Service
public class HttpTelegramClient {
    @Value("${bot.token}")
    private String botToken;

    @Value("${group.chat.id}")
    private String groupChatId;

    @Value("${message.text}")
    private String messageText;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${api.action}")
    private String apiAction;

    public void sendMessageToGroupChat(){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = apiBaseUrl+botToken+apiAction+"?chat_id="+groupChatId+"&text="+messageText;
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        log.info("Response " + response);
    }


}
