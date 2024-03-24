package com.dictionary.model;

public class History {
    private int id;
    private int id_word;

    // Constructors
    public History(int id, int id_word) {
        this.id = id;
        this.id_word = id_word;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_word() {
        return id_word;
    }

    public void setId_word(int id_word) {
        this.id_word = id_word;
    }
}
