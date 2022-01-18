package main;

import java.util.HashMap;
import java.util.Scanner;

public class Console {
    public static final String LANGUAGE_PATH_ONE = "main/Dictionary.txt" ;
    public static final String LANGUAGE_PATH_TWO = "main/digitalDictionary.txt";
    public static final String SPLIT_CHAR = ":";
    public static final String NO_COMMAND = "There is no such command. Try again!";
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
    private HashMap<String, String> allDicts;
    private boolean isRunningConsole = false;

    public Console(){ 
        scanner = new Scanner(System.in);
        allDicts = new HashMap<String, String>(){{
            put(Dictionary.LANGUAGE_TYPE_ONE, LANGUAGE_PATH_ONE ) ;
            put(Dictionary.LANGUAGE_TYPE_TWO, LANGUAGE_PATH_TWO);
        }};
    }

    public void start() throws InterruptedException {

        int dictionarySelection = this.choiceDictionary();
        this.createDictionary(dictionarySelection);
        while (!isRunningConsole) {
            int choiceOfActions = this.choiceOfAction();
            this.choice(choiceOfActions);
        }
    }

    public int choiceDictionary() {
        System.out.println(SELECT_DICTIONARY);
        System.out.println(DICTIONARY_TYPE_ONE);
        System.out.println(DICTIONARY_TYPE_TWO);
        System.out.println(SELECT + SPLIT_CHAR);
        return scanner.nextInt();
    }

    private void createDictionary(int chosenAction) throws InterruptedException{
        switch (chosenAction) {
            case 1:
                this.dictionary = new Dictionary(allDicts.get(Dictionary.LANGUAGE_TYPE_ONE), Dictionary.LANGUAGE_TYPE_ONE);
                break;
            case 2:
                this.dictionary = new Dictionary(allDicts.get(Dictionary.LANGUAGE_TYPE_TWO), Dictionary.LANGUAGE_TYPE_TWO);
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

    private void choice(int chosenAction) throws InterruptedException {

        switch (chosenAction) {
            case 1:
                dictionary.pairReading();
                break;
            case 2:
                System.out.println(ENTER_KEY);
                String userKey = scanner.next();
                dictionary.removeRecord(userKey);
                break;
            case 3:
                System.out.println(ENTER_KEY);
                String usKey = scanner.next();
                dictionary.recordSearch(usKey);
                break;
            case 4:
                System.out.println(ENTER_KEY);
                String key = scanner.next();
                System.out.println(ENTER_VALUE);
                String value = scanner.next();
                dictionary.addAnEntry(key, value);
                break;
            case 5:
                dictionary.saveData();
                int nextDictionary = choiceDictionary();
                createDictionary(nextDictionary);
                break;
            case 6:
                dictionary.saveData();
                this.isRunningConsole = true;
                break;
            default:
                System.out.println(NO_COMMAND);
                break;
        }
    }
}