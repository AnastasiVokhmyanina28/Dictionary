package com.model;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.core.io.ClassPathResource;

public class DictionaryStorage {

    private Map<String, String> localMap = new HashMap<>();
    private final String path;


    public DictionaryStorage(String path) {
        this.path = path;
    }

    public Map<String, String> getData() {
        Scanner scanner = null;
        try {
            File file = new ClassPathResource(path).getFile();
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                parseLine(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return localMap;
    }

    private Map<String, String> parseLine(String line) {
        String[] lineParts = line.split(DictionaryType.getSymbol());
        localMap.put(lineParts[0], lineParts[1]);
        return localMap;
    }


    public void saveData() {
        BufferedWriter bf = null;
        try {
            File file = new ClassPathResource(path).getFile();
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> entry : localMap.entrySet()) {
                bf.write(entry.getKey() + DictionaryType.getSymbol() + entry.getValue());
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