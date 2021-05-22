package com.c19vac.sendtelegrampmvaccine.manager.interf;

import org.springframework.stereotype.Service;

public interface NoticeCreator {


    boolean checkStatusAndSendNotification() throws InterruptedException;

}
