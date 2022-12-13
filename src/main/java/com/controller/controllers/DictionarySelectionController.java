package com.controller.controllers;

import com.controller.logic.*;
import com.model.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("dictionary")
public class DictionarySelectionController {
    private String choiseSystem = "";

    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;

    private ChoiceOfAction dictionary;
    @Autowired
    private LanguageServices languageServices;
    @Autowired
    private ListOfDictionariesServices list;


    @GetMapping("choiseSystem")
    public String selectingASystem() {
        return "SelectingASysteam";
    }


    public String selectingASysteamMapAndFile(@RequestParam(name = "systemChoice") String systemChoice) {
        this.choiseSystem = systemChoice;
        return "SelectingADictionaryMapFile";

    }


    public String selectingASystemDataBase(@RequestParam(name = "systemChoice") String systemChoice, Model model)

    //,
    //   @RequestParam(name = "dictionaryFrom") String dictionaryFrom,
    //   @RequestParam(name = "dictionaryTo") String dictionaryTo)
    {
        if (systemChoice.equals("jdbc")) {
            model.addAttribute("array", list.language()); //заполняем списки
            String dictionaryFrom = "English"; //<- сюда строку, заполняющую список
            String dictionaryTo = "Russian";
            languageServices.getWord().setDictionaryIdFromWhichToTranslate(dictionaryFrom); // для чего это
            languageServices.getWord().setIsTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed(dictionaryTo);

            languageServices.getRow().setDictionaryIdFromWhichToTranslate(dictionaryFrom);
            languageServices.getRow().setIsTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed(dictionaryTo);
            dictionary = languageServices;
        }
        return "SelectingADictionaryDAO";
    }

    @PostMapping("choiseSystem")
    public String selectingASystem(@RequestParam(name = "systemChoice") String systemChoise, Model model) {
        if (systemChoise.equals("map") || systemChoise.equals("file")) {
            return selectingASysteamMapAndFile(systemChoise);
        }
        return selectingASystemDataBase(systemChoise, model);
    }


    @GetMapping("selection")
    public String dictionarySelection() {
        return "SelectAnAction";
    }


    @PostMapping("selection")
    public String dictionarySelection(@RequestParam(name = "choiceDictionary") Integer choiceDictionary

    ) {
        switch (choiseSystem) {
            case "map":
                dictionary = new Dictionary(dictionaryTypeMaps.get(choiceDictionary));
                break;
            case "file":
                dictionary = new FileOperation(dictionaryTypeMaps.get(choiceDictionary));
                break;
        }
        return "SelectAnAction";
    }

    @PostMapping("reading")
    public String fileReading(Model model) {
        model.addAttribute("pairs", dictionary.fileReadingList());
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