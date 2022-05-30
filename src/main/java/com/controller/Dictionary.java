package com.controller;

import java.util.Map;
import java.util.regex.Pattern;
import com.config.DictionaryType;
import com.model.DictionaryStorage;
import com.utils.KeyNotFoundException;

public class Dictionary implements ChoiceOfAction {
    public static final String NO_KEY = "No key found!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    private Map<String, String> localMap;
    private DictionaryStorage dictionaryStorage;
    private DictionaryType dictionaryType;

    public void saveData() {
        dictionaryStorage.saveData();
    }

    public Dictionary(String path, DictionaryType dictionaryType, Map<String, String> localMap) {
        this.localMap = localMap;
        this.dictionaryType = dictionaryType;
        this.dictionaryStorage = new DictionaryStorage(path, localMap);
        dictionaryStorage.getData();
    }

    @Override
    public void removeRecord(String key) throws KeyNotFoundException {
        if (localMap.containsKey(key)) {
            localMap.remove(key);
            saveData();
        } else {
            throw new KeyNotFoundException(NO_KEY);
        }

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
        if (keyCheck(key) && valueCheck(value)) {
            localMap.put(key, value);
            saveData();
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }
    @Override
    public boolean keyCheck(String key) {
        String patKey =  dictionaryType.getPatternKey();
        return Pattern.matches(patKey, key);
    }
    @Override
    public boolean valueCheck(String value) {
        String patValue =  dictionaryType.getPatternValue();
        return Pattern.matches(patValue, value);
    }
}