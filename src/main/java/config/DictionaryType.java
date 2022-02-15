package config;
public enum DictionaryType {

    DICTIONARY_ONE("1","^[a-zA-Z]{4}$", "[а-яёА-ЯЁ]+", "../resources/Dictionary.txt"),
    DICTIONARY_TWO("2","^[0-9]{5}$", "[а-яёА-ЯЁ]+", "../resources/digitalDictionary.txt");

    private final String NUMBER;
    private final String PATTERN_KEY;
    private final String PATTERN_VALUE;
    private final String DICTIONARY_PATH;

    public String getNUMBER() {
        return NUMBER;
    }

    public String getPATTERN_KEY() {
        return PATTERN_KEY;
    }

    public String getPATTERN_VALUE() {
        return PATTERN_VALUE;
    }

    public String getDICTIONARY_PATH() {
        return DICTIONARY_PATH;
    }


    DictionaryType(String NUMBER, String PATTERN_KEY, String PATTERN_VALUE, String DICTIONARY_PATH) {
        this.NUMBER = NUMBER;
        this.PATTERN_KEY = PATTERN_KEY;
        this.PATTERN_VALUE = PATTERN_VALUE;
        this.DICTIONARY_PATH = DICTIONARY_PATH;
    }

}


