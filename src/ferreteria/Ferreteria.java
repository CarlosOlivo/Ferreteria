package ferreteria;

/**
 * Clase principal.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Ferreteria {
  /**
   * Metodo principal.
   * 
   * @param args Los parametros de la linea de comandos.
   */
  public static void main(String[] args) {
    Menu menuPrincipal = new Menu();
    int opcion;
    do {
      menuPrincipal.mostrarMenu();
      opcion = menuPrincipal.leerOpcion();
      menuPrincipal.realizarOperacion(opcion);
    } while (opcion != 0);
  }
}
