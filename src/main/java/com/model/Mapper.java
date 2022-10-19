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
        return (rs.getString("name1")+ " : " +rs.getString("name2"));
    }


}
