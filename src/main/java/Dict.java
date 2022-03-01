import java.util.HashMap;
import java.util.Map;
import config.DictionaryType;
import controller.Dictionary;
import model.DictionaryStorage;
import view.Console;

public class Dict {

    public static void main(String[] args) {
        Map<Integer, Dictionary> dictionaries = new HashMap<>();
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            dictionaries.put(dictionaryType.getNumber(), creation(dictionaryType));
        }

        Console dictionaryConsole = new Console(dictionaries);
        dictionaryConsole.start();
    }

    public static Dictionary creation(DictionaryType dictionaryType) {
        Map<String, String> localMap = new HashMap<>();
        String  dictionaryPath  = dictionaryType.getDictionaryPath();
        var data =  new DictionaryStorage(dictionaryType.getDictionaryPath(), localMap).getData();
        return new Dictionary(dictionaryPath, dictionaryType,data);
    }
}