package server;

import java.sql.*;

public class ConexionBD {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "sistema_facturacion";
    private static final String USER = "root";
    private static final String PASSWORD = "Santiago06!";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void crearBaseDatos() {
        String urlTemp = "jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try (Connection conn = DriverManager.getConnection(urlTemp, USER, PASSWORD);
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE
                    + " DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci");
        } catch (SQLException e) {
            System.out.println("Error al crear base de datos: " + e.getMessage());
        }
    }

    public static void crearTablas() {
        try (Connection conn = conectar(); Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Usuario (" +
                    "usuario VARCHAR(50) PRIMARY KEY," +
                    "contrasena VARCHAR(100) NOT NULL)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Cliente (" +
                    "id VARCHAR(20) PRIMARY KEY," +
                    "nombre VARCHAR(100) NOT NULL," +
                    "correo VARCHAR(100)," +
                    "telefono VARCHAR(50)," +
                    "FOREIGN KEY (usuario) REFERENCES Usuario(usuario))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Producto (" +
                    "codigoProducto VARCHAR(20) PRIMARY KEY," +
                    "nombreProducto VARCHAR(100) NOT NULL," +
                    "precio DOUBLE NOT NULL," +
                    "cantidad INT NOT NULL)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Factura (" +
                    "id VARCHAR(20) PRIMARY KEY," +
                    "cliente_id VARCHAR(20)," +
                    "FOREIGN KEY (cliente_id) REFERENCES Cliente(id))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS ItemFactura (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "factura_id VARCHAR(20)," +
                    "producto_codigo VARCHAR(20)," +
                    "cantidad INT NOT NULL," +
                    "FOREIGN KEY (factura_id) REFERENCES Factura(id)," +
                    "FOREIGN KEY (producto_codigo) REFERENCES Producto(codigoProducto))");
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
    }

    public static void insertarDatosEjemplo() {
        try (Connection conn = conectar()) {
            conn.createStatement().executeUpdate(
                    "INSERT INTO Usuario (usuario, contrasena) " +
                    "SELECT 'admin','admin123' WHERE NOT EXISTS (SELECT 1 FROM Usuario WHERE usuario='admin')");

            conn.createStatement().executeUpdate(
                    "INSERT INTO Cliente (id, nombre, correo, usuario) " +
                    "SELECT 'C001','Juan Pérez','juan@correo.com','admin' WHERE NOT EXISTS (SELECT 1 FROM Cliente WHERE id='C001')");

            conn.createStatement().executeUpdate(
                    "INSERT INTO Producto (codigoProducto, nombreProducto, precio, cantidad) " +
                    "SELECT 'P001','Laptop',1200.00,10 WHERE NOT EXISTS (SELECT 1 FROM Producto WHERE codigoProducto='P001')");
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }

    public static void inicializarBD() {
        crearBaseDatos();
        crearTablas();
        insertarDatosEjemplo();
    }
}
