package com.services.dao.impl;

import com.model.dto.Row;
import com.services.dao.RowDAO;
import liquibase.pro.packaged.O;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RowDaoImpl implements RowDAO {
    private final WordDaoImpl word;
    private final JdbcTemplate jdbcTemplate;
    private final String NOT_KEY = "THIS KEY WAS NOT FOUND";
    private final String DELETE_ROW = "The line is successfully deleted!";
    private int dictionaryIdFromWhichToTranslate;
    private int isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed;

    public void setDictionaryIdFromWhichToTranslate(int dictionaryIdFromWhichToTranslate) {
        this.dictionaryIdFromWhichToTranslate = dictionaryIdFromWhichToTranslate;
    }

    public void setIsTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed(int isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed) {
        this.isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed = isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed;
    }


    private final String QUERY_TO_REPLACE_THE_ID_WITH_THE_WORD = "select tan.name  as key_word_id, word.name as value_word_id\n" +
            "from (select row.id, word.name, row.value_word_id\n" +
            "from row\n" +
            "join word on row.key_word_id = word.id) as tan\n" +
            "join word on tan.value_word_id = word.id";
    private final String THE_LIST_OF_PAIRS = "select name1, name as name2\n" +
            "from(\n" +
            "    select value_word_id, name as name1, language_id as since_language_id\n" +
            "from row\n" +
            "join word on row.key_word_id = word.id\n" +
            ")as sin\n" +
            "join word on sin.value_word_id = word.id\n" +
            "where since_language_id  = ? and language_id = ?";

    @Autowired
    public RowDaoImpl(JdbcTemplate jdbcTemplate, WordDaoImpl word) {
        this.jdbcTemplate = jdbcTemplate;
        this.word = word;
    }


    //вывод пар конкретного словаря
    public List<String> fileReadingList() {
        return jdbcTemplate.query(THE_LIST_OF_PAIRS, new Object[]{dictionaryIdFromWhichToTranslate, isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed}, new BeanPropertyRowMapper<>(String.class));
    }

    //заменить id  на слова
    @Override
    public List<String> convertTheId() {
        return jdbcTemplate.query(QUERY_TO_REPLACE_THE_ID_WITH_THE_WORD, new BeanPropertyRowMapper<>(String.class));
    }

    //пары id
    @Override
    public List<Row> getRows() {
        return jdbcTemplate.query("select key_word_id, value_word_id from row", new BeanPropertyRowMapper<>(Row.class));
    }

    //поиск по ключу
    @Override
    public Row search(int key_word_id) {
        return jdbcTemplate.queryForObject("select key_word_id, values_word_id from row where key_word_id = ?", new Object[]{key_word_id}, new BeanPropertyRowMapper<>(Row.class));
    }

    //удаление пар
    @Override
    public void delete(int key_word_id) {
        jdbcTemplate.update("delete from row where  key_word_id = ?", key_word_id);
    }

    //добавление пар
    @Override
    public void add(int key_word_id, int value_word_id) {
        jdbcTemplate.update("insert into row(key_word_id, value_word_id) values (?, ?)", new Object[]{key_word_id, value_word_id});
    }


    public List<String> lineСheck(String name) {
        if (search(word.getIdWord(name)) != null) {
            return convertTheId();
        } else return null;
    }


    public String removeRecord(String name) {
        Integer idWord = word.getIdWord(name);
        if (idWord != null) {
            delete(idWord);
            deleteWord(idWord);
            return DELETE_ROW;
        } else {
            return NOT_KEY;
        }

    }

    //проверка сколько строк вернется, у которых есть значение
    private Integer rowСount(int value_word_id) {
        return jdbcTemplate.queryForObject("select count(value_word_id) from row where value_word_id = ?", new Object[]{value_word_id}, new BeanPropertyRowMapper<>(Integer.class));
    }


    //проверка есть ли id в списке значений
    private boolean checkForTheExistenceOfTheIdentifier(int value_word_id) {
        if (rowСount(value_word_id) > 0) {
            return true;
        } else return false;
    }

    public void deleteRowWord(int value_word_id) {
        jdbcTemplate.update("delete from word where value_word_id = ?", value_word_id);
    }

    private void deleteWord(int value_word_id) {
        if (checkForTheExistenceOfTheIdentifier(value_word_id) == false) {
            deleteRowWord(value_word_id);
        }
    }

    //проверка есть ли id в столбце ключей
    public Integer searchKey(int key_word_id) {
        return jdbcTemplate.queryForObject("select count (key_word_id) from row where key_word_id = ?", new Object[]{key_word_id}, new BeanPropertyRowMapper<>(Integer.class));
    }

    //проверка есть ли id в столбце значений
    public Integer searchValue(int value_word_id) {
        return jdbcTemplate.queryForObject("select count(value_word_id) from row where value_word_id = ?", new Object[]{value_word_id}, new BeanPropertyRowMapper<>(Integer.class));
    }


    //есть ли уже такая пара
    public boolean isThereACoupleOf(int key_word_id, int value_word_id) {
        if (searchKey(key_word_id) == 0 && searchValue(value_word_id) == 0) {
            add(key_word_id, value_word_id);
            return true; //значит можем добавлять

        } else return false;
    }


}
