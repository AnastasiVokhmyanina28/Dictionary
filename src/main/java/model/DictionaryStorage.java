package model;
import java.io.*;
import java.util.Scanner;
import java.util.Map;
import config.DictionaryType;
import view.Console;

public class DictionaryStorage {
    private Map<String, String> localMap;
    private String path;

    public DictionaryStorage(String path, Map<String, String> localMap) {
        this.localMap = localMap;
        this.path = path;
    }

     public Map<String, String> getData(){

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                parseLine(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return localMap;
    }
    
    private  Map<String, String>  parseLine (String line) {

        String[] lineParts = line.split(DictionaryType.getSymbol());
        localMap.put(lineParts[0], lineParts[1]);
        return localMap;
    }
    public void saveData() {
        File file = new File(path);
        BufferedWriter bf = null;
        try {
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