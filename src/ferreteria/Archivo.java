package ferreteria;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Clase que lleva el control de Archivos.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Archivo {
  
  private FileOutputStream fos;
  private ObjectOutputStream oos;
  private FileInputStream fis;
  private ObjectInputStream ois;
  
  /**
   * Inicializa la clase Archivo.
   */
  public Archivo() {
    fos = null;
    oos = null;
    fis = null;
    ois = null;
  }
  
  /**
   * Guarda una lista de productos a un archivo.
   * @param productos Productos a guardar.
   * @param ventas Ventas a guardar.
   */
  public void guardar(List productos, List ventas) {
    try {
      fos = new FileOutputStream("Ferreteria");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(productos);
      oos.writeObject(ventas);
      oos.close();
      fos.close();
      System.out.println("Productos guardados correctamente.");
    } catch(FileNotFoundException e) {
      System.out.println("ERROR: " + e.getLocalizedMessage());
    } catch(IOException e) {
      System.out.println("ERROR: " + e.getLocalizedMessage());
    }
  }
  
  /**
   * Carga una lista de productos y ventas desde un archivo.
   * @return Productos y ventas del archivo.
   */
  public List[] cargar() {
    List[] productos = {null, null};
    try {
      fis = new FileInputStream("Ferreteria");
      ois = new ObjectInputStream(fis);
      productos[0] = (List) ois.readObject();
      productos[1] = (List) ois.readObject();
      ois.close();
      fis.close();
    } catch(FileNotFoundException e) {
      System.out.println("ERROR: " + e.getLocalizedMessage());
    } catch(IOException | ClassNotFoundException e) {
      System.out.println("ERROR: " + e.getLocalizedMessage());
    }
    return productos;
  }
}
