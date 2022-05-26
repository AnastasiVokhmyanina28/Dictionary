package com.config;

<<<<<<< Updated upstream:src/main/java/config/DictionaryType.java
    DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[а-яёА-ЯЁ]+", "../resources/Dictionary.txt"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[а-яёА-ЯЁ]+", "../resources/digitalDictionary.txt");
=======
public class DictionaryType {
>>>>>>> Stashed changes:src/main/java/com/config/DictionaryType.java

    private static final String splitChar = ":";
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;


    DictionaryType(String patternKey, String patternValue, String dictionaryPath ) {
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }

    public static String getSymbol() {
        return splitChar;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public String getPatternValue() {
        return patternValue;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }
}