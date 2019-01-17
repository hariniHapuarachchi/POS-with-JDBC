package lk.ijse.fx.dep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.Inventory;
import lk.ijse.fx.dep.view.util.Order;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lk.ijse.fx.dep.controller.manageCusController.customer;

public class viewOrdersController {
    @FXML
    private AnchorPane frmViewOrder;
    @FXML
    private TextField searchText;

    ArrayList<Order> newOrderList = new ArrayList<>();
    @FXML
    private TableView<Order> viewOrderTable;

    public void initialize(){
        viewOrderTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderID"));
        viewOrderTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("day"));
        viewOrderTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cuID"));
        viewOrderTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
    }

    public void setOnKeyEnterForOrder(KeyEvent keyEvent) {
        searchText.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {

                    String oid = searchText.getText();
                    for (int j = 0; j< placeOrderController.perOrders.size(); j++){
                        if (placeOrderController.perOrders.get(j) != null && oid.equals(placeOrderController.perOrders.get(j).getOrderID())) {
                            String code = placeOrderController.perOrders.get(j).getOrderID();
                            String day = placeOrderController.perOrders.get(j).getDay();
                            String cid = placeOrderController.perOrders.get(j).getCuID();
                            String name = placeOrderController.perOrders.get(j).getcName();
                            System.out.println("Place Order");
                            System.out.println(day);
                            System.out.println(cid);
                            System.out.println(name);

                            newOrderList.add(new Order(code,day,cid,name));


                            ObservableList<Order> items = FXCollections.observableArrayList(newOrderList);
                            System.out.println("Customer Name : " + items.get(0).getcName());
                            viewOrderTable.setItems(items);

                        }
                    }

                }
            }
        });


    }

    public void viewOrderHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmViewOrder.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void generateOrderRep(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException {

        File file = new File("Reports/OrderInformaion.jasper");
        File f = new File("Reports/subOrderDetails.jasper");

        JasperReport compileReport = (JasperReport) JRLoader.loadObject(file);
        JasperReport compReport = (JasperReport) JRLoader.loadObject(f);

        Map<String,Object> params = new HashMap<>();

        params.put("subRep",compReport);



        Class.forName("com.mysql.jdbc.Driver");


        JasperPrint filledReport = JasperFillManager.fillReport(compileReport,params, DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryInfo", "root", "root"));

        JasperViewer.viewReport(filledReport,false);
    }
}
