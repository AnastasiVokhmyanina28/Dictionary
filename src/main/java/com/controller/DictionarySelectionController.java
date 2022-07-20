package com.controller;

import com.model.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("dictionary")
public class DictionarySelectionController {
    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;

    private ChoiceOfAction dictionary;

    @GetMapping("dictionaries")
    public String chooseDictionary(){
     return "SelectingADictionary";
    }

    @GetMapping("selection")
    public String dictionarySelection() {
        return "SelectAnAction";
    }
    @PostMapping("selection")
    public String dictionarySelection(@RequestParam(name = "choiceDictionary") Integer choiceDictionary,
                                      @RequestParam(name = "systemChoice") Integer systemChoice  ){

        if(systemChoice == 1){
            dictionary = new Dictionary(dictionaryTypeMaps.get(choiceDictionary));
        }
        else {
            dictionary = new FileOperation(dictionaryTypeMaps.get(choiceDictionary));
        }
        return "SelectAnAction";
    }

    @PostMapping("reading")
    public String fileReading(Model model) {
        model.addAttribute("pairs", new ArrayList<>(Arrays.asList(dictionary.fileReading().split("\n"))));
        return "Reading";
    }
    @GetMapping("delete")
    public String removeRecord() {
        return "Delete";
    }

    @PostMapping("delete")
    public String removeRecord(@RequestParam(value = "key") String key,
                               Model model) {
         model.addAttribute("remove", dictionary.removeRecord(key));
        return "Delete";
    }

    @GetMapping("add")
    public String addAnEntry() {
        return "AddEntryToDictionary";
    }

    @PostMapping("add")
    public String addAnEntry(@RequestParam(value = "key") String key,
                             @RequestParam(value = "value") String value,
                             Model model) {
        model.addAttribute("add", dictionary.addAnEntry(key, value));
        return "AddEntryToDictionary";
    }

    @GetMapping("search")
    public String search() {
        return "Search";
    }

    @PostMapping("search")
    public String search(@RequestParam(value = "key") String key,
                         Model model) {
        model.addAttribute("search", dictionary.search(key));
        return "Search";
    }
}
