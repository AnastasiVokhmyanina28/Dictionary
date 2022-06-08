package com.config;

import com.Dict;
import com.model.DictionaryStorage;
import com.view.Console;
import com.controller.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@ComponentScan("com")
@PropertySource("classpath:config.properties")
public class SpringConfig {
    @Value("#{${valuesMap}}")
    Map<Integer, String> dictionaryTypeMap;
    private int dictionaryTypeOne = 1;
    private int dictionaryTypeTwo = 2;

    @Bean
    public Map<Integer, DictionaryType> getDictionaryTypeMap() {
        Map<Integer, DictionaryType> dictionaryMap = new HashMap<>();
        for (Map.Entry<Integer, String> pair : dictionaryTypeMap.entrySet()) {
            String[] m = pair.getValue().split(",");
            dictionaryMap.put(pair.getKey(), new DictionaryType(m[0], m[1], m[2]));
        }
        return dictionaryMap;
    }

    @Bean
    public Map<Integer, DictionaryStorage> getDictionaryStorageMap(){
        Map<Integer, DictionaryStorage> dictionaryStorageMap = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : getDictionaryTypeMap().entrySet()){
            dictionaryStorageMap.put(pair.getKey(), new DictionaryStorage(pair.getValue().getDictionaryPath()));
        }
        return dictionaryStorageMap;
    }

    @Bean
    public Map<DictionaryType, Validator> getValidator() {
        Map<DictionaryType, Validator> validator = new HashMap<>();
        for (Map.Entry<Integer, DictionaryType> pair : getDictionaryTypeMap().entrySet()) {
            DictionaryType dictionaryType = pair.getValue();
            validator.put(dictionaryType, new Validator(dictionaryType.getPatternKey(), dictionaryType.getPatternValue() ));
        }
        return validator;
    }


    @Bean("file")
    public DictionaryType getFileDictionary() {
        return getDictionaryTypeMap().get(dictionaryTypeTwo);
    }

    @Bean("map")
    public DictionaryType getMapDictionary() {
        return getDictionaryTypeMap().get(dictionaryTypeOne);
    }

    @Bean
    public Console getConsole() {
        return new Console();
    }
    
}
