package com.controller;

import java.util.Map;
import com.controller.validation.Validator;
import com.model.DictionaryType;
import com.model.DictionaryStorage;
import com.view.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dictionary implements ChoiceOfAction {
    public static final String NO_KEY = "No key found!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    private Map<String, String> localMap;
    private DictionaryStorage dictionaryStorage;
    private DictionaryType dictionaryType;
    @Autowired
    private Map<DictionaryType, Validator> validator;


    @Autowired
    public Dictionary(@Qualifier("map")DictionaryType dictionaryType) {
        this.dictionaryType = dictionaryType;
        this.dictionaryStorage = new DictionaryStorage(dictionaryType.getDictionaryPath());
        localMap =  dictionaryStorage.getData();
    }

    public void saveData() {
        dictionaryStorage.saveData();
    }


    @Override
    public String removeRecord(String key)  {
        if (localMap.containsKey(key)) {
            localMap.remove(key);
            saveData();
        } else {
          return NO_KEY;
        }
        return Console.KEY_DELETE;
    }

    @Override
    public String fileReading(){
        StringBuilder dictionaryContent = new StringBuilder();
        for (Map.Entry<String,String> pair : localMap.entrySet()) {
            dictionaryContent.append(pair.getKey() + DictionaryType.getSymbol() + pair.getValue() + "\n" ) ;
        }
        saveData();
        return dictionaryContent.toString();
    }

    @Override
    public String search(String key) {
        String search = localMap.get(key);
        if (search != null) {
            String searchResult = key + DictionaryType.getSymbol() + search;
            saveData();
            return searchResult;
        } else {
            return KEY_DOES_NOT_EXIST;
        }

    }

    @Override
    public String addAnEntry(String key, String value) {
        if (validator.get(dictionaryType).validPair(key, value)) {
            localMap.put(key, value);
            saveData();
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }

}