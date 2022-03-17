package view;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import controller.Dictionary;
import config.DictionaryType;
import controller.FileOperation;
import model.DictionaryStorage;
 

public class Console {

    public static final String NO_COMMAND = "There is no such command. Try again!";
    public static final String KEY_DELETE = "The key has been successfully deleted!";
    public static final String ENTER_KEY = "Please enter the key";
    public static final String ENTER_VALUE = "Please enter the value";
    public static final String SELECT_DICTIONARY = "Select dictionary";
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
    public static final String SYSTEM = " Choose a system";
    public static final String MAP_DICTIONARY = "\t1 Work with map";
    public static final String FILE_DICTIONARY = "\t2 Work with file";
    private Scanner scanner;
    private Dictionary dictionary;
    private Map<Integer, Dictionary> mapDictionaries;
    private Map<Integer, FileOperation> fileDictionaries;
    private FileOperation fileOperation;
    private boolean isRunningConsole = false;


    public Console() {
        scanner = new Scanner(System.in, "windows-1251");

    }

    public int startp(){
        System.out.println(SYSTEM);
        System.out.println(MAP_DICTIONARY);
        System.out.println(FILE_DICTIONARY);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }


    private void startS(int choice){

        switch (choice){
            case 1:
                Map<Integer, Dictionary> dictionaries = new HashMap<>();
                for (DictionaryType dictionaryType : DictionaryType.values()) {
                    dictionaries.put(dictionaryType.getNumber(), creation(dictionaryType));
                }
                 this.mapDictionaries = dictionaries;
                break;

            case 2:
                Map<Integer, FileOperation> fileDictionaries = new HashMap<>();
                for (DictionaryType dictionaryType : DictionaryType.values()) {
                    fileDictionaries.put(dictionaryType.getNumber(), creation1(dictionaryType));
                }
                this.fileDictionaries = fileDictionaries;
                break;
            default:
                System.out.println(NO_COMMAND);
                break;

        }
          }




    public static Dictionary creation(DictionaryType dictionaryType) {
        Map<String, String> localMap = new HashMap<>();
        String dictionaryPath = dictionaryType.getDictionaryPath();
        var data = new DictionaryStorage(dictionaryType.getDictionaryPath(), localMap).getData();
        return new Dictionary(dictionaryPath, dictionaryType, data);
    }


    public static FileOperation creation1(DictionaryType dictionaryType) {
        String dictionaryPath = dictionaryType.getDictionaryPath();
        return new FileOperation(dictionaryPath, dictionaryType);
    }





    public void start() {
        int choice = this.startp();
        this.startS(choice);

        int dictionarySelection = this.menuChoiceDictionary();
        if (mapDictionaries != null) {
            chooseDictionary(dictionarySelection);
            while (!isRunningConsole) {
                int choiceOfActions = this.choiceOfAction();
                this.choice(choiceOfActions);
            }
        } else {
            systemSelection(dictionarySelection);
            while (!isRunningConsole) {
                int choiceOfActions = this.choiceOfAction();
                this.fileOperation(choiceOfActions);

            }

        }
    }

    public int menuChoiceDictionary() {
        System.out.println(SELECT_DICTIONARY);
        System.out.println(DICTIONARY_TYPE_ONE);
        System.out.println(DICTIONARY_TYPE_TWO);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }

    public void  systemSelection(int chosenAction ) {
        this.fileOperation = fileDictionaries.get(chosenAction);
    }

    private void chooseDictionary(int chosenAction) {
        this.dictionary = mapDictionaries.get(chosenAction);
    }



    public int choiceOfAction() {
        System.out.println(SELECT_THE_COMMAND + DictionaryType.getSymbol());
        System.out.println(READ);
        System.out.println(DELETE);
        System.out.println(SEARCH);
        System.out.println(ADD_ENTRY);
        System.out.println(CHANGE_THE_DICTIONARY);
        System.out.println(EXIT);
        System.out.println(SELECT + DictionaryType.getSymbol());
        return scanner.nextInt();
    }

    private void choice(int chosenAction) {

        switch (chosenAction) {
            case 1:
                for (String key : dictionary.getLocalMap().keySet()) {
                    System.out.println(key + DictionaryType.getSymbol() + dictionary.getLocalMap().get(key));
                }
                break;
            case 2:
                System.out.println(ENTER_KEY);
                String userKey = scanner.next();
                try {
                    dictionary.removeRecord(userKey);
                    System.out.println(KEY_DELETE);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                dictionary.saveData();
                break;
            case 3:
                System.out.println(ENTER_KEY);
                String usKey = scanner.next();
                System.out.println(dictionary.recordSearch(usKey));
                break;
            case 4:
                System.out.println(ENTER_KEY);
                String key = scanner.next();
                System.out.println(ENTER_VALUE);
                String value = scanner.next();
                System.out.println(dictionary.addAnEntry(key, value));
                dictionary.saveData();
                break;
            case 5:
                dictionary.saveData();
                int nextDictionary = menuChoiceDictionary();
                chooseDictionary(nextDictionary);
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

    private void fileOperation(int chosenAction){

        switch (chosenAction){
            case 1:
                System.out.println(fileOperation.fileReading());
                break;
            case 2:
                System.out.println(ENTER_KEY);
                String userKey = scanner.next();
                fileOperation.fileRemove(userKey);
                break;
            case 3:
                System.out.println(ENTER_KEY);
                String usKey = scanner.next();
                System.out.println(fileOperation.search(usKey));
                break;
            case 4:
                System.out.println(ENTER_KEY);
                String key = scanner.next();
                System.out.println(ENTER_VALUE);
                String value = scanner.next();
               System.out.println(fileOperation.fileAdd(key, value));
                break;
            case 5:
                int nextDictionary = menuChoiceDictionary();
                systemSelection(nextDictionary);
                break;
            case 6:
                this.isRunningConsole = true;
                break;
            default:
                System.out.println(NO_COMMAND);
        }

    }
}