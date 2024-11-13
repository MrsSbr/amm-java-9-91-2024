package ru.vsu.amm.java.service.impl;

import lombok.Getter;
import ru.vsu.amm.java.annotation.Autowired;
import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.service.FirstService;

import java.util.logging.Logger;

@Component
public class FirstServiceImpl implements FirstService {

    private static final Logger log;

    static {
        log = Logger.getLogger(FirstServiceImpl.class.getName());
    }

    @Getter
    @Autowired
    private SecondServiceImpl secondService;

    public void badMethod() {
        log.info("Call badMethod() from FirstService");
        secondService.goodMethod();
        System.out.println("The method above is deceiving");
    }

}
