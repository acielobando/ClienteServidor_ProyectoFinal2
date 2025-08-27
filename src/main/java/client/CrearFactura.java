package client;

import com.lowagie.text.Document;



import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.ItemFactura;
import modelo.Producto;

public class CrearFactura {
    private Cliente cliente;
    private List<ItemFactura> items;

    public CrearFactura(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    // Agregar item a la factura
    public void agregarItem(Producto producto, int cantidad) {
        items.add(new ItemFactura(producto, cantidad));
    }

    // Calcular el total de la factura
    public double calcularTotal() {
        double total = 0;
        for (ItemFactura item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }
public List<ItemFactura> getItems() {
    return items;
}
    // Exportar la factura a PDF con OpenPDF
    public void exportarFacturaPDF(String rutaArchivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // Encabezado
            document.add(new Paragraph("Factura"));
            document.add(new Paragraph("Cliente: " + cliente.getNombre()));
            document.add(new Paragraph(" ")); // Espacio

            // Tabla de productos
            PdfPTable table = new PdfPTable(4);
            table.addCell("Producto");
            table.addCell("Precio");
            table.addCell("Cantidad");
            table.addCell("Subtotal");

            for (ItemFactura item : items) {
                table.addCell(item.getProducto().getNombreProducto());
                table.addCell(String.valueOf(item.getProducto().getPrecio()));
                table.addCell(String.valueOf(item.getCantidad()));
                table.addCell(String.valueOf(item.calcularSubtotal()));
            }

            document.add(table);

            // Total
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: " + calcularTotal()));

            JOptionPane.showMessageDialog(null, "Factura exportada correctamente en " + rutaArchivo);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    Object resumenFactura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
