package ru.vmk.shahova.databaseUi.ui.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Controller implements Initializable {
    private Label label;
    @FXML
    private TextField tfKod;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfInit;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField minPrice;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnExecute;

    //language=SQL
    private static final String INSERT_CATALOG_QUERY = "INSERT INTO catalog_details (kod_details, type_details, name_details, init, price_details) VALUES ('%s','%s','%s','%s','%s')";
    //    language=SQL
    private static final String UPDATE_CATALOG_QUERY = "UPDATE catalog_details SET type_details = '%s', name_details = '%s',init='%s',price_details='%s' WHERE kod_details = '%s'";
    //language=SQL
    private static final String DELETE_CATALOG_QUERY = "DELETE FROM catalog_details WHERE kod_details = '%s'";

    @FXML
    private TableView<Catalog> catalogDetails;
    @FXML
    private TableColumn<Catalog, Integer> kod_details;
    @FXML
    private TableColumn<Catalog, String> type_details;
    @FXML
    private TableColumn<Catalog, String> name_details;
    @FXML
    private TableColumn<Catalog, String> init;
    @FXML
    private TableColumn<Catalog, Integer> price_details;

    @FXML
    private TableView<Contract> contract;
    @FXML
    private TableColumn<Contract, Integer> number_contract;
    @FXML
    private TableColumn<Contract, Integer> kod_supplier;
    @FXML
    private TableColumn<Contract, Date> data_contract;

    @FXML
    private TableView<Supply> supply;
    @FXML
    private TableColumn<Supply, Integer> number_contract1;
    @FXML
    private TableColumn<Supply, Integer> kod_details1;
    @FXML
    private TableColumn<Supply, String> init1;
    @FXML
    private TableColumn<Supply, Date> start_date;
    @FXML
    private TableColumn<Supply, Date> end_date;
    @FXML
    private TableColumn<Supply, Integer> plan;
    @FXML
    private TableColumn<Supply, Integer> price;


    private Queue<String> statements = new ArrayDeque<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catalogDetails.setItems(FXCollections.observableList(getCatalog()));
        kod_details.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        type_details.setCellValueFactory(new PropertyValueFactory<>("type"));
        name_details.setCellValueFactory(new PropertyValueFactory<>("name"));
        init.setCellValueFactory(new PropertyValueFactory<>("init"));
        price_details.setCellValueFactory(new PropertyValueFactory<>("price"));

        catalogDetails.setEditable(true);
        kod_details.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        type_details.setCellFactory(TextFieldTableCell.forTableColumn());
        name_details.setCellFactory(TextFieldTableCell.forTableColumn());
        init.setCellFactory(TextFieldTableCell.forTableColumn());
        price_details.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        contract.setItems(FXCollections.observableList(getContract()));
        number_contract.setCellValueFactory(new PropertyValueFactory<>("number"));
        kod_supplier.setCellValueFactory(new PropertyValueFactory<>("detailsCode"));
        data_contract.setCellValueFactory(new PropertyValueFactory<>("date"));
        contract.setEditable(true);
        number_contract.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        kod_supplier.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        data_contract.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        supply.setItems(FXCollections.observableList(getSupply()));
        number_contract1.setCellValueFactory(new PropertyValueFactory<>("number"));
        kod_details1.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        init1.setCellValueFactory(new PropertyValueFactory<>("init"));
        start_date.setCellValueFactory(new PropertyValueFactory<>("start"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end"));
        plan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        supply.setEditable(true);

        number_contract1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        kod_details1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        init1.setCellFactory(TextFieldTableCell.forTableColumn());
        start_date.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        end_date.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        plan.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        btnInsert.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> insertRecord());
        btnUpdate.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> updateRecord());
        btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteRecord());
        btnExecute.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> executeProcedure());
    }

    private static final String url = "jdbc:postgresql://127.0.0.1:5433/postgres";
    private static final String user = "postgres";
    private static final String pass = "postgres";

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            return null;
        }

    }

    public List<Catalog> getCatalog() {
        List<Catalog> catalogList = new ArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM catalog_details";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Catalog catalog;
            while (rs.next()) {
                catalog = new Catalog(
                        rs.getInt("kod_details"),
                        rs.getString("type_details"),
                        rs.getString("name_details"),
                        rs.getString("init"),
                        rs.getInt("price_details"));
                catalogList.add(catalog);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return catalogList;
    }

    public List<Contract> getContract() {
        List<Contract> contractList = new ArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM contract_supplier";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("number_contract"),
                        rs.getInt("kod_supplier"),
                        rs.getDate("data_contract"));
                contractList.add(contract);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contractList;
    }


    public List<Supply> getSupply() {
        List<Supply> supplyList = new ArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM supply_details";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                Supply supply = new Supply(
                        rs.getInt("number_contract"),
                        rs.getInt("kod_details"),
                        rs.getString("init"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("plan"),
                        rs.getInt("unit_price")
                );
                supplyList.add(supply);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return supplyList;
    }

    private void executeProcedure() {
        String result = "";
        try {
            Integer minPrice = Integer.parseInt(this.minPrice.getText());
            String query = "SELECT m('" + minPrice + "');";
            Connection conn = getConnection();
            Statement st;
            st = conn.createStatement();
            st.execute(query);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                result += "," + rs.getString(1);
            }
            this.minPrice.setText(result);
        }
        catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    private void insertRecord() {
        String query = String.format(INSERT_CATALOG_QUERY, tfKod.getText(), tfType.getText(), tfName.getText(), tfInit.getText(), tfPrice.getText());
        try {
            executeQuery(query);
            catalogDetails.getItems().add(new Catalog(
                    Integer.parseInt(tfKod.getText()),
                    tfType.getText(),
                    tfName.getText(),
                    tfInit.getText(),
                    Integer.parseInt(tfPrice.getText()))
            );
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private void updateRecord() {
        String query = String.format(UPDATE_CATALOG_QUERY, tfType.getText(), tfName.getText(), tfInit.getText(), tfPrice.getText(), tfKod.getText());
        try {
            executeQuery(query);
            Optional<Catalog> catalogOptional = catalogDetails.getItems()
                    .filtered(item -> item.getDetailCode() == Integer.parseInt(tfKod.getText()))
                    .stream()
                    .findFirst();

            if (catalogOptional.isPresent()) {
                int index = catalogDetails.getItems().indexOf(catalogOptional.get());
                Catalog catalog = catalogOptional.get();
                catalog.setInit(tfInit.getText());
                catalog.setType(tfType.getText());
                catalog.setName(tfName.getText());
                catalog.setPrice(Integer.parseInt(tfPrice.getText()));
                catalogDetails.getItems().set(index, catalog);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void deleteRecord() {
       Catalog catalog = catalogDetails.getSelectionModel().getSelectedItem();
       if(catalog != null) {
           String query = String.format(DELETE_CATALOG_QUERY,catalog.getDetailCode());
            try{
                executeQuery(query);
                catalogDetails.getItems()
                        .removeIf(item -> item.getDetailCode() == catalog.getDetailCode());
            }catch(SQLException e){
                e.printStackTrace();

           }

       }

    }

    private void executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement st;
        st = conn.createStatement();
        st.executeUpdate(query);
    }

}