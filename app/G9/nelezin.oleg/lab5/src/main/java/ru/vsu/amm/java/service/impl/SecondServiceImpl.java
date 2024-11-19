package ru.vsu.amm.java.service.impl;


import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.service.SecondService;

import java.util.logging.Logger;

@Component
public class SecondServiceImpl implements SecondService {
    private static final Logger log;

    static {
        log = Logger.getLogger(SecondServiceImpl.class.getName());
    }

    public void goodMethod() {
        log.info("Call goodMethod() from SecondService");
        System.out.println("I'm good!");
    }
}
