package com.services.dao.impl;

import com.model.Mapper;
import com.model.dto.Row;
import com.services.dao.RowDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * работает с таблицей Row
 */
@Component
public class RowDaoImpl implements RowDAO {
    private final WordDaoImpl word;
    @Autowired
    private LanguageDaoImpl languageDao;
    private final JdbcTemplate jdbcTemplate;
    private final String NOT_KEY = "THIS KEY WAS NOT FOUND";
    private final String DELETE_ROW = "The line is successfully deleted!";
    private final String str = null;
    private int dictionaryIdFromWhichToTranslate;
    private int isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed;

    public void setDictionaryIdFromWhichToTranslate(String dictionaryIdFromWhichToTranslate) {
        this.dictionaryIdFromWhichToTranslate = languageDao.getIdLanguage(dictionaryIdFromWhichToTranslate);
    }

    public void setIsTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed(String isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed) {
        this.isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed = languageDao.getIdLanguage(isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed);
    }


    private final String QUERY_TO_REPLACE_THE_ID_WITH_THE_WORD = "select tan.name  as key_word_id, word.name as value_word_id\n" +
            "from (select row.id, word.name, row.value_word_id\n" +
            "from row\n" +
            "join word on row.key_word_id = word.id) as tan\n" +
            "join word on tan.value_word_id = word.id";
    private final String THE_LIST_OF_PAIRS = "select key_word_id, name as value_word_id\n" +
            "            from(\n" +
            "                select value_word_id, name as key_word_id, language_id as since_language_id\n" +
            "            from row\n" +
            "            join word on row.key_word_id = word.id\n" +
            "            )as sin\n" +
            "            join word on sin.value_word_id = word.id\n" +
            "            where since_language_id  = ? and language_id = ?";

    private final String QUERY_SEARCH = "select search.name as key_word_id, word.name as value_word_id\n" +
            "from (select row.id, word.name,value_word_id\n" +
            "from row\n" +
            "join word on word.id = row.key_word_id\n" +
            "where key_word_id = ?) as search\n" +
            "join word on word.id = search.value_word_id";

    @Autowired
    public RowDaoImpl(JdbcTemplate jdbcTemplate, WordDaoImpl word) {
        this.jdbcTemplate = jdbcTemplate;
        this.word = word;
    }

    /**
     * вывод пар, выбранного словаря, замена id  на слова
     *
     * @return
     */
    public List<String> fileReadingList() {
        return jdbcTemplate.query(THE_LIST_OF_PAIRS, new Object[]{dictionaryIdFromWhichToTranslate, isTheIdentifierOfTheDictionaryIntoWhichTheTranslatioIsPerformed}, new Mapper());
    }

    /**
     * замена id на слова(вся строка)
     *
     * @return
     */
    @Override
    public List<String> convertTheId() {
        return jdbcTemplate.query(QUERY_TO_REPLACE_THE_ID_WITH_THE_WORD, new BeanPropertyRowMapper<>(String.class));
    }

    /**
     * вывод пар id
     *
     * @return
     */
    @Override
    public List<Row> getRows() {
        return jdbcTemplate.query("select key_word_id, value_word_id from row", new BeanPropertyRowMapper<>(Row.class));
    }

    /**
     * поиск строки по ключу
     *
     * @param key_word_id - ключ
     * @return
     */
    @Override
    public List<String> search(int key_word_id) {
        return jdbcTemplate.query(QUERY_SEARCH, new Object[]{key_word_id}, new Mapper());
    }

    /**
     * удаление строки по ключу
     *
     * @param key_word_id - ключ
     */
    @Override
    public void delete(int key_word_id) {
        jdbcTemplate.update("delete from row where  key_word_id = ?", key_word_id);
    }

    /**
     * добавление пар
     *
     * @param key_word_id   - ключ
     * @param value_word_id - значение
     */
    @Override
    public void add(int key_word_id, int value_word_id) {
        jdbcTemplate.update("insert into row(key_word_id, value_word_id) values (?, ?)", new Object[]{key_word_id, value_word_id}, new Mapper());
    }

    /**
     * проверяет по id слова есть ли строка с таким ключом, если есть - то выводит ее и заменяет id на слова
     *
     * @param name - слово, у которого узнает id
     * @return
     */
    // ПЕРЕДЕЛАТЬ НА ДРУГОЙ МЕТОД
    public List<String> lineСheck(String name) {
        Integer idWord = word.getIdWord(name); // узнали id слова
        if (idWord.equals(null)) {
            return null;
        }
        return search(idWord);
    }

    /**
     * узнаем по id слова есть ли такая строка, если есть - то удаляем, после проверяем есть ли такое слово еще в row, если нет - то удаляем слово из таблицы word
     *
     * @param name
     * @return
     */
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

    /**
     * считает сколько строк вернется. проверяем есть ли слово в столбце значений  +проверка есть ли id в столбце значений
     *
     * @param value_word_id
     * @return
     */
    private Integer rowСount(int value_word_id) {
        return jdbcTemplate.queryForObject("select count(value_word_id) from row where value_word_id = ?", new Object[]{value_word_id}, new BeanPropertyRowMapper<>(Integer.class));
    }

    /**
     * проверяем есть ли строки удовлетворяющие rowСount   (проверка есть ли id слова в списке значений)
     *
     * @param value_word_id
     * @return
     */
    private boolean checkForTheExistenceOfTheIdentifier(int value_word_id) {
        if (rowСount(value_word_id) > 0) {
            return true;
        } else return false;
    }

    /**
     * удаление строоки по id
     *
     * @param value_word_id
     */
    public void deleteRowWord(int value_word_id) {
        jdbcTemplate.update("delete from word where value_word_id = ?", value_word_id);
    }

    /**
     * если нет слова, то удаляем слово из word
     *
     * @param value_word_id
     */
    private void deleteWord(int value_word_id) {
        if (checkForTheExistenceOfTheIdentifier(value_word_id) == false) {
            deleteRowWord(value_word_id);
        }
    }

    /**
     * сколько записей с нужным id в столбце ключей()
     *
     * @param key_word_id
     * @return
     */
    //проверка есть ли id в столбце ключей
    public Integer searchKey(int key_word_id) {
        return jdbcTemplate.queryForObject("select count (key_word_id) from row where key_word_id = ?", new Object[]{key_word_id}, new BeanPropertyRowMapper<>(Integer.class));
    }

    /**
     * проверяем существует ли такая пара в таблице row
     *
     * @param key_word_id
     * @param value_word_id
     * @return
     */
    //есть ли уже такая пара
    public boolean isThereACoupleOf(int key_word_id, int value_word_id) {
        if (searchKey(key_word_id) == 0 && rowСount(value_word_id) == 0) {
            add(key_word_id, value_word_id);
            return true; //значит можем добавлять

        } else return false;
    }


}
