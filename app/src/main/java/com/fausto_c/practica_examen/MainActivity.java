package com.fausto_c.practica_examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fausto_c.practica_examen.model.conexion.DbHelper;
import com.fausto_c.practica_examen.model.dao.EmpleadoDao;
import com.fausto_c.practica_examen.model.vo.Empleado;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtCedula, txtNombres, txtApellidos, txtFechaContrato,
            txtSalario, txtDiscapacidad, txtHorario;
    private ImageButton btncreateDB, btnGuardar, btnEliminar, btnModificar, btnSiguiente;


    //fields
    String cedula;
    String nombres;
    String apellidos;
    String fecha_contrato;
    String salario;
    String discapacidad;
    String horario;

    //DAO
    EmpleadoDao empleadoDao;

    //Table
    private TableLayout tlempleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCedula = findViewById(R.id.txtCedula);
        txtNombres = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellido);
        txtFechaContrato = findViewById(R.id.txtHire_date);
        txtSalario = findViewById(R.id.txtSalario);
        txtDiscapacidad = findViewById(R.id.txtDiscapacidad);
        txtHorario = findViewById(R.id.txtHorario);

        //tabla
        tlempleados = findViewById(R.id.tlempleados);

        //botones
        btncreateDB = findViewById(R.id.iBtnCreateDb);
        btnGuardar = findViewById(R.id.iBtnGuardar);
        btnEliminar = findViewById(R.id.iBtnEliminar);
        btnModificar = findViewById(R.id.iBtnEditar);
        btnSiguiente = findViewById(R.id.iBtnNext);

        btncreateDB.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);


        fillTable();

    }

    private void createDB() {
        DbHelper dbHelper = new DbHelper(this.getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Toast.makeText(this, db != null ? "BASE CREADA" : "ERROR AL CREAR LA BASE", Toast.LENGTH_LONG).show();
    }


    //METODOS CRUD
    public void insertar() {

        cedula = txtCedula.getText().toString();
        nombres = txtNombres.getText().toString();
        apellidos = txtApellidos.getText().toString();
        fecha_contrato = txtFechaContrato.getText().toString();
        salario = txtSalario.getText().toString();
        discapacidad = txtDiscapacidad.getText().toString();
        horario = txtHorario.getText().toString();

        if (!cedula.isEmpty() && !nombres.isEmpty() && !apellidos.isEmpty() &&
                !fecha_contrato.isEmpty() && !salario.isEmpty() && !discapacidad.isEmpty() &&
                !horario.isEmpty()) {

            if (!checkCedula(cedula)) {
                double salariocount = Double.parseDouble(salario);
                int discapacidadPercent = Integer.parseInt(discapacidad);

                empleadoDao = new EmpleadoDao(cedula, nombres, apellidos, fecha_contrato,
                        salariocount, discapacidadPercent, horario);
                empleadoDao.guardar(this);
                Toast.makeText(this, "Empleado creado", Toast.LENGTH_SHORT).show();
                cleanFields();
                fillTable();
            } else {
                Toast.makeText(this, "Ya existe un registro con esa cédula", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Ingrese los campos indicados", Toast.LENGTH_SHORT).show();
        }

    }

    private void fillTable() {
        tlempleados.removeAllViews();
        empleadoDao = new EmpleadoDao();

        List<Empleado> empleadoList = empleadoDao.findAll(this);

        if (!empleadoList.isEmpty()) {

            //variables de las celdas de las filas
            TextView tdCedula, tdEmpleado, tdFechaContrato, tdSalario, tdDiscapacidad;

            for (Empleado empleado :
                    empleadoList) {

                View fields = LayoutInflater.from(this).inflate(R.layout.item_table_layout, null, false);
                tdCedula = fields.findViewById(R.id.tdCedula);
                tdEmpleado = fields.findViewById(R.id.tdEmpleado);
                tdFechaContrato = fields.findViewById(R.id.tdHireDate);
                tdSalario = fields.findViewById(R.id.tdSalario);
                tdDiscapacidad = fields.findViewById(R.id.tdDiscapacidad);

                tdCedula.setText(empleado.getCedula());
                tdEmpleado.setText(empleado.getNombres() + " " + empleado.getApellidos());
                tdFechaContrato.setText(empleado.getFecha_contrato());
                tdSalario.setText(String.valueOf(empleado.getSalario()));
                tdDiscapacidad.setText(String.valueOf(empleado.getDiscapacidad()));

                tlempleados.addView(fields);
            }

        }

    }

    private void cleanFields() {

        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtFechaContrato.setText("");
        txtSalario.setText("");
        txtDiscapacidad.setText("");
        txtHorario.setText("");

    }

    public void clickRow(View view) {

        /*Este método lo llamamos en el evento onClick del archivo
         * layout  --> item_table_layout.xml
         * */

        resetColors();

        view.setBackgroundColor(Color.parseColor("#9AC6E2"));
        TableRow row = (TableRow) view;
        TextView cell = (TextView) row.getChildAt(0);//--> extraemos el textView con el código
        String cedula = cell.getText().toString();

        if (!cedula.isEmpty()) {

            empleadoDao = new EmpleadoDao();

            Empleado empleado = empleadoDao.findById(this, cedula);

            txtCedula.setText(empleado.getCedula());
            txtNombres.setText(empleado.getNombres());
            txtApellidos.setText(empleado.getApellidos());
            txtFechaContrato.setText(empleado.getFecha_contrato());
            txtSalario.setText(String.valueOf(empleado.getSalario()));
            txtDiscapacidad.setText(String.valueOf(empleado.getDiscapacidad()));
            txtHorario.setText(empleado.getHorario());
        }

    }

    public void resetColors() {
        for (int i = 0; i < tlempleados.getChildCount(); i++) {

            View cell = tlempleados.getChildAt(i);
            cell.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }


    private void eliminar() {

        String cedula = txtCedula.getText().toString();
        if (!cedula.isEmpty()) {
            if (checkCedula(cedula)) {
                empleadoDao = new EmpleadoDao();
                empleadoDao.delete(this, cedula);
                Toast.makeText(this.getApplicationContext(), "Empleado eliminado", Toast.LENGTH_LONG).show();
                cleanFields();
                fillTable();
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "Ingrese la cédula correspondiente", Toast.LENGTH_LONG).show();
            cleanFields();
        }

    }

    public boolean checkCedula(String cedula) {

        Empleado empleado = empleadoDao.findById(this, cedula);

        if (empleado.getCedula() != null) {
            return true;
        } else {
            cleanFields();
            Toast.makeText(this, "No existe registro con la cédula:" + cedula, Toast.LENGTH_LONG).show();
            return false;
        }
    }


    private void update() {

        cedula = txtCedula.getText().toString();
        nombres = txtNombres.getText().toString();
        apellidos = txtApellidos.getText().toString();
        fecha_contrato = txtFechaContrato.getText().toString();
        salario = txtSalario.getText().toString();
        discapacidad = txtDiscapacidad.getText().toString();
        horario = txtHorario.getText().toString();


        if (!cedula.isEmpty() && !nombres.isEmpty() && !apellidos.isEmpty() &&
                !fecha_contrato.isEmpty() && !salario.isEmpty() && !discapacidad.isEmpty() &&
                !horario.isEmpty()) {
            if (checkCedula(cedula)) {
                double salariocount = Double.parseDouble(salario);
                int discapacidadPercent = Integer.parseInt(discapacidad);

                empleadoDao = new EmpleadoDao(cedula, nombres, apellidos, fecha_contrato,
                        salariocount, discapacidadPercent, horario);

                empleadoDao.update(this, cedula);
                Toast.makeText(this, "Registro modificiado", Toast.LENGTH_SHORT).show();
                cleanFields();
                fillTable();
            }

        } else {
            Toast.makeText(this, "Ingrese los campos indicados", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Puede seleccionarlos dando click en la tabla", Toast.LENGTH_SHORT).show();
        }


    }


    private void goToNextActivity() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iBtnCreateDb:
                createDB();
                break;
            case R.id.iBtnGuardar:
                insertar();
                break;
            case R.id.iBtnEliminar:
                eliminar();
                break;
            case R.id.iBtnEditar:
                update();
                break;
            case R.id.iBtnNext:
                goToNextActivity();
                break;
        }

    }


}