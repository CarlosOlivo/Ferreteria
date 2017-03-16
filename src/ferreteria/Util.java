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

import ferreteria.Modelo.Producto;
import ferreteria.Modelo.Venta;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 * Clase de utilidades varias
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Util {
  
  /**
   * Comprueba si una cadena es un entero valido.
   * @param str Cadena a probar.
   * @return Verdadero si es un entero, falso en caso contrario.
   */
  public static boolean esEntero(String str)  {
    try {
      Integer.parseUnsignedInt(str);
    } catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  
  /**
   * Comprueba si una cadena es un double valido.
   * @param str Cadena a probar.
   * @return Verdadero si es un double, falso en caso contrario.
   */
  public static boolean esDoble(String str)  {
    try {
      Double.parseDouble(str);
    } catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  
  /**
   * Calcula el Hash-MD5 de una cadena.
   * @param cadena Cadena a codificar.
   * @return Cadena codificada.
   */
  public static String calcularMD5(String cadena) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] array = md.digest(cadena.getBytes(Charset.forName("UTF-8")));
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < array.length; ++i) {
        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      //Nothing to do there...
    }
    return null;
  }

  /**
   * Muestra una alerta, tipo error.
   * @param msg Mensaje a mostrar.
   */
  public static void mostrarError(String msg) {
    Alert error = new Alert(Alert.AlertType.ERROR);
    error.initStyle(StageStyle.UTILITY);
    error.setHeaderText(null);
    error.setContentText(msg);
    error.show();
  }
  
  /**
   * Muestra una alerta, informativa.
   * @param msg Mensaje a mostrar.
   */
  public static void mostrarInfo(String msg) {
    Alert info = new Alert(Alert.AlertType.INFORMATION);
    info.initStyle(StageStyle.UTILITY);
    info.setHeaderText(null);
    info.setContentText(msg);
    info.show();
  }
  
  /**
   * Muestra una nota de venta.
   * @param msg Venta a mostrar.
   */
  private static void mostrarNota(String msg) {
    Alert nota = new Alert(Alert.AlertType.NONE);
    nota.initStyle(StageStyle.UTILITY);
    nota.setTitle("Nota de venta");
    nota.setHeaderText(null);
    nota.setGraphic(null);
    Label venta = new Label(msg);
    venta.setWrapText(true);
    nota.getDialogPane().setContent(venta);
    nota.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    nota.show();
  }
  
  /**
   * Muestra una alerta, con confirmación.
   * @param msg Mensaje a mostrar.
   * @return Verdadero si es OK, falso en caso contrario.
   */
  public static boolean mostrarConfirmacion(String msg) {
    Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
    conf.initStyle(StageStyle.UTILITY);
    conf.setHeaderText(null);
    conf.setContentText(msg);
    Optional<ButtonType> resultado = conf.showAndWait();
    return resultado.get() == ButtonType.OK;
  }
  
  /**
   * Comprueba si un producto es valido.
   * @param clave Clave del producto.
   * @param nombre Nonbre del producto.
   * @param descripcion Descripción del producto.
   * @param precio Precio del Producto.
   * @param existencias Existencias del producto.
   * @param unidad Unidad del producto.
   * @return Verdadero si todas las validaciones se cumplen, falso en caso contrario.
   */
  public static boolean esProducto(TextField clave, TextField nombre, TextField descripcion, TextField precio, TextField existencias, TextField unidad) {
    if(!esEntero(clave.getText())) {
      mostrarError("Introduce un n\u00FAmero entero valido.");
      clave.clear();
      return false;
    }
    if("".equals(nombre.getText())) {
      mostrarError("Introduce un nombre.");
      nombre.clear();
      return false;
    }
    if("".equals(descripcion.getText())) {
      mostrarError("Introduce una descripci\u00F3n.");
      descripcion.clear();
      return false;
    }
    if(!esDoble(precio.getText())) {
      mostrarError("Introduce un n\u00FAmero valido.");
      precio.clear();
      return false;
    }
    if(!esEntero(existencias.getText())) {
      mostrarError("Introduce un n\u00FAmero entero valido.");
      existencias.clear();
      return false;
    }
    if("".equals(unidad.getText())) {
      mostrarError("Introduce un tipo de unidad.");
      unidad.clear();
      return false;
    }
    return true;
  }
  
   /**
   * Muestra los datos de un producto en especifico.
   * @param producto Producto.
   */
  public static void verProducto(Producto producto) {
    String msg = "Clave: #" + producto.getClave();
    msg += "\nNombre: " + producto.getNombre();
    msg += "\nDescripcion: " + producto.getDescripcion();
    msg += "\nPrecio de compra: $" + producto.getPrecioCompra();
    msg += "\nExistencias: " + producto.getExistencias();
    msg += "\nUnidad: " + producto.getTipoUnidad();
    mostrarInfo(msg);
  }
  
  public static void verVenta(Venta venta) {
    String msg = " -=-=-=- Ferreteria -=-=-=-";
    msg += "\nFolio: #" + venta.getFolio() + " - Fecha: " + venta.getFecha();
    msg += "\n -=-=-=-=-=-=-=-=-=-=-=-";
    msg += "\nProducto | Cantidad | Precio";
    msg += "\n" + venta.getProductos();
    msg += " -=-=-=-=-=-=-=-=-=-=-=-";
    msg += "\n             Productos: #" + venta.getCantidad();
    msg += "\n               Subtotal: $" + venta.getSubtotal();
    msg += "\n                      IVA: $" + venta.getIVA();
    msg += "\n                    Total: $" + venta.getTotal();
    mostrarNota(msg);
  }
  
  /**
   * Devuelve la fecha actual.
   * @return Fecha en formato dd/MM/yyyy.
   */
  public static String obtenerFechaActual() {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(new Date());
  }
  
  /**
   * Redondea un double a 2 decimales.
   * @param d Double a redondear.
   * @return Double redondeado.
   */
  public static double redondear(double d) {
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.DOWN);
    return Double.parseDouble(df.format(d));
  }
  
}
