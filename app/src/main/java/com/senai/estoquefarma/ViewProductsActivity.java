package com.senai.estoquefarma;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.senai.estoquefarma.database.DatabaseHelper;
import java.util.ArrayList;

public class ViewProductsActivity extends AppCompatActivity {

    private ListView listViewProdutos;
    private Button btnVoltar;
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> produtosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_products_activity);

        listViewProdutos = findViewById(R.id.listViewProdutos);
        btnVoltar = findViewById(R.id.btnVoltar);


        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();


        produtosList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtosList);
        listViewProdutos.setAdapter(adapter);

        carregarProdutos();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Voltar para a tela anterior
            }
        });
    }

    private void carregarProdutos() {
        Cursor cursor = db.query("produtos", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
                double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));
                int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"));

                String produtoInfo = String.format("Nome: %s\nCategoria: %s\nPreço: R$%.2f\nQuantidade: %d",
                        nome, categoria, preco, quantidade);
                produtosList.add(produtoInfo);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Nenhum produto cadastrado.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
