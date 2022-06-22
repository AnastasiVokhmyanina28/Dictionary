package com.controller;

public interface ChoiceOfAction {
    String removeRecord(String key) throws Exception;
    String search(String key);
    String addAnEntry(String key, String value);
    String fileReading();
}