package controller;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import config.DictionaryType;
import model.DictionaryStorage;
import view.Console;

public class Dictionary {
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


    public void removeRecord(String key) throws Exception {
        if (localMap.containsKey(key)) {
            localMap.remove(key);
        } else {
            throw new Exception(NO_KEY);
        }
    }

    public Map<String, String> getLocalMap() {
        return localMap;
    }

    public String recordSearch(String key) {
        String search = localMap.get(key);
        if (search != null) {
            String searchResult = key + DictionaryType.getSymbol() + search;
            return searchResult;
        } else {
            return KEY_DOES_NOT_EXIST;
        }

    }


    public String addAnEntry(String key, String value) {
        boolean matches = Pattern.matches(dictionaryType.getPatternValue(), value);
        if (keyCheck(key) && matches) {
            localMap.put(key, value);
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }

    private boolean keyCheck(String key) {
        String patKey =  dictionaryType.getPatternKey();
        return Pattern.matches(patKey, key);
    }
}