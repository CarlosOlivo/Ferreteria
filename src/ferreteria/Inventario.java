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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que lleva el control de inventario sobre los productos y sus operaciones.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Inventario {
  
  private List<Producto> productos;
  private List<Venta> ventas;
  private final Entrada teclado;
  private final Archivo archivo;
  
  /**
   * Inicializa la clase Inventario.
   */
  public Inventario() {
    productos = new ArrayList<>();
    ventas = new ArrayList<>();
    teclado = new Entrada();
    archivo = new Archivo();
  }
  
  /**
   * Agrega un producto al inventario.
   */
  public void agregarProducto() {
    System.out.print("Clave: #");
    int clave = teclado.leerEntero();
    if (existeProducto(clave)) {
      System.out.println("El producto ya existe en el inventario.");
      return;
    }
    System.out.print("Nombre: ");
    String nombre = teclado.leerCadena();
    System.out.print("Descripcion: ");
    String descripcion = teclado.leerCadena();
    System.out.print("Precio de compra: $");
    double precioCompra = teclado.leerDouble();
    System.out.print("Existencias: ");
    int existencias = teclado.leerEntero();
    System.out.print("Unidad: ");
    String unidad = teclado.leerCadena();
    productos.add(new Producto(clave, nombre, descripcion, precioCompra, existencias, unidad));
  }
  
  /**
   * Elimina un producto del inventario por su clave.
   */
  public void eliminarProducto() {
    System.out.print("Clave: #");
    int clave = teclado.leerEntero();
    Iterator<Producto> it = productos.iterator();
    while(it.hasNext()) {
      if (it.next().getClave() == clave) {
        it.remove();
        System.out.println("Producto eliminado del inventario exitosamente.");
        return;
      }
    }
    System.out.println("El producto no existe en el inventario.");
  }
  
  /**
   * Edita un producto del inventario por su clave.
   */
  public void editarProducto() {
    System.out.print("Clave: #");
    int clave = teclado.leerEntero();
    Iterator<Producto> it = productos.iterator();
    while(it.hasNext()) {
      Producto producto = it.next();
      if (producto.getClave() == clave) {
        System.out.print("Nombre [" + producto.getNombre() + "]: ");
        String nombre = teclado.leerCadena();
        if (!"".equals(nombre)) {
          producto.setNombre(nombre);
        }
        System.out.print("Descripcion [" + producto.getDescripcion()+ "]: ");
        String descripcion = teclado.leerCadena();
        if (!"".equals(descripcion)) {
          producto.setDescripcion(descripcion);
        }
        System.out.print("Precio de compra [$" + producto.getPrecioCompra()+ "]: ");
        double precioCompra = teclado.leerDouble();
        if (precioCompra != 0) {
          producto.setPrecioCompra(precioCompra);
        }
        System.out.print("Existencias [" + producto.getExistencias()+ "]: ");
        int existencias = teclado.leerEntero();
        if (existencias != 0) {
          producto.setExistencias(existencias);
        }
        System.out.print("Unidad [" + producto.getTipoUnidad()+ "]: ");
        String unidad = teclado.leerCadena();
        if (!"".equals(unidad)) {
          producto.setTipoUnidad(unidad);
        }
        return;
      }
    }
    System.out.println("El producto no existe en el inventario.");
  }
  
  /**
   * Muestra las opciones para buscar un producto en el inventario.
   * @param tipo Por clave, nombre o descripción.
   */
  public void buscarProducto(int tipo) {
    switch(tipo) {
      case 1:
        buscarProductoClave();
        break;
      case 2:
        buscarProductoNombre();
        break;
      case 3:
        buscarProductoDescripcion();
        break;
      default:
        System.out.println("Opcion invalida.");
        break;
    }
  }
  
  /**
   * Busca un producto en el inventario por su clave.
   */
  private void buscarProductoClave() {
    System.out.print("Clave: #");
    int clave = teclado.leerEntero();
    for (Producto producto : productos) {
      if (producto.getClave() == clave) {
        mostrarProducto(producto);
        return;
      }
    }
    System.out.println("No existe producto con tal clave en el inventario.");
  }
  
  /**
   * Busca un producto en el inventario por su nombre.
   */
  private void buscarProductoNombre() {
    System.out.print("Nombre: ");
    String nombre = teclado.leerCadena();
    for (Producto producto : productos) {
      if (producto.getNombre().equals(nombre)) {
        mostrarProducto(producto);
        return;
      }
    }
    System.out.println("No existe producto con tal nombre en el inventario.");
  }
  
  /**
   * Busca un producto en el inventario por su descripción.
   */
  private void buscarProductoDescripcion() {
    System.out.print("Descripcion: ");
    String descripcion = teclado.leerCadena();
    for (Producto producto : productos) {
      if (producto.getDescripcion().equals(descripcion)) {
        mostrarProducto(producto);
        return;
      }
    }
    System.out.println("No existe producto con tal descripcion en el inventario.");
  }
  
  /**
   * Muestra los datos de un producto en especifico.
   * @param producto Producto.
   */
  private void mostrarProducto(Producto producto) {
    System.out.println("Clave: #" + producto.getClave());
    System.out.println("Nombre: " + producto.getNombre());
    System.out.println("Descripcion: " + producto.getDescripcion());
    System.out.println("Precio de compra: $" + producto.getPrecioCompra());
    System.out.println("Existencias: " + producto.getExistencias());
    System.out.println("Unidad: " + producto.getTipoUnidad());
  }
  
  /**
   * Muestra los datos de una venta en especifico.
   * @param venta Venta.
   */
  private void mostrarVenta(Venta venta) {
    System.out.println("Folio: #" + venta.getFolio());
    System.out.println("Fecha: " + venta.obtenerFecha());
    System.out.print(venta.getProductos());
    System.out.println("Productos: #" + venta.getCantidad());
    System.out.println("Subtotal: $" + venta.getSubtotal());
    System.out.println("IVA 16%: $" + venta.getIVA());
    System.out.println("Total: $" + venta.calcularTotal());
  }
  
  /**
   * Comprueba si existe un producto en el inventario.
   * @param clave Clave del producto.
   * @return Verdadero si exista, falso en caso contrario.
   */
  private boolean existeProducto(int clave) {
    for (Producto producto : productos) {
      if (producto.getClave() == clave) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Comprueba si existe una venta en el inventario.
   * @param folio Folio de la venta.
   * @return Verdadero si exista, falso en caso contrario.
   */
  private boolean existeVenta(int folio) {
    for (Venta venta : ventas) {
      if (venta.getFolio() == folio) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Calcula el valor del inventario.
   */
  public void calcularCostoInventario() {
    int numProductos = 0;
    int numExistencias = 0;
    double costos = 0;
    for (Producto producto : productos) {
      numProductos++;
      numExistencias += producto.getExistencias();
      costos += producto.getPrecioCompra();
    }
    System.out.println("En el inventario se cuenta con:");
    System.out.println("***** " + numProductos + " productos diferentes *****");
    System.out.println("de los cuales se cuenta con " + numExistencias + " existencias");
    System.out.println("con un valor de $" + costos + " en total.");
  }
  
  /**
   * Guarda el inventario en un archivo.
   */
  public void guardarInventario() {
    archivo.guardar(productos, ventas);
  }
  
  /**
   * Carga el inventario de un archivo.
   */
  public void cargarInventario() {
    List[] Archivo = archivo.cargar();
    if (Archivo[0] != null && Archivo[1] != null) {
      productos = Archivo[0];
      ventas = Archivo[1];
      System.out.println("Inventario cargado correctamente.");
    } 
  }
  
  /**
   * Vende un producto del inventario.
   */
  public void venderProducto() {
    boolean existe = false;
    int opc = 0;
    int cantidad = 0;
    int subtotal = 0;
    String venta = "";
    do {
      System.out.print("Clave: #");
      int clave = teclado.leerEntero();
      Iterator<Producto> it = productos.iterator();
      while(it.hasNext()) {
        existe = false;
        Producto producto = it.next();
        if (producto.getClave() == clave) {
          existe = true;
          if(producto.venderProducto()) {
            cantidad++;
            double precio = producto.getPrecioCompra() * 1.50;
            venta += producto.getNombre() + " | " + producto.getTipoUnidad() + " | $" + precio + "\n";
            subtotal += precio;
          } else {
            System.out.println("El producto no se encuentra en existencia.");
          }
          break;
        }
      }
      if(existe == false) {
        System.out.println("El producto no existe en el inventario.");
      }
      System.out.print("Vender otro producto? 1.- Si 2.- No | ");
      opc = teclado.leerEntero();
    } while(opc == 1);
    if(cantidad >= 1) {
      ventas.add(new Venta(generarFolio(), venta, cantidad, subtotal));
    }
  }
  
  /**
   * Genera un folio para una venta.
   * @return Folio.
   */
  private int generarFolio() {
    int folio = 1;
    if(!ventas.isEmpty()) {
      folio += ventas.get(ventas.size() - 1).getFolio();
    }
    return folio;
  }
  
  /**
   * Muestra un producto por su folio.
   */
  public void mostrarVentaFolio() {
    System.out.print("Folio: #");
    int folio = teclado.leerEntero();
    for (Venta venta : ventas) {
      if(venta.getFolio() == folio) {
        System.out.println();
        mostrarVenta(venta);
        return;
      }
    }
    System.out.println("No existe venta con tal folio en el inventario.");
  }
  
  public void mostrarVentas() {
    System.out.print("Fecha [" + obtenerFechaActual() + "]: ");
    String fecha = teclado.leerFecha();
    if("".equals(fecha)) {
      fecha = obtenerFechaActual();
    }
    int numVentas = 0;
    int numProductos = 0;
    double ganancias = 0;
    for (Venta venta : ventas) {
      if(venta.obtenerFecha().equals(fecha)) {
        System.out.println();
        mostrarVenta(venta);
        numVentas++;
        numProductos += venta.getCantidad();
        ganancias += venta.calcularTotal();
      }
    }
    if(numVentas >= 1) {
      System.out.println();
      System.out.println("Fecha: " + fecha);
      System.out.println("Ventas: #" + numVentas);
      System.out.println("Productos: #" + numProductos);
      System.out.println("Ganancias: $" + ganancias);
    } else {
      System.out.println("No existe ventas con fecha " + fecha + " en el inventario.");
    }
  }
  
  /**
   * Devuelve la fecha actual.
   * @return Fecha en formato dd/MM/yyyy.
   */
  private String obtenerFechaActual() {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(new Date());
  }
}
