package ru.vsu.amm.java.service;


import ru.vsu.amm.java.storage.ResponsesStorage;

public interface ResponsesParserService {

    void parseFile(String filePath, ResponsesStorage responsesStorage);
}
