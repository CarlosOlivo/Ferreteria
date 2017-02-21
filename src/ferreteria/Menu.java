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

/**
 * Clase encargada de el manejo de los menús.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Menu {
  
  private final Entrada teclado;
  private final Inventario productos;
  
  /**
   * Inicializa la clase Menú.
   */
  public Menu() {
    teclado = new Entrada();
    productos = new Inventario();
  }
  
  /**
   * Despliega el menú principal.
   */
  public void mostrarMenu() {
    System.out.println("Ferreteria");
    System.out.println("1.- Agregar producto");
    System.out.println("2.- Eliminar producto");
    System.out.println("3.- Editar producto");
    System.out.println("4.- Buscar producto");
    System.out.println("5.- Calcular costo de inventario");
    System.out.println("6.- Guardar inventario");
    System.out.println("7.- Cargar inventario");
    System.out.println("8.- Vender producto");
    System.out.println("9.- Mostrar venta");
    System.out.println("0.- Salir");
  }
  
  /**
   * Despliega el menú de busqueda.
   */
  private void mostrarMenuBusqueda() {
    System.out.println("Buscar por...");
    System.out.println("1.- Clave");
    System.out.println("2.- Nombre");
    System.out.println("3.- Descripcion");
  }
  
  /**
   * Lee una opcion desde el teclado.
   * 
   * @return Valor numerico entero.
   */
  public int leerOpcion() {
    System.out.print("Elige una opcion: ");
    return teclado.leerEntero();
  }
  
  /**
   * Realiza una operacion.
   * 
   * @param opcion Opción a realizar.
   */
  public void realizarOperacion(int opcion) {
    switch(opcion) {
      case 1:
        productos.agregarProducto();
        break;
      case 2:
        productos.eliminarProducto();
        break;
      case 3:
        productos.editarProducto();
        break;
      case 4:
        mostrarMenuBusqueda();
        productos.buscarProducto(leerOpcion());
        break;
      case 5:
        productos.calcularCostoInventario();
        break;
      case 6:
        productos.guardarInventario();
        break;
      case 7:
        productos.cargarInventario();
        break;
      case 8:
        productos.venderProducto();
        break;
      case 9:
        productos.mostrarVentaFolio();
        break;
      case 0:
        System.out.println("Gracias por usar el programa.");
        break;
      default:
        System.out.println("Valor fuera de rango.");
        break;
    }
    System.out.println();
  }
}
