package com.c19vac.sendtelegrampmvaccine.manager.impl;

import com.c19vac.sendtelegrampmvaccine.httpclient.HttpTelegramClient;
import com.c19vac.sendtelegrampmvaccine.manager.interf.NoticeCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

public class NoticeCreatorLombardia implements NoticeCreator {


    private final ChromeDriver chromeDriver;

    private final List<String> groupChatId;

    private final String messageText;

    private final HttpTelegramClient httpTelegramClient;

    public NoticeCreatorLombardia(ChromeDriver chromeDriver, List<String> groupChatId, String messageText, HttpTelegramClient httpTelegramClient) {
        this.chromeDriver = chromeDriver;
        this.groupChatId = groupChatId;
        this.messageText = messageText;
        this.httpTelegramClient = httpTelegramClient;
    }

    private boolean firstTime = true;

    public static Logger logger = LogManager.getLogger(NoticeCreatorLombardia.class);

    @Override
    public boolean checkStatusAndSendNotification() throws InterruptedException {
        try {
            boolean canIScheduleMyVaccine;
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            boolean notified = false;
            String baseUrl = "https://prenotazionevaccinicovid.regione.lombardia.it/";
            if (firstTime) {
                chromeDriver.get(baseUrl);
            }
            //name of the tag on the lombardia vaccine reservation page
            WebElement openModalOne = chromeDriver.findElement(By.id("openModalOne"));
            String text = openModalOne.getText();
            //age of the people that want to vaccinate
            canIScheduleMyVaccine = text.contains("40");
            if (canIScheduleMyVaccine) {
                logger.info("WE WE UAGLIO PUOI PRENOTARE E MO T'ARRIVA NU BELL MESSAGGIO");
                groupChatId.forEach(g -> {
                    httpTelegramClient.sendMessageToGroupChat(g, messageText);
                });
                notified = true;
            }
            chromeDriver.navigate().refresh();
            firstTime = false;
            return notified;
        } catch (Exception e){
            logger.error("Exception occured: ", e);
            return false;
        }
    }

}
