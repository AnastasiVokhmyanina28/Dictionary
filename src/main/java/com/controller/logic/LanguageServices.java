package com.controller.logic;

import com.services.dao.impl.RowDaoImpl;
import com.services.dao.impl.WordDaoImpl;

import java.util.AbstractMap;
import java.util.List;

public class LanguageServices {

    private RowDaoImpl row;

    private WordDaoImpl word;

    public LanguageServices(RowDaoImpl row, WordDaoImpl word) {
        this.row = row;
        this.word = word;
    }

    public String search(String name) {return row.lineСheck(name).toString();
    }

    public List <String> fileReadingList(){
        return row.fileReadingList();
    }

    public String removeRecord(String name){
        return row.removeRecord(name);
    }

    public  String addAnEntry(String key, String value){
        AbstractMap.SimpleEntry<Integer, Integer> pair = word.add(key, value);
        if (pair == null){
           return "error";
        }
        else {
        if (row.isThereACoupleOf(pair.getKey(), pair.getValue())){
            return "добавлвила";
        }
        else {
            return "пара есть атстань";
        }
        }
    }










}