package com.model;

import com.model.dto.Language;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapperLanguageName implements RowMapper {

    public List<Language> mapRow(ResultSet set, int number) throws SQLException {
        List<Language> languageList = new ArrayList<>();
        while (set.next()){
            languageList.add(new Language(set.getLong("id"), set.getString("name"), set.getString("pattern")));
        }
        return languageList;
    }
}
