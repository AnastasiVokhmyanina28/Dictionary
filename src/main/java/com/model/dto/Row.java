package com.model.dto;

public class Row {
    private Long id;
    private Long keyWordId;
    private Long valueWordId;


    public Row(Long id, Long keyWordId, Long valueWordId) {
        this.id = id;
        this.keyWordId = keyWordId;
        this.valueWordId = valueWordId;
    }

    public Long getId() {
        return id;
    }

    public Long getKeyWordId() {
        return keyWordId;
    }

    public Long getValueWordId() {
        return valueWordId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKeyWordId(Long keyWordId) {
        this.keyWordId = keyWordId;
    }

    public void setValueWordId(Long valueWordId) {
        this.valueWordId = valueWordId;
    }
}

