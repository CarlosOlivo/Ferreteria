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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase productos.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Producto implements Externalizable {
  
  private final SimpleIntegerProperty clave;
  private final SimpleStringProperty nombre;
  private final SimpleStringProperty descripcion;
  private final SimpleDoubleProperty precioCompra;
  private final SimpleIntegerProperty existencias;
  private final SimpleStringProperty tipoUnidad;
  
  /**
   * Inicializa la clase Producto.
   */
  public Producto() {
    clave = new SimpleIntegerProperty(0);
    nombre = new SimpleStringProperty("Por definir");
    descripcion = new SimpleStringProperty("Por definir");
    precioCompra = new SimpleDoubleProperty(0);
    existencias = new SimpleIntegerProperty(0);
    tipoUnidad = new SimpleStringProperty("Por definir");
  }
  
  /**
   * Constructor sobrecargado.
   * @param clave Clave del producto numerica.
   * @param nombre Nombre del producto.
   * @param descripcion Descripción del producto.
   * @param precioCompra Precio del producto.
   * @param existencias Existencias del producto.
   * @param tipoUnidad Unidad de medición del producto.
   */
  public Producto(int clave, String nombre, String descripcion, double precioCompra, int existencias, String tipoUnidad) {
    this.clave = new SimpleIntegerProperty(clave);
    this.nombre = new SimpleStringProperty(nombre);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.precioCompra = new SimpleDoubleProperty(precioCompra);
    this.existencias = new SimpleIntegerProperty(existencias);
    this.tipoUnidad = new SimpleStringProperty(tipoUnidad);
  }

  public int getClave() {
    return clave.get();
  }

  private void setClave(int clave) {
    this.clave.set(clave);
  }

  public String getNombre() {
    return nombre.get();
  }

  public void setNombre(String nombre) {
    this.nombre.set(nombre);
  }

  public String getDescripcion() {
    return descripcion.get();
  }

  public void setDescripcion(String descripcion) {
    this.descripcion.set(descripcion);
  }

  public double getPrecioCompra() {
    return precioCompra.get();
  }

  public void setPrecioCompra(double precioCompra) {
    this.precioCompra.set(precioCompra);
  }

  public int getExistencias() {
    return existencias.get();
  }

  public void setExistencias(int existencias) {
    this.existencias.set(existencias);
  }

  public String getTipoUnidad() {
    return tipoUnidad.get();
  }

  public void setTipoUnidad(String tipoUnidad) {
    this.tipoUnidad.set(tipoUnidad);
  }
  
  /**
   * Vende un producto en existencia.
   * @return Verdadero en caso exitoso, falso en caso contrario.
   */
  public boolean venderProducto() {
    if(getExistencias() >= 1) {
      setExistencias(getExistencias() - 1);
      return true;
    }
    return false;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(getClave());
    out.writeObject(getNombre());
    out.writeObject(getDescripcion());
    out.writeDouble(getPrecioCompra());
    out.writeInt(getExistencias());
    out.writeObject(getTipoUnidad());
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    setClave(in.readInt());
    setNombre((String) in.readObject());
    setDescripcion((String) in.readObject());
    setPrecioCompra(in.readDouble());
    setExistencias(in.readInt());
    setTipoUnidad((String) in.readObject());
  }
}
