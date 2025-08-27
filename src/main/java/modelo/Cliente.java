package modelo;

import java.io.Serializable;
public class Cliente extends Usuario implements Serializable {
    private String id; // Identificador único del cliente
    private String nombre; // Nombre completo del cliente
    private String correo; // Correo electrónico del cliente
     private String telefono; // Correo electrónico del cliente

    public Cliente(String id, String nombre, String correo, String usuario, String contrasena, String contrasena1) {
        super(usuario, contrasena); // Llamada al constructor de Usuario
        this.id = id; // Asignar ID
        this.nombre = nombre; // Asignar nombre
        this.correo = correo; // Asignar correo
        this.telefono = telefono;
       
    }

    public String getId() { return id; } // Obtener ID
    public String getNombre() { return nombre; } // Obtener nombre
    public String getCorreo() { return correo; } // Obtener correo
    public String telefono() { return telefono; }
}