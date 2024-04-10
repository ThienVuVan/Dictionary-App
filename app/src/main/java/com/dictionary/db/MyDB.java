package com.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.dictionary.model.Word;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String WordTable = "WordTable";
    public static final String WordId = "WordId";
    public static final String OriginalText = "OriginalText";
    public static final String Type = "Type";
    public static final String TranslatedText = "TranslatedText";
    public static final String Definition = "Definition";
    public static final String Synonyms = "Synonyms";
    public static final String Antonyms = "Antonyms";
    public static final String Example = "Example";
    public static final String IsMark = "IsMark";
    public static final String Audio = "Audio";
    private static MyDB instance;

    private MyDB(@Nullable Context context) {
        super(context, "DictionaryDB", null, 1);
    }

    public static synchronized MyDB getInstance(Context context) {
        if (instance == null) {
            instance = new MyDB(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create WordTable
        String createWordTableSQL = "CREATE TABLE IF NOT EXISTS " + WordTable + "("
                + WordId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OriginalText + " TEXT, "
                + Type + " TEXT, "
                + TranslatedText + " TEXT, "
                + Definition + " TEXT, "
                + Synonyms + " TEXT, "
                + Antonyms + " TEXT, "
                + IsMark + " INTEGER, "
                + Example + " TEXT, "
                + Audio + " TEXT)";
        db.execSQL(createWordTableSQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WordTable);
        onCreate(db);
    }

    // CRUD operations for Word
    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OriginalText, word.getOriginal_text());
        values.put(Type, word.getType());
        values.put(TranslatedText, word.getTranslated_text());
        values.put(Definition, word.getDefinition());
        values.put(Synonyms, word.getSynonyms());
        values.put(Antonyms, word.getAntonyms());
        values.put(IsMark, word.getMark());
        values.put(Example, word.getExample());
        values.put(Audio, word.getAudio());
        db.insert(WordTable, null, values);
        db.close();
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> list = new ArrayList<>();
        String sql = "SELECT * FROM " + WordTable;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Word word = new Word(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                list.add(word);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public ArrayList<Word> getAllMarkedWords() {
        ArrayList<Word> list = new ArrayList<>();
        String sql = "SELECT * FROM " + WordTable + " WHERE " + IsMark + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Word word = new Word(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                list.add(word);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public void updateMark(int wordId, int markStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IsMark, markStatus);
        db.update(WordTable, values, WordId + " = ?", new String[]{String.valueOf(wordId)});
        db.close();
    }

    public void deleteMark(int wordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IsMark, 0); // Assuming 0 means unmarked
        db.update(WordTable, values, WordId + " = ?", new String[]{String.valueOf(wordId)});
        db.close();
    }

    public void deleteWord(int wordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WordTable, WordId + " = ?", new String[]{String.valueOf(wordId)});
        db.close();
    }

    public boolean isWordExists(String originalText) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WordTable, new String[]{WordId}, OriginalText + "=?",
                new String[]{originalText}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public Word getWordByOriginalText(String originalText) {
        Word word = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WordTable, null, OriginalText + "=?",
                new String[]{originalText}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            word = new Word(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getString(8),
                    cursor.getString(9)
            );
            cursor.close();
        }
        db.close();
        return word;
    }

    public void deleteAllWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WordTable, null, null); // Xóa tất cả dữ liệu trong bảng
        db.close();
    }

}