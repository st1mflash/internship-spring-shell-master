package ru.liga.internshipspringshell.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.internshipspringshell.model.Currency;

import java.io.*;
import java.math.BigDecimal;

@ShellComponent
public class Controller {

    @ShellMethod("Greating!")
    public String hello(@ShellOption String name) {
        return "Hello, " + name + "!";
    }

    @ShellMethod("Displays currency codes and names.")
    public String codes () throws IOException {
        return Currency.getCodeNameList();
    }

    @ShellMethod("Converts the transferred amount from one currency to another.")
    public String convert (
            @ShellOption BigDecimal amount,
            @ShellOption String from,
            @ShellOption String to
    ) throws IOException {
        return to.toUpperCase() + ": " + Currency.convertCurrency(amount, new Currency(from), new Currency(to));
    }
}
