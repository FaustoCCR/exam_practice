package com.fausto_c.practica_examen.model.vo;

public class Empleado {

    private String cedula;
    private String nombres;
    private String apellidos;
    private String fecha_contrato;
    private double salario;
    private int discapacidad;
    private String horario;

    public Empleado(){

    }

    public Empleado(String cedula, String nombres, String apellidos, String fecha_contrato, double salario, int discapacidad, String horario) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_contrato = fecha_contrato;
        this.salario = salario;
        this.discapacidad = discapacidad;
        this.horario = horario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(String fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(int discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
