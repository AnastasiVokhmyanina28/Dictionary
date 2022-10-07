package com.model.dto;

public class Word {
    private Long id;
    private String wordName;
    private Long wordLanguageId;

    public Word(Long id, String wordName, Long wordLanguageId) {
        this.id = id;
        this.wordName = wordName;
        this.wordLanguageId = wordLanguageId;
    }

    public Long getId() {
        return id;
    }

    public String getWordName() {
        return wordName;
    }

    public Long getWordLanguageId() {
        return wordLanguageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public void setWordLanguageId(Long wordLanguageId) {
        this.wordLanguageId = wordLanguageId;
    }
}
