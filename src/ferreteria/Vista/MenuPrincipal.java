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
import static ferreteria.Util.verProducto;
import static ferreteria.Util.mostrarConfirmacion;
import static ferreteria.Util.mostrarError;
import static ferreteria.Util.mostrarInfo;
import ferreteria.Modelo.Venta;
import static ferreteria.Util.esEntero;
import static ferreteria.Util.redondear;
import static ferreteria.Util.verVenta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
 * Clase grafica principal.
 * 
 * @author Carlos Olivo
 * @version 0.1
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
    validar();
  }
  
  /**
   * Valida que no sea un usuario inexistente.
   */
  private void validar() {
    if(stage.getUserData() == null) {
      mostrarError("Autentificaci\u00F3n requerida");
      stage.close();
    } else {
      cargar();
    }
  }
  
  /**
   * Prepara al escenario para iniciar.
   */
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
      if(esAdmin()) {
        guardar();
      }
    });
    stage.show();
  }
  
  /**
   * Guarda los productos y las ventas.
   */
  private void guardar() {
    if(productos != null) {
      new Archivo<Producto>().guardar(productos, "Productos");
    }
    if(ventas != null) {
      new Archivo<Venta>().guardar(ventas, "Ventas");
    }
  }
  
  /**
   * Crea la tabla productos.
   */
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
    
    tablaProductos.setRowFactory((TableView<Producto> tableView) -> {
      final TableRow<Producto> row = new TableRow<>();
      final ContextMenu contextMenu = new ContextMenu();
      
      final MenuItem mostrar = new MenuItem("Mostrar");
      mostrar.setOnAction((ActionEvent) -> {
        verProducto(row.getItem());
      });
      contextMenu.getItems().add(mostrar);
      
      final MenuItem editar = new MenuItem("Editar");
      editar.setOnAction((ActionEvent) -> {
        editarProducto(row.getItem());
      });
      if(esAdmin()) {
        contextMenu.getItems().add(editar);
      }
      
      final MenuItem eliminar = new MenuItem("Eliminar");
      eliminar.setOnAction((ActionEvent) -> {
        eliminarProducto(row.getItem());
      });
      if(esAdmin()) {
        contextMenu.getItems().add(eliminar);
      }
      
      row.contextMenuProperty().bind(
          Bindings.when(row.emptyProperty())
              .then((ContextMenu)null)
              .otherwise(contextMenu)
      );
      return row ;
    });  
  }
  
  /**
   * Crea la tabla ventas.
   */
  private void crearTablaVentas() {
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
    
    TableColumn subtotal = new TableColumn("Subtotal");
    subtotal.setCellValueFactory(new PropertyValueFactory("subtotal"));
    tablaVentas.getColumns().add(subtotal);
    
    TableColumn iva = new TableColumn("IVA");
    iva.setCellValueFactory(new PropertyValueFactory("IVA"));
    tablaVentas.getColumns().add(iva);
    
    TableColumn total = new TableColumn("Total");
    total.setCellValueFactory(new PropertyValueFactory("total"));
    tablaVentas.getColumns().add(total);
    
    tablaVentas.setRowFactory((TableView<Venta> tableView) -> {
      final TableRow<Venta> row = new TableRow<>();
      final ContextMenu contextMenu = new ContextMenu();
      
      final MenuItem mostrar = new MenuItem("Mostrar");
      mostrar.setOnAction((ActionEvent) -> {
        verVenta(row.getItem());
      });
      contextMenu.getItems().add(mostrar);
      
      row.contextMenuProperty().bind(
          Bindings.when(row.emptyProperty())
              .then((ContextMenu)null)
              .otherwise(contextMenu)
      );
      return row ;
    });  
  }
  
  /**
   * Crea la escena principal.
   * @return Escena.
   */
  private Scene menu() {
    bp = new BorderPane();
    bp.setLeft(opciones());
    
    crearTablaProductos();
    crearTablaVentas();
    
    bp.setCenter(tablaProductos);
    
    return new Scene(bp, 1000, 500);
  }
  
  /**
   * Panel de opciones.
   * @return Panel.
   */
  private VBox opciones() {
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
          bp.setBottom(null);
          break;
        case "Ventas":
          stage.setTitle("Ferreteria - Ventas");
          bp.setCenter(tablaVentas);
          bp.setBottom(filtrarFecha());
          break;
        default:
          mostrarError("Opci\u00F3n invalida");
      }
      bp.setRight(null);
    });
    vb.getChildren().add(opciones);
    
    Separator separador = new Separator();
    separador.setPadding(new Insets(5));
    vb.getChildren().add(separador);
    
    Text titulo1 = new Text("Productos");
    titulo1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo1);
    
    Hyperlink agregar = new Hyperlink("Agregar");
    agregar.setOnAction((ActionEvent) -> {
      bp.setRight(agregarProducto());
    });
    if(esAdmin()) {
      vb.getChildren().add(agregar);
    }
   
    Hyperlink inventario = new Hyperlink("Inventario");
    inventario.setOnAction((ActionEvent) -> {
      calcularInventario();
    });
    vb.getChildren().add(inventario);
    
    Hyperlink buscar = new Hyperlink("Buscar");
    buscar.setOnAction((ActionEvent) -> {
      bp.setRight(buscarProducto());
    });
    vb.getChildren().add(buscar);
    
    Hyperlink vender = new Hyperlink("Vender");
    vender.setOnAction((ActionEvent) -> {
      bp.getLeft().setDisable(true);
      bp.setRight(venderProducto());
    });
    if(esAdmin()) {
      vb.getChildren().add(vender);
    }
    return vb;
  }
  
  /**
   * Panel para agregar un producto.
   * @return Panel.
   */
  private VBox agregarProducto() {
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
  
  private void editarProducto(Producto producto) {
    EditarProducto eP = new EditarProducto();
    if(eP.cargar(stage, producto)) {
      tablaProductos.refresh();
    }
  }
  
  /**
   * Panel para buscar un producto.
   * @return Panel.
   */
  private VBox buscarProducto() {
    VBox vb = new VBox();
    vb.setPadding(new Insets(5));
    vb.setSpacing(5);
    vb.setAlignment(Pos.TOP_CENTER);
    
    Text titulo = new Text("Buscar producto");
    titulo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vb.getChildren().add(titulo);
    
    ComboBox<String> opciones = new ComboBox<>();
    opciones.getItems().addAll("Clave", "Nombre", "Descripcion");
    opciones.setValue("Clave");
    vb.getChildren().add(opciones);
    
    TextField campo = new TextField();
    vb.getChildren().add(campo);
    
    HBox hb = new HBox();
    hb.setSpacing(5);
    hb.setAlignment(Pos.CENTER);
    
    Button agregar = new Button("Mostrar");
    agregar.setOnAction((ActionEvent) -> {
      if("".equals(campo.getText())) {
        mostrarInfo("Introduce un dato para continuar.");
        return;
      }
      switch(opciones.getSelectionModel().getSelectedItem()) {
        case "Clave":
          if(!esEntero(campo.getText())) {
            mostrarError("Introduce un n\u00FAmero entero valido.");
            return;
          }
          buscarProductoClave(Integer.parseInt(campo.getText()));
          break;
        case "Nombre":
          buscarProductoNombre(campo.getText());
          break;
        case "Descripcion":
          buscarProductoDescripcion(campo.getText());
          break;
        default:
          mostrarError("Opci\u00F3n invalida");
          break;
      }
      campo.clear();
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
  
  /**
   * Elimina un producto del sistema.
   * @param producto Producto a eliminar.
   */
  private void eliminarProducto(Producto producto) {
    if(mostrarConfirmacion("Estas seguro de eliminar el producto con el ID #" + producto.getClave() + "?")) {
      tablaProductos.getItems().remove(producto);
    }
  }
  
  /**
   * Panel para vender un producto.
   * @return Panel.
   */
  private VBox venderProducto() {
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
      if(cantidadP >= 1) {
        ventas.add(new Venta(generarFolio(), ventaP, cantidadP, redondear(subtotalP), 0.16));
      }
      existeP = false;
      cantidadP = 0;
      subtotalP = 0;
      ventaP = "";
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
  
  protected boolean existeP = false;
  protected int cantidadP = 0;
  protected double subtotalP = 0;
  protected String ventaP = "";
  /**
   * Vende productos del inventario.
   * @param clave Clave del producto
   * @return Nota de venta
   */
  private String vendeProducto(int clave) {
    Iterator<Producto> it = productos.iterator();
    while(it.hasNext()) {
      existeP = false;
      Producto producto = it.next();
      if (producto.getClave() == clave) {
        existeP = true;
        if(producto.venderProducto()) {
          cantidadP++;
          double precio = producto.getPrecioCompra() * 1.50;
          precio = redondear(precio);
          ventaP += producto.getNombre() + " | " + producto.getTipoUnidad() + " | $" + precio + "\n";
          subtotalP += precio;
        } else {
          mostrarError("El producto no se encuentra en existencia.");
        }
        break;
      }
    }
    if(existeP == false) {
      mostrarError("El producto no existe en el inventario.");
    }
    return ventaP;
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
  private void calcularInventario() {
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
  
  /**
   * Comprueba si el usuario tiene permisos administrativos.
   * @return Verdadero si es administrador, falso en caso contrario.
   */
  private boolean esAdmin() {
    return stage.getUserData() == "true";
  }
  
   /**
   * Busca un producto en el inventario por su clave.
   * @param clave Clave del producto.
   */
  private void buscarProductoClave(int clave) {
    for (Producto producto : productos) {
      if (producto.getClave() == clave) {
        verProducto(producto);
        return;
      }
    }
    mostrarError("No existe producto con tal clave en el inventario.");
  }
  
  /**
   * Busca un producto en el inventario por su nombre.
   * @param nombre Nombre del producto.
   */
  private void buscarProductoNombre(String nombre) {
    for (Producto producto : productos) {
      if (producto.getNombre().equals(nombre)) {
        verProducto(producto);
        return;
      }
    }
    mostrarError("No existe producto con tal nombre en el inventario.");
  }
  
  /**
   * Busca un producto en el inventario por su descripción.
   * @param descripcion Descripción del producto.
   */
  private void buscarProductoDescripcion(String descripcion) {
    for (Producto producto : productos) {
      if (producto.getDescripcion().equals(descripcion)) {
        verProducto(producto);
        return;
      }
    }
    mostrarError("No existe producto con tal descripcion en el inventario.");
  }
  
  /**
   * Panel para filtrar las ventas por fecha.
   * @return Panel.
   */
  private HBox filtrarFecha() {
    HBox hb = new HBox();
    hb.setSpacing(5);
    hb.setAlignment(Pos.CENTER_LEFT);
    
    FilteredList<Venta> filtro = new FilteredList<>(ventas);
    
    Label titulo = new Label("Filtrar por fecha ");
    hb.getChildren().add(titulo);
    
    ComboBox<String> opciones = new ComboBox<>();
    opciones.getItems().addAll("=", ">", "<");
    opciones.setValue("=");
    hb.getChildren().add(opciones);
    
    DatePicker fecha = new DatePicker();
    fecha.setEditable(false);
    fecha.setValue(LocalDate.now());
    hb.getChildren().add(fecha);
    
    Button filtrar = new Button("Filtrar");
    hb.getChildren().add(filtrar);
    
    Button restablecer = new Button("Restablecer");
    restablecer.setOnAction((ActionEvent) -> {
      opciones.setValue("=");
      fecha.setValue(LocalDate.now());
      restablecer.setVisible(false);
      tablaVentas.setItems(ventas);
    });
    restablecer.setVisible(false);
    hb.getChildren().add(restablecer);
    
    filtrar.setOnAction((ActionEvent) -> {
      filtro.setPredicate(venta -> {
        LocalDate fechaV = LocalDate.parse(venta.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        switch(opciones.getSelectionModel().getSelectedItem()) {
          case "=":
            return fechaV.isEqual(fecha.getValue());
          case ">":
            return fechaV.isAfter(fecha.getValue());
          case "<":
            return fechaV.isBefore(fecha.getValue());
        default:
          mostrarError("Opci\u00F3n invalida");
      }
        return true;
      });
      tablaVentas.setItems(filtro);
      restablecer.setVisible(true);
    });
    
    return hb;
  }
  
}
