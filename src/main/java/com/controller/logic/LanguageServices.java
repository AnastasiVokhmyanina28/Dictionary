package com.controller.logic;

import com.services.dao.impl.RowDaoImpl;
import com.services.dao.impl.WordDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;

@Component
public class LanguageServices implements ChoiceOfAction {
    private final String ERROR = "Ошибка! Проверьте корректность вводимых данных!";
    private final String  ADD_PAIR = "Pair successfully added!";
    @Autowired
    private RowDaoImpl row;
        @Autowired
    private WordDaoImpl word;
        
        

    public RowDaoImpl getRow() {
        return row;
    }

    public WordDaoImpl getWord() {
        return word;
    }


    @Override
    public String search(String name) {
        return row.lineСheck(name).toString();
    }
    @Override
    public List<String> fileReadingList() {
        return row.fileReadingList();
    }

    @Override
    public String removeRecord(String name) {
        return row.removeRecord(name);
    }

    @Override
    public String addAnEntry(String key, String value) {
        AbstractMap.SimpleEntry<Integer, Integer> pair = word.add(key, value);
        if (pair == null) {
            return ERROR;
        } else {
            if (row.isThereACoupleOf(pair.getKey(), pair.getValue())) {
                return ADD_PAIR;
            } else {
                return "пара есть атстань";
            }
        }
    }

    @Override
    public String fileReading() {
        return null;
    }

}