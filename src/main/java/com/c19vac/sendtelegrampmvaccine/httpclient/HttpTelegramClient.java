package com.c19vac.sendtelegrampmvaccine.httpclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@PropertySource(value = "/application.properties")
public class HttpTelegramClient {
    private static final Logger logger = LogManager.getLogger(HttpTelegramClient.class);

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
        logger.info(resourceUrl);
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        logger.info("Response " + response);
    }


}
