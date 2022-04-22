package com.fausto_c.practica_examen.model.dao;

import android.content.Context;
import android.database.Cursor;

import com.fausto_c.practica_examen.model.conexion.DbHelper;
import com.fausto_c.practica_examen.model.vo.Empleado;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao extends Empleado {

    DbHelper dbHelper;

    public EmpleadoDao(){

    }

    public EmpleadoDao(String cedula, String nombres, String apellidos, String fecha_contrato, double salario, int discapacidad, String horario) {
        super(cedula, nombres, apellidos, fecha_contrato, salario, discapacidad, horario);
    }

    public void guardar(Context context){
        dbHelper = new DbHelper(context);
        String nquery = "INSERT INTO empleados " +
                "VALUES('"+getCedula()+"','"+getNombres()+"'" +
                ",'"+getApellidos()+"','"+getFecha_contrato()+"'" +
                ","+getSalario()+","+getDiscapacidad()+",'"+getHorario()+"')";
        dbHelper.noQuery(nquery);
        dbHelper.close();
    }

    public List<Empleado> findAll(Context context){

        List<Empleado> empleadoList = new ArrayList<>();

        dbHelper = new DbHelper(context);
        String sql = "SELECT * FROM empleados";
        Cursor fila = dbHelper.query(sql);

        if (fila.getCount()>0){

            fila.moveToFirst();
            do {

                Empleado empleado = new Empleado();
                empleado.setCedula(fila.getString(0));
                empleado.setNombres(fila.getString(1));
                empleado.setApellidos(fila.getString(2));
                empleado.setFecha_contrato(fila.getString(3));
                empleado.setSalario(fila.getDouble(4));
                empleado.setDiscapacidad(fila.getInt(5));
                empleado.setHorario(fila.getString(6));

                empleadoList.add(empleado);

            }while (fila.moveToNext());
        }

        dbHelper.close();

        return empleadoList;

    }

    public Empleado findById(Context context,String cedula){

        dbHelper = new DbHelper(context);
        Empleado empleado = new Empleado();

        String sql = "SELECT * from empleados WHERE cedula = '" +cedula +"'";

        Cursor fila = dbHelper.query(sql);
        if (fila.moveToFirst()){

            empleado.setCedula(fila.getString(0));
            empleado.setNombres(fila.getString(1));
            empleado.setApellidos(fila.getString(2));
            empleado.setFecha_contrato(fila.getString(3));
            empleado.setSalario(fila.getDouble(4));
            empleado.setDiscapacidad(fila.getInt(5));
            empleado.setHorario(fila.getString(6));

        }
        dbHelper.close();
        return empleado;
    }

    public void delete(Context context,String cedula){
        dbHelper = new DbHelper(context);
        String sql = "DELETE FROM empleados WHERE cedula = '" + cedula + "'";
        dbHelper.noQuery(sql);
        dbHelper.close();

    }

    public void update(Context context,String cedula){

        dbHelper = new DbHelper(context);
        String sql = "UPDATE empleados set nombres= '" + getNombres() + "'" +
                ", apellidos = '" +getApellidos() + "'," +
                "fecha_contrato = '" + getFecha_contrato() + "'," +
                "salario = "+getSalario()+"," +
                "discapacidad = "+getDiscapacidad() + "," +
                "horario = '"+ getHorario() + "' WHERE cedula = '" +cedula+"'";
        dbHelper.noQuery(sql);
        dbHelper.close();
    }
}
