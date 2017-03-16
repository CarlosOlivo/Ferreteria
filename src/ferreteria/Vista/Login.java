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
package ferreteria.Vista;

import static ferreteria.Util.calcularMD5;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Clase encargada de la autentificación en el sistema.
 * 
 * @author Carlos Olivo
 * @version 0.1
 */
public class Login extends Application {
  
  private final String[][] users = {{"admin","21232f297a57a5a743894a0e4a801fc3","true"},{"user","ee11cbb19052e40b07aac0ca060c23ee","false"}};
  private Stage stage;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    this.stage.getIcons().add(new Image("file:resources/images/cart.png"));
    this.stage.setResizable(false);
    this.stage.setScene(login());
    this.stage.getScene().getStylesheets().add("file:resources/DarkTheme.css");
    this.stage.centerOnScreen();
    this.stage.show();
  }
  
  /**
   * Crea la escena para la autentificación en el sistema.
   * @return Escena.
   */
  public Scene login() {    
    GridPane grid = new GridPane();
    grid.getStyleClass().add("background");
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    
    stage.setTitle("Ferreteria - Login");
    Label usuario = new Label("Usuario:");
    grid.add(usuario, 0, 0);

    TextField userTF = new TextField();
    grid.add(userTF, 1, 0);

    Label pw = new Label("Contrase\u00F1a:");
    grid.add(pw, 0, 1);

    PasswordField pwPF = new PasswordField();
    grid.add(pwPF, 1, 1);
    
    Button btn = new Button("Iniciar sesi\u00F3n");
    grid.add(btn, 1, 2);
    
    Text txt = new Text("Inicie sesi\u00F3n para continuar");
    txt.setFill(Color.GREEN);
    grid.add(txt, 1, 3);
    
    btn.setOnAction((ActionEvent) -> {
      if(!"".equals(userTF.getText())) {
        if(!"".equals(pwPF.getText())) {
          for (String[] user : users) {
            if(user[0].equals(userTF.getText())) {
              if(user[1].equals(calcularMD5(pwPF.getText()))) {
                txt.setText("Cargando...");
                stage.setUserData(user[2]);
                Platform.runLater(() -> {
                  new MenuPrincipal().start(stage);
                });
                stage.close();
                return;
              }
            }
          }
          userTF.clear();
          pwPF.clear();
          txt.setText("Usuaio/Contrase\u00F1a invalido");
        } else {
          txt.setText("Introduce una contrase\u00F1a");
        }
      } else {
        txt.setText("Introduce un usuario");
      }
      txt.setFill(Color.FIREBRICK);
    });
    
    return new Scene(grid, 300, 150);
  }
}
