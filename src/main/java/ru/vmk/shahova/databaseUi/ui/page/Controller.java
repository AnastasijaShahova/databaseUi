package ru.vmk.shahova.databaseUi.ui.page;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import ru.vmk.shahova.databaseUi.ui.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class Controller implements Initializable {

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
    private TextField tfNumber;
    @FXML
    private TextField tfKodd;
    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfNumber1;
    @FXML
    private TextField tfKod1;
    @FXML
    private TextField tfInit1;
    @FXML
    private DatePicker tfStartDate;
    @FXML
    private DatePicker tfEndDate;
    @FXML
    private TextField tfPlan;
    @FXML
    private TextField tfPrice1;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnInsert1;
    @FXML
    private Button btnInsert2;
    @FXML
    private Button btnExecute;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnUpdate1;
    @FXML
    private Button btnDelete1;
    @FXML
    private Button btnUpdate2;
    @FXML
    private Button btnDelete2;
    @FXML
    private Button btnSelectSelect;

    //language=SQL
    private static final String INSERT_CATALOG_QUERY = "INSERT INTO catalog_details (kod_details, type_details, name_details, init, price_details) VALUES ('%s','%s','%s','%s','%s')";
    //language=SQL
    private static final String INSERT_CONTRACT_QUERY = "INSERT INTO contract_supplier (kod_supplier, data_contract) VALUES ('%s','%s')";
    //language=SQL
    private static final String INSERT_SUPPLY_QUERY = "INSERT INTO supply_details (number_contract, kod_details, init, start_date, end_date, plan, unit_price) VALUES ('%s','%s','%s','%s','%s','%s','%s')";

    //language=SQL
    private static final String UPDATE_CONTRACT_QUERY = "UPDATE contract_supplier SET kod_supplier = '%s', data_contract = '%s' WHERE number_contract = '%s'";
    //language=SQL
    private static final String UPDATE_CATALOG_QUERY = "UPDATE catalog_details SET type_details = '%s', name_details = '%s',init='%s',price_details='%s' WHERE kod_details = '%s'";
    //language=SQL
    private static final String UPDATE_SUPPLY_QUERY = "UPDATE supply_details SET init = '%s', start_date = '%s',end_date = '%s', plan = '%s', unit_price = '%s' WHERE number_contract = '%s' AND kod_details = '%s'";

    //language=SQL
    private static final String DELETE_CATALOG_QUERY = "DELETE FROM catalog_details WHERE kod_details = '%s'";
    //language=SQL
    private static final String DELETE_CONTRACT_QUERY = "DELETE FROM contract_supplier WHERE number_contract = '%s' ";
    //language=SQL
    private static final String DELETE_SUPPLY_QUERY = "DELETE FROM supply_details WHERE number_contract = '%s' AND kod_details = '%s'";


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
        btnSelect.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openResultQuery());
        btnSelectSelect.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openResultQuery1());
        kod_details.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        type_details.setCellValueFactory(new PropertyValueFactory<>("type"));
        name_details.setCellValueFactory(new PropertyValueFactory<>("name"));
        init.setCellValueFactory(new PropertyValueFactory<>("init"));
        price_details.setCellValueFactory(new PropertyValueFactory<>("price"));

        catalogDetails.setEditable(true);
        kod_details.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        type_details.setCellFactory(ComboBoxTableCell.forTableColumn("Покупная", "Собственная"));
        name_details.setCellFactory(ComboBoxTableCell.forTableColumn("Гвоздь", "Шуруп", "Молоток"));
        init.setCellFactory(ComboBoxTableCell.forTableColumn("кг", "шт"));
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
        btnInsert1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> insertRecord1());
        btnInsert2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> insertRecord2());
        btnUpdate.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> updateRecord());
        btnUpdate1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> updateRecord1());
        btnUpdate2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> updateRecord2());
        btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteRecord());
        btnDelete1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteRecord1());
        btnDelete2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> deleteRecord2());
        btnExecute.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> executeProcedure());
    }

    private static final String url = "jdbc:postgresql://127.0.0.1:5433/postgres";
    private static final String user = "postgres";
    private static final String pass = "postgres";

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception ex) {
            throw new RuntimeException("Error connection");
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
            throw new RuntimeException("Error get catalog");
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
            throw new RuntimeException("Error get contract");
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
            throw new RuntimeException("Error get contract");
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
                result += " " + rs.getString(1);
            }
            this.minPrice.setText(result);
        } catch (Exception throwables) {
            throw new RuntimeException("Error execute procedure");
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
        } catch (SQLException throwable) {
            throw new RuntimeException("Error insert");
        }
    }

    private void insertRecord1() {
        String query = String.format(INSERT_CONTRACT_QUERY, tfKodd.getText(), tfDate.getValue());
        try {
            executeQuery(query);
            contract.getItems().add(new Contract(
                    Integer.parseInt(tfNumber.getText()),
                    Integer.parseInt(tfKodd.getText()),
                    java.sql.Date.valueOf(tfDate.getValue()))
            );
        } catch (SQLException throwable) {
            throw new RuntimeException("Error insert");
        }
    }

    private void insertRecord2() {
        String query = String.format(INSERT_SUPPLY_QUERY, tfNumber1.getText(), tfKod1.getText(), tfInit1.getText(), tfStartDate.getValue(), tfEndDate.getValue(), tfPlan.getText(), tfPrice1.getText());
        try {
            executeQuery(query);
            supply.getItems().add(new Supply(
                    Integer.parseInt(tfNumber1.getText()),
                    Integer.parseInt(tfKod1.getText()),
                    tfInit1.getText(),
                    java.sql.Date.valueOf(tfStartDate.getValue()),
                    java.sql.Date.valueOf(tfEndDate.getValue()),
                    Integer.parseInt(tfPlan.getText()),
                    Integer.parseInt(tfPrice1.getText()))
            );
        } catch (SQLException throwable) {
            throw new RuntimeException("Error insert");
        }
    }

    private void updateRecord() {

        String query = String.format(UPDATE_CATALOG_QUERY, tfType.getText(), tfName.getText(), tfInit.getText(), tfPrice.getText(), tfKod.getText());
        System.out.println(query);
        try {
            executeQuery(query);
            Optional<Catalog> catalogOptional = catalogDetails.getItems()
                    .filtered(item -> item.getDetailCode() == Integer.parseInt(tfKod.getText()))
                    .stream()
                    .findFirst();

            if (catalogOptional.isPresent()) {
                int index = catalogDetails.getItems().indexOf(catalogOptional.get());
                Catalog catalog = catalogOptional.get();
                catalog.setDetailCode(Integer.parseInt(tfKod.getText()));
                catalog.setType(tfType.getText());
                catalog.setName(tfName.getText());
                catalog.setInit(tfInit.getText());
                catalog.setPrice(Integer.parseInt(tfPrice.getText()));
                catalogDetails.getItems().set(index, catalog);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateRecord1() {

        String query = String.format(UPDATE_CONTRACT_QUERY, tfKodd.getText(), tfDate.getValue(), tfNumber.getText());
        try {
            executeQuery(query);
            Optional<Contract> contractOptional = contract.getItems()
                    .filtered(item -> item.getNumber() == Integer.parseInt(tfNumber.getText()))
                    .stream()
                    .findFirst();

            if (contractOptional.isPresent()) {
                int index = contract.getItems().indexOf(contractOptional.get());
                Contract contract1 = contractOptional.get();
                contract1.setNumber(Integer.parseInt(tfNumber.getText()));
                contract1.setDate(java.sql.Date.valueOf(tfDate.getValue()));
                contract.getItems().set(index, contract1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateRecord2() {

        String query = String.format(UPDATE_SUPPLY_QUERY, tfInit1.getText(), tfStartDate.getValue(), tfEndDate.getValue(), tfPlan.getText(), tfPrice1.getText(), tfNumber1.getText(), tfKod1.getText());
        try {
            executeQuery(query);
            Optional<Supply> supplyOptional = supply.getItems()
                    .filtered(item -> (item.getNumber() == Integer.parseInt(tfNumber1.getText())) && (item.getDetailCode() == Integer.parseInt(tfKod1.getText())))
                    .stream()
                    .findFirst();
            if (supplyOptional.isPresent()) {
                int index = supply.getItems().indexOf(supplyOptional.get());
                Supply supply1 = supplyOptional.get();
                supply1.setInit(tfInit1.getText());
                supply1.setStart(java.sql.Date.valueOf(tfStartDate.getValue()));
                supply1.setEnd(java.sql.Date.valueOf(tfEndDate.getValue()));
                supply1.setPlan(Integer.parseInt(tfPlan.getText()));
                supply1.setPrice(Integer.parseInt(tfPrice1.getText()));
                supply.getItems().set(index, supply1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteRecord() {
        Catalog catalog = catalogDetails.getSelectionModel().getSelectedItem();
        if (catalog != null) {
            String query = String.format(DELETE_CATALOG_QUERY, catalog.getDetailCode());
            try {
                executeQuery(query);
                catalogDetails.getItems().removeIf(item -> item.getDetailCode() == catalog.getDetailCode());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteRecord1() {
        Contract contr = contract.getSelectionModel().getSelectedItem();
        if (contr != null) {
            String query = String.format(DELETE_CONTRACT_QUERY, contr.getNumber());
            try {
                executeQuery(query);
                contract.getItems().removeIf(item -> item.getNumber() == contr.getNumber());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteRecord2() {
        Supply supl = supply.getSelectionModel().getSelectedItem();
        if (supl != null) {
            String query = String.format(DELETE_SUPPLY_QUERY, supl.getNumber(), supl.getDetailCode());
            try {
                executeQuery(query);
                supply.getItems().removeIf(item -> (item.getNumber() == Integer.parseInt(tfNumber1.getText())) && (item.getDetailCode() == Integer.parseInt(tfKod1.getText())));
            } catch (SQLException e) {
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

    private static void openResultQuery() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/newWindow.fxml"));
        try {
            Parent root = loader.load();
            dialog.setScene(new Scene(root, 500, 500));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void openResultQuery1() {
        List<Contract> contracts = this.contract.getItems().stream()
                .filter(item -> item.getDate().after(new GregorianCalendar(2020, 05, 01).getTime()))
                .collect(Collectors.toList());

        List<Integer> contractNumber = contracts.stream()
                .map(Contract::getNumber)
                .collect(Collectors.toList());

        List<Supply> supplies = this.supply.getItems().stream()
                .filter(item -> contractNumber.contains(item.getNumber()))
                .collect(Collectors.toList());

        List<Integer> suppliesKods = supplies.stream()
                .map(Supply::getDetailCode)
                .collect(Collectors.toList());

        List<Catalog> catalogs = this.catalogDetails.getItems().stream()
                .filter(item -> suppliesKods.contains(item.getDetailCode()))
                .collect(Collectors.toList());

        List<Result> resultList = new ArrayList<>();
        for (int i = 0; i < supplies.size(); i++) {
            Result result = new Result(
                    supplies.get(i).getDetailCode(),
                    catalogs.get(i).getName(),
                    supplies.get(i).getPrice(),
                    supplies.get(i).getPlan()
            );
            resultList.add(result);
        }

        Controller2.results = resultList;

        Stage dialog1 = new Stage();
        dialog1.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Window.fxml"));
        try {
            Parent root = loader.load();
            dialog1.setScene(new Scene(root, 500, 500));
            dialog1.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}