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
