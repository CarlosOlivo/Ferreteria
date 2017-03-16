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
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase que lleva el control de Archivos.
 * 
 * @author Carlos Olivo
 * @version 0.1
 * @param <T> Tipo de elementos a guardar.
 */
public class Archivo<T> {
  
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
   * @param lista Lista a guardar.
   * @param nombre Nombre del archivo.
   */
  public void guardar(ObservableList<T> lista, String nombre) {
    try {
      fos = new FileOutputStream(nombre);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(new ArrayList<>(lista));
      oos.close();
      fos.close();
    } catch(FileNotFoundException e) {
      System.err.println("ERROR: " + e.getLocalizedMessage());
    } catch(IOException e) {
      System.err.println("ERROR: " + e.getLocalizedMessage());
    }
  }
  
  /**
   * Carga una lista desde un archivo.
   * @param archivo Nombre del archivo.
   * @return Lista del archivo.
   */
  public ObservableList<T> cargar(String archivo) {
    ObservableList<T> productos = FXCollections.observableArrayList();
    try {
      fis = new FileInputStream(archivo);
      ois = new ObjectInputStream(fis);
      List<T> lista = (List<T>) ois.readObject();
      productos = FXCollections.observableList(lista);
      ois.close();
      fis.close();
    } catch(FileNotFoundException e) {
      System.err.println("ERROR: " + e.getLocalizedMessage());
    } catch(IOException | ClassNotFoundException e) {
      System.err.println("ERROR: " + e.getLocalizedMessage());
    }
    return productos;
  }
}
