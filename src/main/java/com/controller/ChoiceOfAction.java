package com.controller;
import java.util.List;
public interface ChoiceOfAction {
    String removeRecord(String key) ;
    String search(String key);
    String addAnEntry(String key, String value);
    String fileReading();
    List <String> fileReadingList();
}