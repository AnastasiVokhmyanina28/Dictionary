package com.model;

import com.model.dto.Language;
import liquibase.pro.packaged.O;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public class Mapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
        return (rs.getString("key_word_id")+ " : " +rs.getString("value_word_id"));
    }

}
