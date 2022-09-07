package com.services.dao;

import com.model.dto.MeaningsLyingInTheDictionary;

import java.util.List;

public interface DictionaryValuesDAO {
    List<MeaningsLyingInTheDictionary> getMeaningsLyingInTheDictionaries();
    MeaningsLyingInTheDictionary getLineOfDictionary(String word);
    String removeRecord(String words);

    boolean valid(String word,String translation, String dictionary);
    boolean checkStringsToSeeIfTheyMatch(String word, String translate);
    void addingALineToDictionaryValues(String word, String translation, String dictionary, int wordId, int translationId);

}
