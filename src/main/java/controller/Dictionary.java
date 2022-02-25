package controller;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import config.DictionaryType;
import model.FileHandling;
import view.Console;

public class Dictionary {
    public static final String NO_KEY = "No key found!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    private Map<String, String> localMap;
    private FileHandling fileHandling;
    private String path;
    private Integer languageType;

    public FileHandling getFileHandling() {
        return fileHandling;
    }

    public void getSaveData() {
        fileHandling.saveData();
    }


    


    public Dictionary(String path, Integer languageType, Map<String, String> localMap) {
        this.path = path;
        this.localMap = localMap;
        this.languageType = languageType;
        this.fileHandling = new FileHandling(path, localMap);


        fileHandling.getData();
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
            String searchResult = key + Console.SPLIT_CHAR + search;
            return searchResult;
        } else {
            return KEY_DOES_NOT_EXIST;
        }

    }


    public String addAnEntry(String key, String value) {
        boolean matches = Pattern.matches(DictionaryType.DICTIONARY_ONE.getPatternValue(), value);
        if (keyCheck(key) && matches) {
            localMap.put(key, value);
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }

    private boolean keyCheck(String key) {
        boolean check = false;
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            String value = dictionaryType.getPatternKey();
            boolean numberComparison = languageType.equals(dictionaryType.getNumber());
            if (numberComparison == true) {
                Pattern pattern = Pattern.compile(value);
                check = pattern.matcher(key).matches();
                break;
            }
        }
        return check;
    }
}