package com.senai.estoquefarma.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.senai.estoquefarma.database.DatabaseHelper;
import com.senai.estoquefarma.entities.User;

import java.util.ArrayList;

public class UserController {

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public UserController(Context context){
        this.databaseHelper = new DatabaseHelper(context);
    }

    // Método para cadastrar um user
    public String save(User user){
        ContentValues values;

        db = databaseHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("nome", user.getName());
        values.put("email", user.getEmail());
        values.put("senha", user.getSenha());

        long result = db.insert("user", null, values);

        if (result == -1){
            return "ERROR TO SAVE USER";
        }else{
            return "USER SAVED SUCESSFULLY";
        }
    }

    public ArrayList<User> findAll(){
        db = databaseHelper.getReadableDatabase();

        String[] campos = {"id","nome","email","senha"};
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = db.query("user", campos, null, null, null, null, null);

        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"));

            users.add(new User(id, name, email, senha));
        }
        cursor.close();
        return users;
    }

    public boolean authentication(String email, String senha){
        db = databaseHelper.getReadableDatabase();
        String where = String.format("email = '%s' AND senha = '%s'", email, senha);
        String[] campos = {"email", "senha"};
        Cursor cursor = db.query("user", campos, where, null, null, null, null);

        return cursor.moveToNext();
    }
}

