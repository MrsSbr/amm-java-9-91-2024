package ru.vsu.amm.java.annotation.service;

import java.util.Set;

public interface ComponentScanningService {

    Set<Class<?>> scanProject();
}
