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

import ferreteria.Archivo;
import ferreteria.Modelo.Producto;
import static ferreteria.Util.esProducto;
import static ferreteria.Util.mostrarConfirmacion;
import static ferreteria.Util.mostrarError;
import static ferreteria.Util.mostrarInfo;
import ferreteria.Modelo.Venta;
import static ferreteria.Util.esEntero;
import static ferreteria.Util.redondear;
import java.util.Iterator;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Carlos Olivo
 */
public class MenuPrincipal extends Application {
  
  private Stage stage;
  private BorderPane bp;
  private TableView<Producto> tablaProductos;
  private TableView<Venta> tablaVentas;
  private ObservableList<Producto> productos;
  private ObservableList<Venta> ventas;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    cargar();
  }
  
  private void cargar() {
    productos = new Archivo<Producto>().cargar("Productos");
    ventas = new Archivo<Venta>().cargar("Ventas");
    stage.setTitle("Ferreteria - Productos");
    stage.getIcons().add(new Image("file:resources/images/cart.png"));
    stage.setResizable(false);
    stage.setScene(menu());
    //stage.getScene().getStylesheets().add("file:resources/DarkTheme.css");
    stage.centerOnScreen();
    stage.setOnCloseRequest((EventHandler) -> {
      guardar();
    });
    stage.show();
  }
  
  private void guardar() {
    if(productos != null) {
      new Archivo<Producto>().guardar(productos, "Productos");
    }
    if(ventas != null) {
      new Archivo<Venta>().guardar(ventas, "Ventas");
    }
  }
  
  public void crearTablaProductos() {
    tablaProductos = new TableView<>(productos);
    tablaProductos.setPlaceholder(new Text("Sin productos..."));
    
    TableColumn clave = new TableColumn("Clave");
    clave.setCellValueFactory(new PropertyValueFactory("clave"));
    tablaProductos.getColumns().add(clave);
    
    TableColumn nombre = new TableColumn("Nombre");
    nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    tablaProductos.getColumns().add(nombre);
    
    TableColumn descripcion = new TableColumn("Descripci\u00F3n");
    descripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
    tablaProductos.getColumns().add(descripcion);
    
    TableColumn precio = new TableColumn("Precio");
    precio.setCellValueFactory(new PropertyValueFactory("precioCompra"));
    tablaProductos.getColumns().add(precio);
    
    TableColumn existencias = new TableColumn("Existencias");
    existencias.setCellValueFactory(new PropertyValueFactory("existencias"));
    tablaProductos.getColumns().add(existencias);
    
    TableColumn unidad = new TableColumn("Unidad");
    unidad.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
    tablaProductos.getColumns().add(unidad);
  }
  
  public void crearTablaVentas() {
    tablaVentas = new TableView<>(ventas);
    tablaVentas.setPlaceholder(new Text("Sin ventas..."));
    
    TableColumn folio = new TableColumn("Folio");
    folio.setCellValueFactory(new PropertyValueFactory("folio"));
    tablaVentas.getColumns().add(folio);
    
    TableColumn fecha = new TableColumn("Fecha");
    fecha.setCellValueFactory(new PropertyValueFactory("fecha"));
    tablaVentas.getColumns().add(fecha);
    
    TableColumn producto = new TableColumn("Productos");
    producto.setCellValueFactory(new PropertyValueFactory("productos"));
    tablaVentas.getColumns().add(producto);
    
    TableColumn cantidad = new TableColumn("Cantidad");
    cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
    tablaVentas.getColumns().add(cantidad);
    
    //TableColumn iva = new TableColumn("IVA");
    //iva.setCellValueFactory(new PropertyValueFactory("iva"));
    //tablaVentas.getColumns().add(iva);
    
    TableColumn subtotal = new TableColumn("Subtotal");
    subtotal.setCellValueFactory(new PropertyValueFactory("subtotal"));
    tablaVentas.getColumns().add(subtotal);
  }
  
  public Scene menu() {
    bp = new BorderPane();
    bp.setLeft(opciones());
    
    crearTablaProductos();
    crearTablaVentas();
    
    bp.setCenter(tablaProductos);
    
    return new Scene(bp, 1000, 500);
  }
  
  public VBox opciones() {
    VBox vb = new VBox();
    vb.setPadding(new Insets(10));
    
    ComboBox<String> opciones = new ComboBox<>();
    opciones.getItems().addAll("Productos", "Ventas");
    opciones.setValue("Productos");
    opciones.setOnAction((ActionEvent) -> {
      switch(opciones.getSelectionModel().getSelectedItem()) {
        case "Productos":
          stage.setTitle("Ferreteria - Productos");
          bp.setCenter(tablaProductos);
          break;
        case "Ventas":
          stage.setTitle("Ferreteria - Ventas");
          bp.setCenter(tablaVentas);
          break;
        default:
          mostrarError("Opci\u00F3n invalida");
      }
      bp.setRight(null);
    });
    vb.getChildren().add(opciones);
    
    Text titulo1 = new Text("Productos");
    titulo1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo1);
    
    Hyperlink agregar = new Hyperlink("Agregar");
    agregar.setOnAction((ActionEvent) -> {
      bp.setRight(agregarProducto());
    });
    vb.getChildren().add(agregar);
    
    Hyperlink editar = new Hyperlink("Editar");
    editar.setOnAction((ActionEvent) -> {
      editarProducto();
    });
    vb.getChildren().add(editar);
    
    Hyperlink eliminar = new Hyperlink("Eliminar");
    eliminar.setOnAction((ActionEvent) -> {
      eliminarProducto();
    });
    vb.getChildren().add(eliminar);
    
    Hyperlink inventario = new Hyperlink("Inventario");
    inventario.setOnAction((ActionEvent) -> {
      calcularInventario();
    });
    vb.getChildren().add(inventario);
    
    Hyperlink vender = new Hyperlink("Vender");
    vender.setOnAction((ActionEvent) -> {
      bp.getLeft().setDisable(true);
      bp.setRight(venderProducto());
    });
    vb.getChildren().add(vender);
    
    Text titulo2 = new Text("Ventas");
    titulo2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo2);
    
    return vb;
  }
  
  public VBox agregarProducto() {
    VBox vb = new VBox();
    vb.setPadding(new Insets(5));
    vb.setSpacing(5);
    vb.setAlignment(Pos.TOP_CENTER);
    
    Text titulo = new Text("Agregar producto");
    titulo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo);
    
    TextField clave = new TextField();
    clave.setPromptText("Clave");
    vb.getChildren().add(clave);
    
    TextField nombre = new TextField();
    nombre.setPromptText("Nombre");
    vb.getChildren().add(nombre);
    
    TextField descripcion = new TextField();
    descripcion.setPromptText("Descripci\u00F3n");
    vb.getChildren().add(descripcion);
    
    TextField precio = new TextField();
    precio.setPromptText("Precio");
    vb.getChildren().add(precio);
    
    TextField existencias = new TextField();
    existencias.setPromptText("Existencias");
    vb.getChildren().add(existencias);
    
    TextField unidad = new TextField();
    unidad.setPromptText("Unidad");
    vb.getChildren().add(unidad);
    
    HBox hb = new HBox();
    hb.setSpacing(5);
    hb.setAlignment(Pos.CENTER);
    
    Button agregar = new Button("Agregar");
    agregar.setOnAction((ActionEvent) -> {
      if(!esProducto(clave, nombre, descripcion, precio, existencias, unidad)) {
        return;
      }
      if(existeProducto(Integer.parseInt(clave.getText()))) {
        mostrarError("Un producto con esta clave ya existe en el sistema.");
        clave.clear();
        return;
      }
      productos.add(new Producto(
          Integer.parseInt(clave.getText()),
          nombre.getText(),
          descripcion.getText(),
          Double.parseDouble(precio.getText()),
          Integer.parseInt(existencias.getText()),
          unidad.getText()
      ));
      clave.clear();
      nombre.clear();
      descripcion.clear();
      precio.clear();
      existencias.clear();
      unidad.clear();
    });
    hb.getChildren().add(agregar);
    
    Button cerrar = new Button("Cerrar");
    cerrar.setOnAction((ActionEvent) -> {
      bp.setRight(null);
    });
    hb.getChildren().add(cerrar);
    
    vb.getChildren().add(hb);
    return vb;
  }
  
  public void editarProducto() {
    Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
    if(producto == null) {
      mostrarError("Selecciona un producto para editar.");
      return;
    }
    EditarProducto eP = new EditarProducto();
    if(eP.cargar(stage, producto)) {
      tablaProductos.refresh();
    }
  }
  
  public void eliminarProducto() {
    int seleccion = tablaProductos.getSelectionModel().getSelectedIndex();
    if(seleccion >= 0) {
      if(mostrarConfirmacion("Estas seguro de eliminar el producto con el ID #" + tablaProductos.getItems().get(seleccion).getClave() + "?")) {
        tablaProductos.getItems().remove(seleccion);
      }
    } else {
      mostrarError("Selecciona un producto para elmininar.");
    }
  }
  
  public VBox venderProducto() {
    VBox vb = new VBox();
    vb.setPadding(new Insets(5));
    vb.setSpacing(5);
    vb.setAlignment(Pos.TOP_CENTER);
    
    Text titulo = new Text("Vender producto");
    titulo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo);
    
    TextField clave = new TextField();
    clave.setPromptText("Clave");
    vb.getChildren().add(clave);
    
    HBox hb = new HBox();
    hb.setSpacing(5);
    hb.setAlignment(Pos.CENTER);
    
    Button agregar = new Button("Agregar");
    hb.getChildren().add(agregar);
    
    Button listo = new Button("Listo");
    listo.setOnAction((ActionEvent) -> {
      if(cantidad >= 1) {
        ventas.add(new Venta(generarFolio(), venta, cantidad, redondear(subtotal)));
      }
      existe = false;
      cantidad = 0;
      subtotal = 0;
      venta = "";
      bp.getLeft().setDisable(false);
      bp.setRight(null);
      tablaProductos.refresh();
    });
    hb.getChildren().add(listo);
    
    vb.getChildren().add(hb);
    
    Label nota = new Label();
    vb.getChildren().add(nota);
    
    agregar.setOnAction((ActionEvent) -> {
      if(!esEntero(clave.getText())) {
        mostrarError("Introduce un n\u00FAmero entero valido.");
        return;
      }
      nota.setText(vendeProducto(Integer.parseInt(clave.getText())));
    });
    
    return vb;
  }
  
  protected boolean existe = false;
  protected int cantidad = 0;
  protected double subtotal = 0;
  protected String venta = "";
  /**
   * Vende productos del inventario.
   * @param clave Clave del producto
   * @return Nota de venta
   */
  public String vendeProducto(int clave) {
    Iterator<Producto> it = productos.iterator();
    while(it.hasNext()) {
      existe = false;
      Producto producto = it.next();
      if (producto.getClave() == clave) {
        existe = true;
        if(producto.venderProducto()) {
          cantidad++;
          double precio = producto.getPrecioCompra() * 1.50;
          precio = redondear(precio);
          venta += producto.getNombre() + " | " + producto.getTipoUnidad() + " | $" + precio + "\n";
          subtotal += precio;
        } else {
          mostrarError("El producto no se encuentra en existencia.");
        }
        break;
      }
    }
    if(existe == false) {
      mostrarError("El producto no existe en el inventario.");
    }
    return venta;
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
   * Calcula el valor del inventario.
   */
  public void calcularInventario() {
    int numProductos = 0;
    int numExistencias = 0;
    double costos = 0;
    for (Producto producto : productos) {
      numProductos++;
      numExistencias += producto.getExistencias();
      costos += producto.getPrecioCompra();
    }
    String msg = "";
    msg += "Productos: " + numProductos + "\n";
    msg += "Existencias: " + numExistencias + "\n";
    msg += "Valor: $" + costos;
    mostrarInfo(msg);
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
  
  public static void main(String[] args) {
    launch(args);
  }
  
}
