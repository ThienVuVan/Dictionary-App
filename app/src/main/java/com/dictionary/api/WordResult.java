package com.dictionary.api;
import java.util.List;

public class WordResult {
    private String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public List<Meaning> getMeanings() {
        return meanings;
    }
    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }
    private List<Meaning> meanings;

}
