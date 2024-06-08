package com.example.lab6_20182895.Bean;

public class Usuario {
    private String nombre;
    private String email;
    // Puedes agregar más atributos según sea necesario

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Métodos getter y setter para los atributos

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
