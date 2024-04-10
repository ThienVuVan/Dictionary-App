package com.dictionary.model;

import com.dictionary.api.WordDetail;

public class ApiResult {
    private Word word;
    private WordDetail word_detail;

    public ApiResult() {
    }

    public ApiResult(Word word, WordDetail word_detail) {
        this.word = word;
        this.word_detail = word_detail;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public WordDetail getWord_detail() {
        return word_detail;
    }

    public void setWord_detail(WordDetail word_detail) {
        this.word_detail = word_detail;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "word=" + word +
                '}';
    }
}
