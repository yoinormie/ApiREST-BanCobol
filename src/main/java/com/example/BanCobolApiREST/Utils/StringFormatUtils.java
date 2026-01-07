package com.example.BanCobolApiREST.Utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class StringFormatUtils {

    public String formatString (BigDecimal amount){
        long amountTransformed = (long) (amount.doubleValue() * 100);
        return String.format("%011d", amountTransformed);
    }

    public String formatString (BigDecimal amount, String specificFormat){
        long amountTransformed = (long) (amount.doubleValue() * 100);
        return String.format(specificFormat, amountTransformed);
    }

    public BigDecimal formatToNumber(String programResult){
        if (programResult == null || programResult.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal numberResult = new BigDecimal(programResult.trim());
        //
        return numberResult.movePointLeft(2).setScale(2, RoundingMode.HALF_UP);
    }
}
