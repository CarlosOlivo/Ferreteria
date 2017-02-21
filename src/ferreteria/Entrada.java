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
}
