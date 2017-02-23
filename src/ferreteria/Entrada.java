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

import java.util.Scanner;

/**
 * Clase que permite capturar la entrada del sistema, por defecto, el teclado.
 *
 * @author Carlos Olivo
 * @version 0.1
 */
public class Entrada {

  private final Scanner sc;

  /**
   * Inicializa la clase Entrada.
   */
  public Entrada() {
    sc = new Scanner(System.in);
  }

  /**
   * Lee un numero entero desde el teclado.
   * 
   * @return Valor numerico tipo entero.
   */
  public int leerEntero() {
    int entero = sc.nextInt();
    sc.nextLine();
    return entero;
  }

  /**
   * Lee un numero double desde el teclado
   * 
   * @return Valor numerico tipo double.
   */
  public double leerDouble() {
    return sc.nextDouble();
  }
  
  /**
   * Lee una cadena de texto desde el teclado
   * 
   * @return Cadena de texto tipo String.
   */
  public String leerCadena() {
    return sc.nextLine();
  }
  
  /**
   * Lee una fecha desde el teclado
   * 
   * @return Fecha tipo String.
   */
  public String leerFecha() {
    // TO-DO: Validar fecha
    return sc.nextLine();
  }
}
