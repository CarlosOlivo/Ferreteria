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
package ferreteria.Modelo;

import static ferreteria.Util.obtenerFechaActual;
import static ferreteria.Util.redondear;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase ventas.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Venta implements Externalizable {
  
  private final SimpleIntegerProperty folio;
  private final SimpleStringProperty fecha = new SimpleStringProperty(obtenerFechaActual());
  private final SimpleStringProperty productos;
  private final SimpleIntegerProperty cantidad;
  private final SimpleDoubleProperty subtotal;
  private final SimpleDoubleProperty iva;
  private final SimpleDoubleProperty total;
  
  /**
   * Inicializa la clase Venta.
   */
  public Venta() {
    folio = new SimpleIntegerProperty(0);
    productos = new SimpleStringProperty("");
    cantidad = new SimpleIntegerProperty(0);
    subtotal = new SimpleDoubleProperty(0);
    iva = new SimpleDoubleProperty(0);
    total = new SimpleDoubleProperty(0);
  }
  
  /**
   * Constructor sobrecargado.
   * @param folio Folio de venta.
   * @param productos Lista de productos.
   * @param cantidad Cantidad de productos.
   * @param subtotal Subtotal de la venta.
   * @param iva IVA% de la venta.
   */
  public Venta(int folio, String productos, int cantidad, double subtotal, double iva) {
    this.folio = new SimpleIntegerProperty(folio);
    this.productos = new SimpleStringProperty(productos);
    this.cantidad = new SimpleIntegerProperty(cantidad);
    this.subtotal = new SimpleDoubleProperty(subtotal);
    this.iva = new SimpleDoubleProperty(redondear(getSubtotal()*iva));
    this.total = new SimpleDoubleProperty(redondear(getSubtotal()+getIVA()));
  }

  public int getFolio() {
    return folio.get();
  }

  private void setFolio(int folio) {
    this.folio.set(folio);
  }
  
  public String getFecha() {
    return fecha.get();
  }

  private void setFecha(String fecha) {
    this.fecha.set(fecha);
  }

  public String getProductos() {
    return productos.get();
  }
  
  public void setProductos(String productos) {
    this.productos.set(productos);
  }

  public int getCantidad() {
    return cantidad.get();
  }

  public void setCantidad(int cantidad) {
    this.cantidad.set(cantidad);
  }

  public double getSubtotal() {
    return subtotal.get();
  }

  public void setSubtotal(double subtotal) {
    this.subtotal.set(subtotal);
  }
  
  public double getIVA() {
    return iva.get();
  }

  public void setIVA(double iva) {
    this.iva.set(iva);
  }
  
  public double getTotal() {
    return total.get();
  }

  public void setTotal(double total) {
    this.total.set(total);
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(getFolio());
    out.writeObject(getFecha());
    out.writeObject(getProductos());
    out.writeInt(getCantidad());
    out.writeDouble(getSubtotal());
    out.writeDouble(getIVA());
    out.writeDouble(getTotal());
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    setFolio(in.readInt());
    setFecha((String) in.readObject());
    setProductos((String) in.readObject());
    setCantidad(in.readInt());
    setSubtotal(in.readDouble());
    setIVA(in.readDouble());
    setTotal(in.readDouble());
  }
}
