package ru.vsu.amm.java.annotation.service.impl;

import ru.vsu.amm.java.annotation.Autowired;
import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.annotation.service.SecondService;

import java.util.logging.Logger;

@Component
public class SecondServiceImpl implements SecondService {

    private static final Logger log;

    static {
        log = Logger.getLogger(SecondServiceImpl.class.getName());
    }

    @Autowired
    private FirstServiceImpl firstService;

    public void badMethod() {
        log.info("Call badMethod() from FirstService");
        firstService.goodMethod();
        System.out.println("The method above is deceiving");
    }
}
