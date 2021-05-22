package com.c19vac.sendtelegrampmvaccine;

import com.c19vac.sendtelegrampmvaccine.httpclient.HttpTelegramClient;
import com.c19vac.sendtelegrampmvaccine.manager.interf.NoticeCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class SpringDesktopSampleApplication implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(SpringDesktopSampleApplication.class);

    @Autowired
    private List<NoticeCreator> noticeCreators;

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringDesktopSampleApplication.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws InterruptedException {

        while(!noticeCreators.isEmpty()){
            List<NoticeCreator> notifiedRegions = new ArrayList<>();
            for (NoticeCreator noticeCreator : noticeCreators){
                boolean notificationResult = noticeCreator.checkStatusAndSendNotification();
                if(notificationResult){
                    notifiedRegions.add(noticeCreator);
                }
            }
            notifiedRegions.forEach(n -> noticeCreators.remove(n));
        }

    }
}
