/* 
 * Copyright (C) 2017 Carlos Olivo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
