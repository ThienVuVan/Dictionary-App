package com.dictionary.model;

public class Word {
    private int id;
    private String original_text;
    private String type;
    private String translated_text;
    private String definition;
    private String synonyms;
    private String antonyms;
    private String example;
    private Integer isMark;

    // Constructors
    public Word(int id, String original_text, String type, String translated_text, String definition,
                String synonyms, String antonyms, Integer isMark, String example) {
        this.id = id;
        this.original_text = original_text;
        this.type = type;
        this.translated_text = translated_text;
        this.definition = definition;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.example = example;
        this.isMark = isMark;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_text() {
        return original_text;
    }

    public void setOriginal_text(String original_text) {
        this.original_text = original_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTranslated_text() {
        return translated_text;
    }

    public void setTranslated_text(String translated_text) {
        this.translated_text = translated_text;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
    public String getSynonyms() {
        return synonyms;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }

    public Integer getMark() {
        return isMark;
    }

    public void setMark(Integer mark) {
        isMark = mark;
    }
}
