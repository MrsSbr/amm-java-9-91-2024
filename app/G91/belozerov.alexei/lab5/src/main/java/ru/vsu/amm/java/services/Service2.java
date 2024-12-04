package ru.vsu.amm.java.services;

import ru.vsu.amm.java.annotations.Autowired;
import ru.vsu.amm.java.annotations.Component;

@Component
public class Service2 {

    @Autowired
    Service1 service1;

    public String getMessage() {
        return "Service2 method is called -> " + service1.getMessage();
    }

    public Service1 getService1() {
        return service1;
    }
}
