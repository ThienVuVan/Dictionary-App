package com.dictionary.model;

public class WordDetail {
    private String type;
    private String definition;
    private String example;
    private String synonyms;
    private String antonyms;

    public WordDetail() {
    }

    public WordDetail(String type, String definition, String example, String synonyms, String antonyms) {
        this.type = type;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    @Override
    public String toString() {
        return "WordDetail{" +
                "type='" + type + '\'' +
                ", definition='" + definition + '\'' +
                ", example='" + example + '\'' +
                ", synonyms='" + synonyms + '\'' +
                ", antonyms='" + antonyms + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }
}
