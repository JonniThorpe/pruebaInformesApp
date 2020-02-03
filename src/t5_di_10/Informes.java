package t5_di_10;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


class MenuActionListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Selected: " + e.getActionCommand());

  }
}


public class Informes extends Application {

    public static Connection conexion = null;
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 250, Color.WHITE);
        primaryStage.setTitle("Generador Informes");

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        Menu infMenu = new Menu("Informes");
        MenuItem informe1 = new MenuItem("Listado Facturas");
        MenuItem informe2 = new MenuItem("Ventas Totales");
        MenuItem informe3 = new MenuItem("Facturas por Cliente");
        MenuItem informe4 = new MenuItem("Subinforme Listado Facturas");
        
        conectaBD();
        
        informe1.setOnAction(event -> {
        
            generaInforme1();
        
        });
        
        informe2.setOnAction(event -> {
        
            generaInforme2();
        });
        
        informe3.setOnAction(event -> {
            
            JFrame frame = new JFrame("Parametro");
            String paramS = JOptionPane.showInputDialog(frame, "Introduzca el parametro");
            int param = Integer.parseInt(paramS);
        
            generaInforme3(param);
                
        });
        
        informe4.setOnAction(event -> {
        
            generaInforme4();
        });
        

        infMenu.getItems().addAll(informe1, informe2, informe3, informe4);

        menuBar.getMenus().addAll(infMenu);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void conectaBD(){
        
        String baseDatos = "jdbc:hsqldb:hsql://localhost:9001/xdb";
        String usuario = "sa";

        String clave = "";

        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            conexion = DriverManager.getConnection(baseDatos, usuario, clave);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Fallo al cargar JDBC");
            System.exit(1);
        } catch (SQLException sqle) {
            System.err.println("No se pudo conectar a BD");
            System.exit(1);
        } catch (java.lang.InstantiationException sqlex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        } catch (Exception ex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        }
        
    }
    
    public void generaInforme1() {
        try { 
            JasperReport jr = (JasperReport) JRLoader.loadObject(Informes.
                    class.getResource("facturas.jasper"));
         Map parametros = new HashMap();
         JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
         JasperViewer.viewReport(jp,false); 
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper"); 
            JOptionPane.showMessageDialog(null, ex); 
         } 
    }
    public void generaInforme2() {
        try { 
            JasperReport jr = (JasperReport) JRLoader.loadObject(Informes.
                    class.getResource("ventasTotales.jasper"));
         //Map de parámetros
         Map parametros = new HashMap();
         JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
         JasperViewer.viewReport(jp,false); 
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper"); 
            JOptionPane.showMessageDialog(null, ex); 
         } 
    }
    public void generaInforme3(int param) {
        try { 
            JasperReport jr = (JasperReport) JRLoader.loadObject(Informes.
                    class.getResource("facturasporCliente.jasper"));
         //Map de parámetros
         Map parametros = new HashMap();
         parametros.put("FacturasCliente", param);
         JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
         JasperViewer.viewReport(jp,false); 
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper"); 
            JOptionPane.showMessageDialog(null, ex); 
         } 
    }
    public void generaInforme4() {
        try { 
            JasperReport jr = (JasperReport) JRLoader.loadObject(Informes.
                    class.getResource("facturasconSubinformes.jasper"));
         Map parametros = new HashMap();
         JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
         JasperViewer.viewReport(jp,false); 
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper"); 
            JOptionPane.showMessageDialog(null, ex); 
         } 
    }

    public static void main(String[] args) {
        launch(args);
    }

}
