package com.c19vac.sendtelegrampmvaccine.manager.impl;

import com.c19vac.sendtelegrampmvaccine.httpclient.HttpTelegramClient;
import com.c19vac.sendtelegrampmvaccine.manager.interf.NoticeCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NoticeCreatorPiemonte implements NoticeCreator {

    private final ChromeDriver chromeDriver;

    private final HttpTelegramClient httpTelegramClient;

    private final String groupChatId;

    private final String messageTextPiemonte;

    private boolean firstTime = true;

    public static Logger logger = LogManager.getLogger(NoticeCreatorLombardia.class);

    public NoticeCreatorPiemonte(ChromeDriver chromeDriver, String groupChatId, String messageTextPiemonte,HttpTelegramClient httpTelegramClient) {
        this.chromeDriver = chromeDriver;
        this.httpTelegramClient = httpTelegramClient;
        this.groupChatId = groupChatId;
        this.messageTextPiemonte = messageTextPiemonte;
    }

    @Override
    public boolean checkStatusAndSendNotification() throws InterruptedException {
        AtomicBoolean canIScheduleMyVaccine = new AtomicBoolean(false);
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        boolean notified = false;
        String baseUrl = "https://www.ilpiemontetivaccina.it/preadesione/#/";
        if (firstTime) {
            chromeDriver.get(baseUrl);
        }
        //name of the tag on the lombardia vaccine reservation page
        List<WebElement> classNames = chromeDriver.findElements(By.className("q-item__label"));

        canIScheduleMyVaccine.set(false);
        classNames.forEach(w -> canIScheduleMyVaccine.set(w.getText().contains("Persone in età compresa tra i 30 ed i 39 anni")));
        //age of the people that want to vaccinate
        if (canIScheduleMyVaccine.get()) {
            logger.info("WE WE UAGLIO PUOI PRENOTARE E MO T'ARRIVA NU BELL MESSAGGIO");
            httpTelegramClient.sendMessageToGroupChat(groupChatId, messageTextPiemonte);
            notified = true;
        }
        chromeDriver.navigate().refresh();
        firstTime = false;
        return notified;
    }

}
