package com.senai.estoquefarma.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String NAME_DATABASE = "db_app.db";
    private final int VERSION_DATABASE = 1;

    public DatabaseHelper(Context context) {
        super(context, "db_app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlUser = "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(45) NOT NULL," +
                "email VARCHAR(45) NOT NULL," +
                "senha VARCHAR(45) NOT NULL);";

        String sqlProdutos = "CREATE TABLE produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(45) NOT NULL," +
                "categoria VARCHAR(45) NOT NULL," +
                "preco REAL NOT NULL," +
                "quantidade INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(sqlUser);
        sqLiteDatabase.execSQL(sqlProdutos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS produtos");
        onCreate(sqLiteDatabase);
    }
}
