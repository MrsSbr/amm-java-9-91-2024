package ru.vsu.amm.java;

@Component
public class FirstService {

    @Autowired
    private SecondService secondService;

    public void badMethod() {
        secondService.goodMethod();
        System.out.println("The method above is deceiving");
    }
}
