package com.controller;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import com.controller.validation.Validator;
import com.model.DictionaryType;
import com.utils.KeyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class FileOperation implements ChoiceOfAction {
    private final String path;
    private DictionaryType dictionaryType;
    @Autowired
    private Map<DictionaryType, Validator> validator;

    public FileOperation(@Qualifier("file")DictionaryType dictionaryType)  {
        this.dictionaryType = dictionaryType;
        this.path = dictionaryType.getDictionaryPath();
    }

    @Override
    public String fileReading() {
        StringBuilder data = new StringBuilder();
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  data.toString();
    }

    @Override
    public String addAnEntry(String key, String value) {
        if (validator.get(dictionaryType).validPair(key, value)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
                bufferedWriter.write(key + DictionaryType.getSymbol() + value + "\n");
                bufferedWriter.flush();
            } catch (IOException e) {
            }
            return Dictionary.ADD_KEY;
        } else {
            return Dictionary.SIMILARITY_TO_THE_PATTERN;
        }
    }

    @Override
    public void removeRecord(String key) throws KeyNotFoundException {
        boolean deleteLine = false;
        String[] readLines = fileReading().split("\n");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < readLines.length; i++) {
                if (!key.equals(readLines[i].split(DictionaryType.getSymbol())[0])) {
                    bufferedWriter.write(readLines[i] + "\n");
                } else {
                    deleteLine = true;
                }
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!deleteLine) {
            throw new KeyNotFoundException(Dictionary.NO_KEY);
        }
    }

    @Override
    public String search(String key) {

        String[] stringsSearch = fileReading().split("\n");

            for (int i = 0; i < stringsSearch.length; i++) {
                if (key.equals(stringsSearch[i].split(DictionaryType.getSymbol())[0])) {
                   return stringsSearch[i];
                }
            }
    return Dictionary.NO_KEY;
    }
}
