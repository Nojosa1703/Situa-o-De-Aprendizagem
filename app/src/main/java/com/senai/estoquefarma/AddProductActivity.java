package com.senai.estoquefarma;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.senai.estoquefarma.database.DatabaseHelper;

public class AddProductActivity extends AppCompatActivity {

    private EditText inputNome, inputPreco, inputQuantidade;
    private Spinner spinnerCategoria;
    private Button btnCadastrarProduto;
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        inputNome = findViewById(R.id.inputNome2);
        inputPreco = findViewById(R.id.inputPreco2);
        inputQuantidade = findViewById(R.id.inputQuantidade2);
        spinnerCategoria = findViewById(R.id.spinnerCategoria2);
        btnCadastrarProduto = findViewById(R.id.btnCadastrarProduto2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarProduto();
            }
        });
    }

    private void cadastrarProduto() {
        String nome = inputNome.getText().toString();
        String precoStr = inputPreco.getText().toString();
        String quantidadeStr = inputQuantidade.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (nome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        double preco;
        int quantidade;

        try {
            preco = Double.parseDouble(precoStr);
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço ou quantidade inválidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("categoria", categoria);
        values.put("preco", preco);
        values.put("quantidade", quantidade);

        try {
            long result = db.insert("produtos", null, values);
            if (result == -1) {
                Toast.makeText(this, "Erro ao cadastrar produto.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                inputNome.setText("");
                inputPreco.setText("");
                inputQuantidade.setText("");
                spinnerCategoria.setSelection(0);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
