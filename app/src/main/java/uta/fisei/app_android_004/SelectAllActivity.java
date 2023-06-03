package uta.fisei.app_android_004;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectAllActivity extends AppCompatActivity {

    private ListView listViewData;
    private ArrayList<String> arrayListClients;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_all);

        listViewData = findViewById(R.id.listViewData);

        arrayListClients = fillListView();

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, arrayListClients);

        listViewData.setAdapter(adapter);
    }

    private ArrayList fillListView() {
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(this,
                "SEXTODB", null, 1);
        SQLiteDatabase sql = dataBaseAdmin.getReadableDatabase();

        ArrayList<String> listClients = new ArrayList<String>();

        final String SELECT = "SELECT Code, Name, LastName, Phone, Email " +
                              "FROM Clients";

        Cursor cursor = sql.rawQuery(SELECT, null);

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                listClients.add(cursor.getString(0) + "   " +
                        cursor.getString(1) + " " + cursor.getString(2));
            }
        }
        else {
            Toast.makeText(this, "No existen clientes",
                    Toast.LENGTH_SHORT).show();
        }

        sql.close();

        return listClients;
    }
}