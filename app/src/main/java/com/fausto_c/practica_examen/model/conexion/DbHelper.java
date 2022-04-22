package com.fausto_c.practica_examen.model.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    //constantes para la creación de la BD
    private static final String DATABASE_NAME = "supermercado.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE empleados(cedula TEXT NOT NULL PRIMARY KEY UNIQUE," +
                "nombres TEXT," +
                "apellidos TEXT," +
                "fecha_contrato TEXT," +
                "salario REAL," +
                "discapacidad INTEGER," +
                "horario TEXT)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*Métodos para realizar cambios en la data*/
    public void noQuery(String nquery){
        this.getWritableDatabase().execSQL(nquery);
    }

    public Cursor query(String sql){
        return this.getReadableDatabase().rawQuery(sql,null);
    }
}
