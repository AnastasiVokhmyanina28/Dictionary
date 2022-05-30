package com.controller;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.config.DictionaryType;
import com.utils.KeyNotFoundException;

public class FileOperation implements ChoiceOfAction {
    private final String path;
    private DictionaryType dictionaryType;

    public FileOperation(String path, DictionaryType dictionaryType)  {
        this.dictionaryType = dictionaryType;
        this.path = path;
    }

    @Override
    public String fileReading() {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    @Override
    public String addAnEntry(String key, String value) {
        if (keyCheck(key) && valueCheck(value)) {
            BufferedWriter bufferedWriter = null;
            try {
                FileWriter fileWriter = new FileWriter(path, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write( key + DictionaryType.getSymbol() + value + "\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (Exception e) {
                }
            }
            return Dictionary.ADD_KEY;
        } else {
            return Dictionary.SIMILARITY_TO_THE_PATTERN;
        }
    }

    @Override
    public boolean keyCheck(String key) {
        String patKey =  dictionaryType.getPatternKey();
        return Pattern.matches(patKey, key);
    }

    @Override
    public boolean valueCheck(String value) {
        String patValue =  dictionaryType.getPatternValue();
        return Pattern.matches(patValue, value);
    }

    @Override
    public void removeRecord(String key) throws KeyNotFoundException   {
        boolean deleteLine = false;
        String[] readLines = fileReading().split("\n");
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < readLines.length; i++) {
                if (!key.equals(readLines[i].split(DictionaryType.getSymbol())[0])) {
                    bufferedWriter.write(readLines[i] + "\n");
                }
                else {
                    deleteLine = true;
                }
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (Exception e) {
            }
        }
        if (!deleteLine){
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
