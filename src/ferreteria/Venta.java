package ferreteria;

import java.io.Serializable;

/**
 * Clase ventas.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Venta implements Serializable {
  
  private int folio;
  private String productos;
  private int cantidad;
  private double subtotal;
  private double iva;
  
  /**
   * Inicializa la clase Venta.
   */
  public Venta() {
    folio = 0;
    productos = "";
    cantidad = 0;
    subtotal = 0;
    iva = 0;
  }
  
  /**
   * Constructor sobrecargado.
   * @param folio Folio de venta.
   * @param productos Lista de productos.
   * @param cantidad Cantidad de productos.
   * @param subtotal Subtotal de la venta.
   * @param iva IVA de la venta.
   */
  public Venta(int folio, String productos, int cantidad, double subtotal, double iva) {
    this.folio = folio;
    this.productos = productos;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.iva = iva;
  }

  public int getFolio() {
    return folio;
  }

  public void setFolio(int folio) {
    this.folio = folio;
  }

  public String getProductos() {
    return productos;
  }

  public void setProductos(String productos) {
    this.productos = productos;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
  }
  
  public double getIVA() {
    return iva;
  }

  public void setIVA(double iva) {
    this.iva = iva;
  }
}
