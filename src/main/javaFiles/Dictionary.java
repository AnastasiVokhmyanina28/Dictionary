package src.main.javaFiles;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Dictionary {
    public static final String SPLIT_CHAR = ":";
    public static final String NO_KEY = "No key found!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    private Map<String, String> localMap;
    DictionaryType dictionaryType ;

    public FileHandling getFileHandling() {
        return fileHandling;
    }

    private  FileHandling fileHandling ;
    private HashMap<String,String> keyConditions;
    private String path;
    private String languageType;



    public Dictionary(String path,String languageType, Map<String, String> localMap){
        this.path = path;
        this.localMap = localMap;
        this.languageType = languageType;
        this.fileHandling = new FileHandling(path, localMap);
        this.dictionaryType = new DictionaryType();
        this.keyConditions = new HashMap<String, String>(){{
           put(dictionaryType.LANGUAGE_TYPE_ONE, dictionaryType.PATTERN_TYPE_TWO );
            put(dictionaryType.LANGUAGE_TYPE_TWO,dictionaryType.PATTERN_TYPE_THREE );

       }};

        fileHandling.getData();
    }



    public void removeRecord(String key) throws Exception {
        if (localMap.containsKey(key)){
            localMap.remove(key);
        }
        else {
        throw new Exception(NO_KEY);
        }
    }

    public Map<String, String>  getLocalMap() {
        return  localMap;
    }

    public  String   recordSearch(String key) {
        String search = localMap.get(key);
        if(search != null){
            String searchResult = key + SPLIT_CHAR + search;
           return searchResult ;
        }
        else {
          return KEY_DOES_NOT_EXIST;
        }

    }


    public String  addAnEntry(String key, String value){

        boolean matches = Pattern.matches( dictionaryType.PATTERN_TYPE_ONE,value );
        if (keyCheck(key) && matches) {
            localMap.put(key, value);
           return ADD_KEY;
        }
        else {
          return SIMILARITY_TO_THE_PATTERN;
        }
    }

    private boolean keyCheck(String key) {

        String value = keyConditions.get(this.languageType);
        return Pattern.matches(value, key);
    }

}