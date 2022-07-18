package com.controller;
import java.io.*;
import com.view.Console;
import com.controller.validation.Validator;
import com.model.DictionaryType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FileOperation implements ChoiceOfAction {
    private final String path;
    private static final boolean flexibility = true;
    private final Validator validator;

    public FileOperation(@Qualifier("file")DictionaryType dictionaryType)  {
        this.path = dictionaryType.getDictionaryPath();
        this.validator  = new Validator(dictionaryType.getPatternKey(), dictionaryType.getPatternValue());
    }

    @Override
    public String fileReading() {
        StringBuilder data = new StringBuilder();
        String line = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(new ClassPathResource(path).getFile()))){
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
        if (validator.validPair(key, value)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new ClassPathResource(path).getFile(), flexibility))) {
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
    public String removeRecord(String key)  {
        boolean deleteLine = false;
        String[] readLines = fileReading().split("\n");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new ClassPathResource(path).getFile()))) {
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
            return Dictionary.NO_KEY;
        }
        return Console.KEY_DELETE;
    }

    @Override
    public String search(String key) {

        String[] stringsSearch = fileReading().split("\n");

        for (String search : stringsSearch) {
            if (key.equals(search.split(DictionaryType.getSymbol())[0])) {
                return search;
            }
        }
    return Dictionary.NO_KEY;
    }
}
