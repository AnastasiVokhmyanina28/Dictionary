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
    ChoiceOfAction dictionary;

    @GetMapping("dictionaries")
    public String chooseDictionary(){
     return "SelectingADictionary";
    }

    @GetMapping("selection")
    public String dictionarySelection(@RequestParam(name = "choise") Integer choise){
    dictionary = new FileOperation(dictionaryTypeMaps.get(choise));
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
    public String removeRecord(String key, Model model) {
         model.addAttribute("remove", dictionary.removeRecord(key));
        return "Delete";
    }

    @GetMapping("add")
    public String addAnEntry() {
        return "AddEntryToDictionary";
    }

    @PostMapping("adding")
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
