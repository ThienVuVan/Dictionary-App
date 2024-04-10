package com.dictionary.model;

import com.dictionary.api.WordResult;

import java.util.List;

public class ApiResult {
    private Word word;
    private List<WordDetail> wordDetailList;

    public ApiResult() {
    }

    public ApiResult(Word word, List<WordDetail> wordDetailList) {
        this.word = word;
        this.wordDetailList = wordDetailList;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public List<WordDetail> getWordDetailList() {
        return wordDetailList;
    }

    public void setWordDetailList(List<WordDetail> wordDetailList) {
        this.wordDetailList = wordDetailList;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "word=" + word +
                '}';
    }
}
