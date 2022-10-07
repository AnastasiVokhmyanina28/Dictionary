package com.services.dao;

import com.model.dto.Row;

import java.util.List;

public interface RowDAO {
     List<String> convertTheId();
    List<Row> getRows();
    Row search(int key_word_id);
    void delete(int id);
    void add(int key_word_id, int value_word_id);
    List<String> lineСheck(String name);

}
