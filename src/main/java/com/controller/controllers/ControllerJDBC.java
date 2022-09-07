package com.controller.controllers;

import com.config.JdbcConfig;
import com.controller.logic.ChoiceOfAction;
import com.controller.logic.Dao;
import com.controller.logic.Dictionary;
import com.controller.logic.FileOperation;
import com.model.DictionaryType;
import com.model.dto.ListOfDictionaries;
import com.services.dao.DictionaryDao;
import com.services.dao.DictionaryServices;
import com.services.dao.DictionaryValuesDAO;
import com.services.dao.impl.DictionaryDAOImpl;
import com.services.dao.impl.DictionaryValuesDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("database")
public class ControllerJDBC {
    @Autowired
    private Map<Integer, DictionaryType> dictionaryTypeMaps;
    @Autowired
    private JdbcConfig jdbcConfig;
    @Autowired
    private DictionaryValuesDAO dictionaryValuesDAO;
    @Autowired
    private DictionaryDao dictionaryDao;
    private ChoiceOfAction choice;

//представление выбора системы
    @GetMapping("system")
    public String selectingASysteam(){
            return "SelectingASysteam";
    }


    //представление выбора словаря
    @GetMapping("dictionaries")
    public String chooseDictionary(){
        return "SelectingADictionaryDAO";
    }

    @PostMapping("dictionaries")
    public String chooseDictionary(@RequestParam(name = "systemChoice") String systemChoice,
                                   @RequestParam(name = "choiceDictionary") Integer choiceDictionary,
                                   Model model){
        switch (systemChoice) {
            case "map":
                choice = new Dictionary(dictionaryTypeMaps.get(choiceDictionary));
                break;
            case "jdbc":
                choice = new DictionaryServices(dictionaryValuesDAO, dictionaryDao, model.getAttribute("dictionaryFrom").toString(), model.getAttribute("dictionaryTo").toString());
                return "SelectingADictionaryDAO";
            case "file":
                choice = new FileOperation(dictionaryTypeMaps.get(choiceDictionary));
                break;
        }
        return "SelectAnAction";
    }
//выбор действия
    @GetMapping("selection")
    public String choosingAction(){
        return "SelectAnAction";
    }
    @PostMapping("selection")
    public String choosingAction(@RequestParam(name = "choiceDictionary") Integer choiceDictionary){
return "fggynhjgfyu";
    }

}