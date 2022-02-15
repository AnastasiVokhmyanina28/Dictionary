import java.util.HashMap;
import java.util.Map;
import config.DictionaryType;
import controller.Dictionary;
import model.FileHandling;
import view.Console;

public class Dict {

    public static void main(String[] args) {
        Map<String, Dictionary> dictionaries;
        dictionaries = new HashMap<>();
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            dictionaries.put(dictionaryType.getNUMBER(), creationOne(dictionaryType));
        }

        Console dictionaryConsole = new Console(dictionaries);
        dictionaryConsole.start();
    }

    public static Dictionary creationOne(DictionaryType dictionaryType) {
        Map<String, String> localMap = new HashMap<>();
        return new Dictionary(dictionaryType.getDICTIONARY_PATH(), dictionaryType.getNUMBER(), new FileHandling(dictionaryType.getDICTIONARY_PATH(), localMap).getData());
    }
}