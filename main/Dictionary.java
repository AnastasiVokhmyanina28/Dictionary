package main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.regex.Pattern;

    public class Dictionary {
    public static final String PATTERN_TYPE_ONE = "[а-яА-Я]+";
    public static final String PATTERN_TYPE_TWO ="^[a-zA-Z]{4}$" ;
    public static final String PATTERN_TYPE_THREE = "^[0-9]{5}$" ;
    public static final String LANGUAGE_TYPE_ONE = "first";
    public static final String LANGUAGE_TYPE_TWO = "second";
    public static final String SPLIT_CHAR = ":";
    public static final String KEY_DELETE = "The key has been successfully deleted!";
    public static final String NO_KEY = "No key found!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    private Map<String, String> localMap;
    private HashMap<String,String>  keyConditions;
    private String path;
    private String languageType;
    private String valueType;

    public Dictionary(String path, String languageType){
        this.path = path;
        this.valueType = PATTERN_TYPE_ONE;
        this.languageType = languageType;
       this.keyConditions = new HashMap<String, String>(){{
           put(LANGUAGE_TYPE_ONE, PATTERN_TYPE_TWO );
           put(LANGUAGE_TYPE_TWO,PATTERN_TYPE_THREE );
       }};
        getData();
    }

    public void getData() {
        try{
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            localMap = new HashMap<String, String>();
            while (line != null){
                String[] del = line.split(SPLIT_CHAR);
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
         }

        public void saveData() {
        File file = new File(path);
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String,String> entry : localMap.entrySet()){
                bf.write(entry.getKey() + SPLIT_CHAR + entry.getValue());
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

        public void removeRecord(String key) {
        if (localMap.containsKey(key)){
            localMap.remove(key);
            System.out.println(KEY_DELETE);
        }
        else {
            System.out.println(NO_KEY);
        }
        saveData();
        }

        public void pairReading() {
        for (Map.Entry<String, String> entry : localMap.entrySet()) {
            System.out.println(entry.getKey() + SPLIT_CHAR + entry.getValue());
        }
        }

           public  void  recordSearch(String key){
            String search = localMap.get(key);
            if(search != null){
            System.out.println(key + SPLIT_CHAR + search);
            }
            else {
            System.out.println(KEY_DOES_NOT_EXIST);
             }
           }

           public void  addAnEntry(String key, String value){
               boolean matches = Pattern.matches(this.valueType, value);
               if (keyCheck(key) && matches) {
                 localMap.put(key, value);
                 System.out.println(ADD_KEY);
                 }
               else {
                System.out.println(SIMILARITY_TO_THE_PATTERN);
                  }
                saveData();
            }

            private boolean keyCheck(String key) {
                String value = keyConditions.get(this.languageType);
                return Pattern.matches(value, key);
           }
        }
















