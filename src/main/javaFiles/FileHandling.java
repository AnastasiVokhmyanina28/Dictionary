package src.main.javaFiles;

import java.io.*;
import java.util.Map;

public class FileHandling {
    private Map<String, String> localMap;
    private String path;


    public FileHandling(String path, Map<String, String> localMap){
        this.localMap = localMap;
        this.path = path;
    }

    public Map<String, String> getData() {
        try{
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null){
                String[] del = line.split(Console.SPLIT_CHAR);
                localMap.put(del[0], del[1]);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return localMap;
    }

    public void saveData() {
        File file = new File(path);
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String,String> entry : localMap.entrySet()){
                bf.write(entry.getKey() + Console.SPLIT_CHAR + entry.getValue());
                bf.newLine();
            }
            bf.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                bf.close();
            }
            catch (Exception e){

            }
        }
    }
}
