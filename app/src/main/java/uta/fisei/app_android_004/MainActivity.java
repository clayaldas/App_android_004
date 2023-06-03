package uta.fisei.app_android_004;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uta.fisei.app_android_004.DAL.ClientDAL;
import uta.fisei.app_android_004.Entities.Client;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCode;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextPhone;
    private EditText editTextEmail;

    private ClientDAL clientDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCode = findViewById(R.id.editTextCode);
        editTextName =findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        //clientDAL = new ClientDAL();
    }

    public void onClickButtonInsert(View view){
        //insertWithoutClass();
        insertWithClass();
    }

    private void insertWithoutClass() {
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(this,
                "SEXTODB", null, 1);
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();

        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        //sql.execSQL("INSERT INTO Clients (Name, LastName, Phone, Email) " +
        //           "VALUES ('"+name+"', '"+lastName+"', '"+phone+"', '"+email+"')");

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Email", email);

        long count = sql.insert("Clients", null, values);

        sql.close();

        Toast.makeText(this, "Insertado",
                Toast.LENGTH_SHORT).show();

        clearTexts();
    }

    private void insertWithClass() {

        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        Client client = new Client();
        client.setName(name);
        client.setLastName(lastName);
        client.setPhone(phone);
        client.setEmail(email);

        clientDAL = new ClientDAL(this);

        long count = clientDAL.insert(client);

        if (count==0) {
            Toast.makeText(this,
                    "No pude insertar el registro",
                    Toast.LENGTH_SHORT).show();
        }

        clearTexts();
    }

    private void clearTexts() {
        editTextName.setText("");
        editTextLastName.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
    }

    public void onClickButtonSearch(View view){
        //searchWithoutClass();
        searchWithClass();
    }

    private void searchWithoutClass() {
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(this,
                "SEXTODB", null, 1);
        SQLiteDatabase sql = dataBaseAdmin.getReadableDatabase();

        String code = editTextCode.getText().toString();

        if (!code.matches("")) {
            //final String SELECT = "SELECT * " +
            //                     "FROM Clients";

            final String SELECT = "SELECT Name, LastName, Phone, Email " +
                    "FROM Clients " +
                    "WHERE Code = " + code;

            Cursor cursor = sql.rawQuery(SELECT, null);

            if (cursor.moveToFirst()) {
                editTextName.setText(cursor.getString(0));
                editTextLastName.setText(cursor.getString(1));
                editTextPhone.setText(cursor.getString(2));
                editTextEmail.setText(cursor.getString(3));
            }
            else {
                Toast.makeText(this, "Cliente no encontrado",
                        Toast.LENGTH_SHORT).show();

                clearTexts();
            }
        }
        else {
            Toast.makeText(this, "El código es requerido",
                    Toast.LENGTH_SHORT).show();
        }

        sql.close();
    }

    private void searchWithClass() {

        String code = editTextCode.getText().toString();

        if (!code.matches("")) {

            clientDAL = new ClientDAL(this);

            Client client;

            client = clientDAL.search(Integer.valueOf(code));

            if (client!= null) {
                editTextName.setText(client.getName());
                editTextLastName.setText(client.getLastName());
                editTextPhone.setText(client.getPhone());
                editTextEmail.setText(client.getEmail());
            }
            else {
                Toast.makeText(this, "Cliente no encontrado",
                        Toast.LENGTH_SHORT).show();

                clearTexts();
            }
        }
        else {
            Toast.makeText(this, "El código es requerido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButtonDelete(View view){
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(this,
                "SEXTODB", null, 1);
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();

        String code = editTextCode.getText().toString();

        //sql.execSQL("DELETE FROM Clients.....");
        //final String DELETE = "Code=" + "+code+";
        final String DELETE = "Code=" + code;
        int count = sql.delete("Clients", DELETE , null);

        if ( count>0 ) {
            Toast.makeText(this, "Cliente eliminado",
                    Toast.LENGTH_SHORT).show();

            clearTexts();
            editTextCode.setText("");
        }
        else {
            Toast.makeText(this, "El cliente no existe",
                    Toast.LENGTH_SHORT).show();
        }


        sql.close();
    }

    public void onClickButtonUpdate(View view){
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(this, "SEXTODB", null, 1);
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();

        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        String code = editTextCode.getText().toString();

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Email", email);

        final String UPDATE = "Code=" + code;
        int count = sql.update("Clients", values,
                UPDATE, null);

        if ( count>0 ) {
            Toast.makeText(this, "Cliente modificado",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "El cliente no existe",
                    Toast.LENGTH_SHORT).show();
        }

        sql.close();
    }

    public void onClickButtonSelectAll(View view){
        Intent intent = new Intent(this, SelectAllActivity.class);
        startActivity(intent);
    }
}