package com.model.dto;

public class Language {
  private Long id;
  private String languageName;
  private String languagePattern;

    public Language(Long id, String languageName, String languagePattern) {
        this.id = id;
        this.languageName = languageName;
        this.languagePattern = languagePattern;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setLanguagePattern(String languagePattern) {
        this.languagePattern = languagePattern;
    }

    public Long getId() {
        return id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getLanguagePattern() {
        return languagePattern;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", languagePattern='" + languagePattern + '\'' +
                '}';
    }
}
