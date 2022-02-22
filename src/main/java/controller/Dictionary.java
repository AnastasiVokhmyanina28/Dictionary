package controller;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
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
    private String languageType;




    public Dictionary(String path, String languageType, Map<String, String> localMap) {
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
        boolean fth = false;
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            String value = dictionaryType.getPatternKey();
            boolean dfg = this.languageType.equals(dictionaryType.getNumber());
            if (dfg == true) {
                Pattern dfgd = Pattern.compile(value);
                fth  = dfgd.matcher(key).matches();
                break;
            }
        }
            return fth;
    }

    public void saveData() {
        File file = new File(path);
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> entry : localMap.entrySet()) {
                bf.write(entry.getKey() + Console.SPLIT_CHAR + entry.getValue());
                bf.newLine();
            }
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf.close();
            } catch (Exception e) {

            }
        }
    }

}