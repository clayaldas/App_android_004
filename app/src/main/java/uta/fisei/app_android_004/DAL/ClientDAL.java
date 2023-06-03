package uta.fisei.app_android_004.DAL;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import uta.fisei.app_android_004.DataBaseAdmin;
import uta.fisei.app_android_004.Entities.Client;

public class ClientDAL {
    private DataBaseAdmin dataBaseAdmin;
    private SQLiteDatabase sql;
    private Context context;

    public ClientDAL(Context context) {
        this.context = context;
    }

    private void openWrite() {
        dataBaseAdmin = new DataBaseAdmin(context,
                "SEXTODB", null, 1);
        sql = dataBaseAdmin.getWritableDatabase();
    }

    private void openRead() {
        dataBaseAdmin = new DataBaseAdmin(context,
                "SEXTODB", null, 1);
        sql = dataBaseAdmin.getReadableDatabase();
    }

    private void close() {
        sql.close();
    }

    public long insert(Client client) {
        openWrite();

        long count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("Name", client.getName());
            values.put("LastName", client.getLastName());
            values.put("Phone", client.getPhone());
            values.put("Email", client.getEmail());

            count = sql.insert("Clients", null, values);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            //close();
            sql.close();
        }
        return  count;
    }

    public Client search(int code) {
        openRead();

        Client client = null;

        try {
            final String SELECT = "SELECT Name, LastName, Phone, Email " +
                                  "FROM Clients " +
                                  "WHERE Code = " + code;

            Cursor cursor = sql.rawQuery(SELECT, null);

            if (cursor.moveToFirst()) {
                client = new Client();

                client.setName(cursor.getString(0));
                client.setLastName(cursor.getString(1));
                client.setPhone(cursor.getString(2));
                client.setEmail(cursor.getString(3));
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            sql.close();
        }

        return client;
    }

    /*private ArrayList<Client> seachAll() {

        }*/

    public ArrayList<String> searchAll () {
        openRead();

        ArrayList<String> listClients = null;

        try {
            final String SELECT = "SELECT Code, Name, LastName, Phone, Email " +
                                  "FROM Clients";

            Cursor cursor = sql.rawQuery(SELECT, null);

            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    listClients.add(cursor.getString(0) + "   " +
                            cursor.getString(1) + " " + cursor.getString(2));

                   // Client client = new Client();
                    //client.setCode(cursor.getString(0));
                    //client.setName(cursor.getString(1);
                    //..
                    //listClients.add(client)
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            sql.close();
        }

        return  listClients;
    }

    public int delete (int code) {
        int count = 0;

        try {
            final String DELETE = "Code=" + code;
            count = sql.delete("Clients", DELETE , null);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            sql.close();;
        }

        return count;
    }

    public int update (Client client) {
        openWrite();

        int count = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("Name", client.getName());
            values.put("LastName", client.getLastName());
            values.put("Phone", client.getPhone());
            values.put("Email", client.getEmail());

            final String UPDATE = "Code=" + client.getCode();
            count = sql.update("Clients", values,
                    UPDATE, null);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            sql.close();
        }

        return  count;
    }
}
