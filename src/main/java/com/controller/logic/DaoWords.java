package com.controller.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class DaoWords {
//замена id на слова
private final JdbcTemplate jdbcTemplate;
    private final String QUERY = "select tan.name  as key_word_id, word.name as value_word_id\n" +
            "from (select row.id, word.name, row.value_word_id\n" +
            "from row\n" +
            "join word on row.key_word_id = word.id) as tan\n" +
            "join word on tan.value_word_id = word.id";
    @Autowired
    public DaoWords(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TableWords> convertTheId(){
        return jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>());
    }


//    public List<TableWords> gybh() {
//        Statement statement = jdbcConfig.getStat();
//        ArrayList<TableWords> dictionary = new ArrayList<>();
//        try {
//            ResultSet resultSet = statement.executeQuery(QUERY);
//            while (resultSet.next()) {
//                dictionary.add(new TableWords(resultSet.getLong("id"), resultSet.getString("word"), resultSet.getString("translation")));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return dictionary;
//    }

    class TableWords {
        private Long id;
        private String key_word_id;
        private String value_word_id;

        public TableWords(Long id, String key_word_id, String value_word_id) {
            this.id = id;
            this.key_word_id = key_word_id;
            this.value_word_id = value_word_id;
        }
    }
}
