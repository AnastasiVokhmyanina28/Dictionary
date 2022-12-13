package com.services.dao.impl;

import com.services.dao.WordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.regex.Pattern;

@Component
public class WordDaoImpl implements WordDAO {
    private int dictionaryIdFromWhichToTranslate;
    private int isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed;
    @Autowired
    private LanguageDaoImpl languageDao;
    private final JdbcTemplate jdbcTemplate;

    public void setDictionaryIdFromWhichToTranslate(String dictionaryIdFromWhichToTranslate) {
        this.dictionaryIdFromWhichToTranslate = languageDao.getIdLanguage(dictionaryIdFromWhichToTranslate);
    }

    public void setIsTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed(String isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed) {
        this.isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed = languageDao.getIdLanguage(isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed);
    }


    @Autowired
    public WordDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * узнать id слова
     *
     * @param name
     * @return
     */
    @Override
    public Integer getIdWord(String name) {
        try {
            return jdbcTemplate.queryForObject("select id from word where name = ? ", new Object[]{name}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //слово по id
    @Override
    public String getWord(int id) {
        return jdbcTemplate.queryForObject("select name from word where id = ?", new Object[]{id}, String.class);
    }

    //добавление слова и id словаря, которому принадлежит данное слово(возвращает id только добавленной строки)
    @Override
    public Integer addWord(String name, int language_id) {
        return jdbcTemplate.queryForObject("insert into word(name, language_id) values(?, ?) returning id", new Object[]{name, language_id}, Integer.class);
    }

    // поиск слова
    @Override
    public String searchWord(String name) {
        return jdbcTemplate.queryForObject("select name from word where name = ?", new Object[]{name}, (String.class));
    }

    //какому словарю принадлежит слово
    @Override
    public Integer languageWord(String name) {
        return jdbcTemplate.queryForObject("select language_id from word where name = ?", new Object[]{name}, Integer.class);
    }
//----------------------------------------------------------------------------------------------

    //проверка ключа на шаблон
    private boolean keyCheck(String key) {
        String patternKey = languageDao.getPattern(dictionaryIdFromWhichToTranslate);
        return Pattern.matches(patternKey, key);
    }

    private boolean valueCheck(String value) {
        return Pattern.matches(languageDao.getPattern(isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed), value);
    }

    public boolean validPair(String key, String value) {


        return keyCheck(key) && valueCheck(value);
    }

    //вернет пару ключей
    public AbstractMap.SimpleEntry<Integer, Integer> add(String key, String value) {
        if (validPair(key, value)) {
            Integer idKey = getIdWord(key);
            if (idKey == null) {
                idKey = addWord(key, dictionaryIdFromWhichToTranslate);
            }

            Integer idValue = getIdWord(value);
            if (idValue == null) {
                idValue = addWord(value, isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed);
            }
            return new AbstractMap.SimpleEntry<>(idKey, idValue);
        }

        return null;
    }


}
