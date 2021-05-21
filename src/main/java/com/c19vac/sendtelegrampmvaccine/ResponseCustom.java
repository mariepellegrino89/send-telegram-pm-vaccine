package com.c19vac.sendtelegrampmvaccine;

import lombok.Data;

@Data
public class ResponseCustom {

    private boolean result;

    public ResponseCustom(boolean result) {
        this.result=result;
    }
}
