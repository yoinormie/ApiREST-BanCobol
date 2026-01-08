package com.example.BanCobolApiREST.Utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;

@Component
public class StringFormatUtils {

    private static final String PREFIX = "ES91";
    private static final int TOTAL_LENGTH = 20;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String formatString (BigDecimal amount){
        long amountTransformed = (long) (amount.doubleValue() * 100);
        return String.format("%011d", amountTransformed);
    }

    public String formatString (BigDecimal amount, String specificFormat){
        long amountTransformed = (long) (amount.doubleValue() * 100);
        return String.format(specificFormat, amountTransformed);
    }

    public BigDecimal formatToNumber(String programResult) {
        if (programResult == null || programResult.trim().isEmpty()) {
            throw new IllegalArgumentException("Resultado COBOL vacío");
        }

        String normalized = programResult.trim()
                .replaceFirst("^0+(?!$)", "");

        return new BigDecimal(normalized);
    }


    public static String generateAccountNumber() {
        int randomDigits = TOTAL_LENGTH - PREFIX.length();
        StringBuilder sb = new StringBuilder(PREFIX);

        for (int i = 0; i < randomDigits; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }
}
