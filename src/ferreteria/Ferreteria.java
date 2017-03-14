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

import ferreteria.Vista.Login;
import javafx.application.Application;

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
    Application.launch(Login.class, args);
  }
}
