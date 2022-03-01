package config;
public enum DictionaryType {

  DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[а-яёА-ЯЁ]+",  "../resources/Dictionary.txt"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[а-яёА-ЯЁ]+", "../resources/digitalDictionary.txt");

    private final Integer number;
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    public Integer getNumber() {
        return number;
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


    DictionaryType(Integer number, String patternKey, String patternValue, String dictionaryPath) {
        this.number = number;
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }
    
}