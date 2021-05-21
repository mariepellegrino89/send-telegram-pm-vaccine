package com.c19vac.sendtelegrampmvaccine;

import com.c19vac.sendtelegrampmvaccine.httpclient.HttpTelegramClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringDesktopSampleApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(SpringDesktopSampleApplication.class);
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringDesktopSampleApplication.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws InterruptedException {

        //TODO SHITTY CODE, NEED TO FIX
        //works only on chrome and need to place the chromedriver.exe in the right path
        boolean canIScheduleMyVaccine = false;
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        String baseUrl = "https://prenotazionevaccinicovid.regione.lombardia.it/";
        webDriver.get(baseUrl);
        while(!canIScheduleMyVaccine){
            Thread.sleep(1000L);
            //name of the tag on the lombardia vaccine reservation page
            WebElement openModalOne = webDriver.findElement(By.id("openModalOne"));
            String text = openModalOne.getText();
            //age of the people that want to vaccinate
            canIScheduleMyVaccine = text.contains("40");
            if(canIScheduleMyVaccine){
                logger.info("WE WE UAGLIO PUOI PRENOTARE E MO T'ARRIVA NU BELL MESSAGGIO");
                HttpTelegramClient httpTelegramClient = new HttpTelegramClient();
                httpTelegramClient.sendMessageToGroupChat();
            }
            webDriver.navigate().refresh();
        }

    }
}
