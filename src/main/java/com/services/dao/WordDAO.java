package com.services.dao;

import com.model.dto.Word;

public interface WordDAO {

    Integer getIdWord(String word);
    String getWord(int id);
    Integer addWord(String name, int language_id);
    String searchWord(String word);
    Integer languageWord (String name);
}
