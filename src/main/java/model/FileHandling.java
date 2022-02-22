package model;
import java.io.*;
import java.util.Map;
import view.Console;

public class FileHandling {
    private Map<String, String> localMap;
    private String path;

    public FileHandling(String path, Map<String, String> localMap) {
        this.localMap = localMap;
        this.path = path;
    }

    public Map<String, String> getData() {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] del = line.split(Console.SPLIT_CHAR);
                localMap.put(del[0], del[1]);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localMap;
    }


}