package com.services.dao;

import com.controller.logic.ChoiceOfAction;
import com.controller.logic.Dictionary;
import com.model.dto.MeaningsLyingInTheDictionary;

import java.util.ArrayList;
import java.util.List;


public class DictionaryServices implements ChoiceOfAction {
    public DictionaryServices(DictionaryValuesDAO dictionaryValuesDAO, DictionaryDao dictionaryDAO, String dictionaryFrom, String dictionaryTo) {
        this.dictionaryValuesDAO = dictionaryValuesDAO;
        this.dictionaryDAO = dictionaryDAO;
        this.dictionaryFrom = dictionaryFrom;
        this.dictionaryTo = dictionaryTo;
    }

    private DictionaryValuesDAO dictionaryValuesDAO;
    private DictionaryDao dictionaryDAO;
    private String dictionaryFrom;
    private String dictionaryTo;
    @Override
    public String removeRecord (String key){
        return dictionaryValuesDAO.removeRecord(key);
    }

    public String addAnEntry(String word, String translation){
       Integer wordId =  dictionaryDAO.getIdDictionary(dictionaryFrom);
        Integer translationId = dictionaryDAO.getIdDictionary(dictionaryFrom);
        dictionaryValuesDAO.addingALineToDictionaryValues(word,  translation, dictionaryFrom,  wordId,  translationId);
        return Dictionary.ADD_KEY;
    }

    @Override
    public String fileReading() {
        StringBuilder list = new StringBuilder();
        for(MeaningsLyingInTheDictionary line: dictionaryValuesDAO.getMeaningsLyingInTheDictionaries()){
            list.append(line.toString()).append("\n");
        }
        return list.toString();
    }

    @Override
    public String search(String key){
        return  dictionaryValuesDAO.getLineOfDictionary(key).toString();
    }

    public List<String> fileReadingList(){
        ArrayList<String> list = new ArrayList<>();
        for(MeaningsLyingInTheDictionary line: dictionaryValuesDAO.getMeaningsLyingInTheDictionaries()){
            list.add(line.toString());
        }
        return list;
    }
}
