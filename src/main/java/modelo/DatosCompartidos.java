package modelo;

import java.util.ArrayList;
import java.util.List;

public class DatosCompartidos {
    private static List<Cliente> clientes = new ArrayList<>();
    private static Cliente clienteSeleccionado;

    public static void agregarCliente(Cliente c) {
        clientes.add(c);
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public static void setClienteSeleccionado(Cliente cliente) {
        clienteSeleccionado = cliente;
    }
}
