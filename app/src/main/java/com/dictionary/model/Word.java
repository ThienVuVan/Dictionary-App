package com.dictionary.model;

public class Word {
    private int id;
    private String original_text;
    private String translated_text;
    private String phonetic;
    private String audio;
    private Integer isMark;

    public Word() {
    }

    public Word(int id, String original_text, String translated_text, String phonetic, Integer isMark, String audio) {
        this.id = id;
        this.original_text = original_text;
        this.translated_text = translated_text;
        this.phonetic = phonetic;
        this.isMark = isMark;
        this.audio = audio;
    }

    public Word(String original_text, String translated_text, String phonetic, Integer isMark, String audio) {
        this.original_text = original_text;
        this.translated_text = translated_text;
        this.phonetic = phonetic;
        this.audio = audio;
        this.isMark = isMark;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", original_text='" + original_text + '\'' +
                ", translated_text='" + translated_text + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", audio='" + audio + '\'' +
                ", isMark=" + isMark +
                '}';
    }

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

    public String getTranslated_text() {
        return translated_text;
    }

    public void setTranslated_text(String translated_text) {
        this.translated_text = translated_text;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Integer getIsMark() {
        return isMark;
    }

    public void setIsMark(Integer isMark) {
        this.isMark = isMark;
    }
}
