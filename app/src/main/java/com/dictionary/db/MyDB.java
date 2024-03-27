package com.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.dictionary.model.Mark;
import com.dictionary.model.Word;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String WordTable = "WordTable";
    public static final String MarkTable = "MarkTable";

    // Columns for WordTable
    public static final String WordId = "WordId";
    public static final String OriginalText = "OriginalText";
    public static final String Type = "Type";
    public static final String TranslatedText = "TranslatedText";
    public static final String Definition = "Definition";
    public static final String Synonyms = "Synonyms";
    public static final String Antonyms = "Antonyms";
    public static final String Example = "Example";

    // Columns for MarkTable
    public static final String MarkId = "MarkId";
    public static final String IdWord = "IdWord";

    public MyDB(@Nullable Context context) {
        super(context, "DictionaryDB", null, 1);
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
                + Example + " TEXT)";
        db.execSQL(createWordTableSQL);

        // Create MarkTable
        String createMarkTableSQL = "CREATE TABLE IF NOT EXISTS " + MarkTable + "("
                + MarkId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IdWord + " INTEGER, "
                + "FOREIGN KEY (" + IdWord + ") REFERENCES " + WordTable + "(" + WordId + "))";
        db.execSQL(createMarkTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WordTable);
        db.execSQL("DROP TABLE IF EXISTS " + MarkTable);
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
        values.put(Synonyms, word.getSynonyms().toString());
        values.put(Antonyms, word.getAntonyms().toString());
        values.put(Example, word.getExample());
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
                        new ArrayList<>(), // Placeholder for synonyms
                        new ArrayList<>(), // Placeholder for antonyms
                        cursor.getString(7)
                );
                list.add(word);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    // CRUD operations for Mark
    public void addMark(Mark mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IdWord, mark.getId_word());
        db.insert(MarkTable, null, values);
        db.close();
    }

    public ArrayList<Mark> getAllMarks() {
        ArrayList<Mark> list = new ArrayList<>();
        String sql = "SELECT * FROM " + MarkTable;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Mark mark = new Mark(
                        cursor.getInt(0),
                        cursor.getInt(1)
                );
                list.add(mark);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public void updateMark(int id, Mark mark) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IdWord, mark.getId_word());
        db.update(MarkTable, values, MarkId + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteMark(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + MarkTable + " WHERE " + MarkId + " = ?";
        db.execSQL(sql, new String[]{String.valueOf(id)});
        db.close();
    }
}