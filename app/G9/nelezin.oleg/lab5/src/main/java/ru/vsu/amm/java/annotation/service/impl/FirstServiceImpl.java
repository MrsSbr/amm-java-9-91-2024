package ru.vsu.amm.java.annotation.service.impl;

import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.annotation.service.FirstService;

import java.util.logging.Logger;

@Component
public class FirstServiceImpl implements FirstService {
    private static final Logger log;

    static {
        log = Logger.getLogger(FirstServiceImpl.class.getName());
    }

    public void goodMethod() {
        log.info("Call goodMethod() from SecondService");
        System.out.println("I'm good!");
    }

}
