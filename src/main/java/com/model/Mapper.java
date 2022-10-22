package com.model;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 */


public class Mapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
        return (rs.getString("key_word_id")+ " : " +rs.getString("value_word_id"));
    }


}
