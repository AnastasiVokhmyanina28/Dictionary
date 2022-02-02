package src.main.javaFiles;

import java.util.Map;
import java.util.Scanner;

public class Console {

    public static final String SPLIT_CHAR = ":";
    public static final String NO_COMMAND = "There is no such command. Try again!";
    public static final String KEY_DELETE = "The key has been successfully deleted!";
    public static final String ENTER_KEY = "Please enter the key";
    public static final String ENTER_VALUE = "Please enter the value";
    public static final String SELECT_DICTIONARY= "Select dictionary";
    public static final String SELECT = "Your choice";
    public static final String SELECT_THE_COMMAND = "Select the action you want to perform with this dictionary ";
    public static final String DICTIONARY_TYPE_ONE = "\t1 English-Russian dictionary";
    public static final String DICTIONARY_TYPE_TWO = "\t2 Digital dictionary";
    public static final String READ = "\t1 Reading a list of pairs";
    public static final String DELETE = "\t2 Deleting an entry";
    public static final String SEARCH = "\t3 Record search";
    public static final String ADD_ENTRY = "\t4 Adding an entry";
    public static final String CHANGE_THE_DICTIONARY = "\t5 Change the dictionary";
    public static final String EXIT = "\t6 Logout";

    private Scanner scanner;
    private Dictionary dictionary;
    //private FileHandling fileHandling;
    private Map<String, Dictionary> dictionaries;
    private boolean isRunningConsole = false;



    public Console(Map<String, Dictionary> dictionaries){

        scanner = new Scanner(System.in);
        this.dictionaries = dictionaries;
    }

    public void start()  {
        int dictionarySelection = this.menuChoiceDictionary();
        this.choiseDictionary(dictionarySelection);
            while (!isRunningConsole) {
                int choiceOfActions = this.choiceOfAction();
                this.choice(choiceOfActions);
        }
        }



    public int menuChoiceDictionary() {
        System.out.println(SELECT_DICTIONARY);
        System.out.println(DICTIONARY_TYPE_ONE);
        System.out.println(DICTIONARY_TYPE_TWO);
        System.out.println(SELECT + SPLIT_CHAR);
        return scanner.nextInt();
    }

    private void choiseDictionary(int chosenAction) {
        switch (chosenAction) {
            case 1:
                this.dictionary =  dictionaries.get(DictionaryType.LANGUAGE_TYPE_ONE);
            //    this.fileHandling = new FileHandling(DictionaryType.LANGUAGE_PATH_ONE, dictionary.getLocalMap());
                break;
            case 2:
                this.dictionary =  dictionaries.get(DictionaryType.LANGUAGE_TYPE_TWO);
            //    this.fileHandling = new FileHandling(DictionaryType.LANGUAGE_PATH_TWO, dictionary.getLocalMap());
                break;
            default:
                System.out.println(NO_COMMAND);
                start();


        }
    }

    public int choiceOfAction(){
        System.out.println(SELECT_THE_COMMAND + SPLIT_CHAR);
        System.out.println(READ);
        System.out.println(DELETE);
        System.out.println(SEARCH);
        System.out.println(ADD_ENTRY);
        System.out.println(CHANGE_THE_DICTIONARY);
        System.out.println(EXIT);
        System.out.println(SELECT + SPLIT_CHAR);
        return scanner.nextInt();
    }

    private void choice(int chosenAction){

        switch (chosenAction)  {
            case 1:
                for (String key : dictionary.getLocalMap().keySet()) {
                    System.out.println(key + SPLIT_CHAR + dictionary.getLocalMap().get(key));
                }

                break;
            case 2:
                System.out.println(ENTER_KEY);
                String userKey = scanner.next();
                try {
                    dictionary.removeRecord(userKey);
                    System.out.println(KEY_DELETE);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }

                break;
            case 3:
                System.out.println(ENTER_KEY);
                String usKey = scanner.next();
                System.out.println( dictionary.recordSearch(usKey));
                break;
            case 4:
                System.out.println(ENTER_KEY);
                String key = scanner.next();
                System.out.println(ENTER_VALUE);
                String value = scanner.next();
                System.out.println(dictionary.addAnEntry(key, value));
                break;
            case 5:
               dictionary.getFileHandling().saveData();
                int nextDictionary = menuChoiceDictionary();
                choiseDictionary(nextDictionary);
                break;
            case 6:
               dictionary.getFileHandling().saveData();
                this.isRunningConsole = true;
                break;
            default:
                System.out.println(NO_COMMAND);
                break;
        }
    }
}