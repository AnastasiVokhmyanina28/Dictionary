package com.controller;

import com.model.DictionaryType;
import com.view.Console;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "dictionary", description = "REST controller выбора действий со словарем")
@RequestMapping("/api/v1/dictionary")
public class ControllerRest {

    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;

    public ChoiceOfAction dictionaryType(Integer choiceDictionary, String systemChoice) {

        if (systemChoice.equals("map")) {
            return new Dictionary(dictionaryTypeMaps.get(choiceDictionary));
        } else {
            return new FileOperation(dictionaryTypeMaps.get(choiceDictionary));
        }

    }

    @Operation(summary = "reading", description = "Allows you to read data from the dictionary")
    @GetMapping("read")
    @ResponseBody
    public ResponseEntity<?> fileReading(@RequestParam(value = "choiceDictionary") Integer choiceDictionary,
                                         @RequestParam(value = "systemChoice") String systemChoice) {
        ChoiceOfAction dictionary = dictionaryType(choiceDictionary, systemChoice);
        List<String> ReadMethodCall = dictionary.fileReadingList();
        return new ResponseEntity<>(ReadMethodCall, HttpStatus.OK);
    }

    @Operation(summary = "remove", description = "Allows you to delete data from the dictionary")
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity<?> removeRecord(@RequestParam(value = "choiceDictionary") Integer choiceDictionary,
                                          @RequestParam(value = "systemChoice") String systemChoice,
                                          @RequestParam(value = "key") String key) {
        ChoiceOfAction dictionary = dictionaryType(choiceDictionary, systemChoice);
        String delete = dictionary.removeRecord(key);
            if (delete.equals(Console.KEY_DELETE)){
                return new ResponseEntity<>(delete, HttpStatus.OK);
    } else {
                return  new ResponseEntity<>(delete, HttpStatus.NOT_FOUND);
            }
    }

    @Operation(summary = "search", description = "Allows you to search data from the dictionary")
    @GetMapping("search")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam(value = "choiceDictionary") Integer choiceDictionary,
                                    @RequestParam(value = "systemChoice") String systemChoice,
                                    @RequestParam(value = "key") String key) {
        ChoiceOfAction dictionary = dictionaryType(choiceDictionary, systemChoice);
        String search = dictionary.search(key);
        if (search.equals(Dictionary.NO_KEY)) {
            return new ResponseEntity<>(search, HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>(search, HttpStatus.OK);
        }
    }

    @Operation(summary = "add", description = "Allows you to add data to the dictionary")
    @PutMapping("add")
    @ResponseBody
    public ResponseEntity<?> add(@RequestParam(value = "choiceDictionary") Integer choiceDictionary,
                                 @RequestParam(value = "systemChoice") String systemChoice,
                                 @RequestParam(value = "key") String key,
                                 @RequestParam(value = "value") String value) {
        ChoiceOfAction dictionary = dictionaryType(choiceDictionary, systemChoice);
        String add = dictionary.addAnEntry(key, value);
        if (add.equals(Dictionary.ADD_KEY)){
        return new ResponseEntity<>(add, HttpStatus.OK);
    } else {
            return new ResponseEntity<>(add, HttpStatus.NOT_FOUND);
        }
    }
}