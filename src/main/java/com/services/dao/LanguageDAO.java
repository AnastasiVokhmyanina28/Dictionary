package com.services.dao;

import com.model.dto.Language;

import java.util.List;

public interface LanguageDAO {
    List<Language> languages();
    String getPattern(int id);
    Integer getIdLanguage(String language);
    String getNameLanguage(int id);


}


