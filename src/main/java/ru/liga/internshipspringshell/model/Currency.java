package ru.liga.internshipspringshell.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

public class Currency {
    private final static String pathFileCurrency = "src/main/resources/tsv/currencies.tsv";
    private String code;
    private int nominal;
    private String name;
    private BigDecimal money;

    public Currency(String code) throws IOException {
        code = code.toUpperCase();

        if(code.equals("RUB")) {
            this.code = code;
            this.name = "Российский рубль";
            this.money = BigDecimal.valueOf(1.00000000000);
            this.nominal = 1;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(pathFileCurrency));
            String fileLine = reader.readLine();
            while (fileLine != null) {
                if (Objects.equals(fileLine.split("\t")[0], code)) {
                    this.code = code;
                    this.nominal = Integer.parseInt(fileLine.split("\t")[1]);
                    this.name = fileLine.split("\t")[2];
                    this.money = BigDecimal.valueOf(Double.parseDouble(fileLine.split("\t")[3].replace(",", ".")));
                    break;
                }
                fileLine = reader.readLine();
            }
            reader.close();

            if(this.code == null) {
                throw new IOException("Не удалось определить валюту с кодом - " + code);
            }
        }
    }
    /*
    public BigDecimal convertToRub() {
        return Objects.requireNonNull(this.money).multiply(BigDecimal.valueOf(this.nominal));
    }
    */
    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
    public BigDecimal getMoney(){
        return this.money;
    }
    public int getNominal(){
        return this.nominal;
    }
    public BigDecimal getOneNominalMoney() {
        return this.money.divide(BigDecimal.valueOf(this.nominal), 10, BigDecimal.ROUND_UP);
    }
    public static BigDecimal convertCurrency(BigDecimal amount, Currency from, Currency to) {
        return amount.multiply(from.getOneNominalMoney().divide(to.getOneNominalMoney(),10,  BigDecimal.ROUND_UP));
    }
    public static String getCodeNameList() throws IOException {
        StringBuilder resultCodes = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(pathFileCurrency));
        reader.readLine();

        String line = reader.readLine();

        while (line != null) {
            resultCodes
                    .append(line.split("\t")[0])
                    .append(" ")
                    .append(line.split("\t")[2])
                    .append("\n");

            line = reader.readLine();
        }
        reader.close();
        return resultCodes.toString();
    }

}
