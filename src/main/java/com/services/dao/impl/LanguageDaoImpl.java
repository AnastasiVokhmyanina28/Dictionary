package com.services.dao.impl;

import com.model.dto.Language;
import com.services.dao.LanguageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class LanguageDaoImpl implements LanguageDAO {
     private final JdbcTemplate jdbcTemplate;
    @Autowired
    public LanguageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //список всех словарей
    @Override
    public List<Language> languages(){
    return jdbcTemplate.query("select name from language", new BeanPropertyRowMapper<>(Language.class));
    }
    @Override
    // получения шаблонов
    public String getPattern(int id){
        return jdbcTemplate.queryForObject("select pattern from language where id = ?",new Object[]{id}, String.class);
    }
    @Override
    //id по названию словаря
    public Integer getIdLanguage(String name){
        return jdbcTemplate.queryForObject("select id from language where name = ?", new Object[]{name}, Integer.class);
    }
    @Override
    //название слолваря по id
    public String getNameLanguage(int id){
        return jdbcTemplate.queryForObject("select name from language id = ?", new Object[]{id}, String.class);
    }
}
