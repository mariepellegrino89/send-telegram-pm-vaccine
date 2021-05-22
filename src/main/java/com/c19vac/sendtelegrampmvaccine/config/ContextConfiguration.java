package com.c19vac.sendtelegrampmvaccine.config;

import com.c19vac.sendtelegrampmvaccine.httpclient.HttpTelegramClient;
import com.c19vac.sendtelegrampmvaccine.manager.impl.NoticeCreatorLombardia;
import com.c19vac.sendtelegrampmvaccine.manager.impl.NoticeCreatorPiemonte;
import com.c19vac.sendtelegrampmvaccine.manager.interf.NoticeCreator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class ContextConfiguration {

    @Value("${group.chat.id}")
    private String groupChatId;

    @Value("${message.text.lombardia}")
    private String messageTextLombardia;

    @Value("${message.text.piemonte}")
    private String messageTextPiemonte;


    @Bean
    @Scope(scopeName = "singleton")
    public ChromeDriver getChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        return new ChromeDriver();
    }

//    @Bean
//    @Scope(scopeName = "singleton")
//    public FirefoxDriver firefoxDriver(){
//        return new FirefoxDriver();
//    }
//
//    @Bean
//    @Scope(scopeName = "singleton")
//    public OperaDriver operaDriver(){
//        return new OperaDriver();
//    }
//
//    @Bean
//    @Scope(scopeName = "singleton")
//    public EdgeDriver edgeDriver(){
//        return new EdgeDriver();
//    }
//
//    @Bean
//    @Scope(scopeName = "singleton")
//    public InternetExplorerDriver internetExplorerDriver(){
//        return new InternetExplorerDriver();
//    }

    @Bean(name = "noticeCreators")
    @Scope(scopeName = "singleton")
    public List<NoticeCreator> getNoticeCreators(ChromeDriver chromeDriver, HttpTelegramClient httpTelegramClient){
        List<NoticeCreator> noticeCreators = new ArrayList<>();
        noticeCreators.add(new NoticeCreatorLombardia(chromeDriver, groupChatId, messageTextLombardia, httpTelegramClient));
        noticeCreators.add(new NoticeCreatorPiemonte(chromeDriver, groupChatId, messageTextPiemonte, httpTelegramClient));
        return noticeCreators;
    }
}
