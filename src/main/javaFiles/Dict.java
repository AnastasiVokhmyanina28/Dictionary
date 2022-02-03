package src.main.javaFiles;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class Dict {

    public static void main(String[] args)  {
       Map<String, Dictionary> dictionaries;
        dictionaries = new HashMap<>();
        dictionaries.put(DictionaryType.LANGUAGE_TYPE_ONE, creationOne()) ;
        dictionaries.put(DictionaryType.LANGUAGE_TYPE_TWO, creationTwo()) ;

        Console dictionaryConsole = new Console(dictionaries);
        dictionaryConsole.start();
    }

    public static Dictionary creationOne(){
        Map<String,String> localMap = new HashMap<>();
        return new Dictionary(DictionaryType.LANGUAGE_PATH_ONE , DictionaryType.LANGUAGE_TYPE_ONE, new FileHandling(DictionaryType.LANGUAGE_PATH_ONE, localMap).getData());
    }

    public static Dictionary creationTwo(){
        Map<String,String> localMap = new HashMap<>();
        return new Dictionary(DictionaryType.LANGUAGE_PATH_TWO, DictionaryType.LANGUAGE_TYPE_TWO , new FileHandling(DictionaryType.LANGUAGE_PATH_TWO, localMap).getData());

    }

}