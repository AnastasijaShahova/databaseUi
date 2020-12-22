package ru.vmk.shahova.databaseUi.ui.page;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DateStringConverter;
import ru.vmk.shahova.databaseUi.ui.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class Controller3 implements Initializable {
    @FXML
    private TableColumn<Itog, Integer> tfKod;
    @FXML
    private TableColumn<Itog, String> tfType;
    @FXML
    private TableColumn<Itog, String> tfName;
    @FXML
    private TableColumn<Itog, String> tfInit;
    @FXML
    private TableColumn<Itog, Integer> tfPrice;
    @FXML
    private TableColumn<Itog, Integer> tfNumber;
    @FXML
    private TableColumn<Itog, Integer> tfKodSup;
    @FXML
    private TableColumn<Itog, Date> tfDate;
    @FXML
    private TableColumn<Itog, Date> tfStart;
    @FXML
    private TableColumn<Itog, Date> tfEnd;
    @FXML
    private TableColumn<Itog, Integer> tfPlan;
    @FXML
    private TableColumn<Itog, Integer> tfUnit;
    @FXML
    private Button btnZad2;


    @FXML
    private TableView<Itog> tableItog;

    //language=SQL
    private static final String SELECT_BY_Info
            = "SELECT * " +
            "FROM catalog_details " +
            "         LEFT JOIN supply_details sd on catalog_details.kod_details = sd.kod_details " +
            "         LEFT JOIN contract_supplier cs on sd.number_contract = cs.number_contract ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableItog.setItems(FXCollections.observableList(new ArrayList(getByInfo())));
        tfKod.setCellValueFactory(new PropertyValueFactory<>("detailCode"));
        tfType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tfName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tfInit.setCellValueFactory(new PropertyValueFactory<>("init"));
        tfPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tfNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tfKodSup.setCellValueFactory(new PropertyValueFactory<>("codeSupplier"));
        tfDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tfStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tfEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tfPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        tfUnit.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tfKod.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfType.setCellFactory(TextFieldTableCell.forTableColumn());
        tfName.setCellFactory(TextFieldTableCell.forTableColumn());
        tfInit.setCellFactory(TextFieldTableCell.forTableColumn());
        tfPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfKodSup.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        tfStart.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        tfEnd.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        tfPlan.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfUnit.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        btnZad2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->groupByKodSupplier());

    }
    public Set<Itog> getByInfo(){
        Set<Itog> resultSet =  new HashSet();
        Connection conn = Controller.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SELECT_BY_Info);
            Itog result;
            while (rs.next()) {
                result = new Itog(
                        rs.getInt("kod_details"),
                        rs.getString("type_details"),
                        rs.getString("name_details"),
                        rs.getString("init"),
                        rs.getInt("price_details"),
                        rs.getInt("number_contract"),
                        rs.getInt("kod_supplier"),
                        rs.getDate("data_contract"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("plan"),
                        rs.getInt("unit_price"));
                resultSet.add(result);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Error");
        }
        Set set= new HashSet(resultSet);
        return resultSet;
    }

    public void groupByKodSupplier () {
        List<Zad2> result = new ArrayList<Zad2>();
        // это словать ключ это код детайл - значение это список итогов по это код детайл
        Map<Integer, List<Itog>> map = tableItog.getItems() // берем все элементы из таблицы
                .stream() // запускаем конвеер
                .collect(Collectors.groupingBy(Itog::getCodeSupplier)); // группируем из по детайл код

        // entry - это одно значение мапы, по факту просто (ключ значение)
        for (Map.Entry<Integer, List<Itog>> entry: map.entrySet()) {
            // тут мы берем ключи это код саплайер
            int kodSupplier = entry.getKey();
            // тут берем значение это список итогов
            int sum = entry.getValue()
                    .stream()
                    .filter(itog -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(itog.getDate());
                        int year = calendar.get(Calendar.YEAR); // просто берем год от даты
                        return year == 2020; // и сравниваем
                    })
                    .mapToInt(Itog::getUnitPrice) // говорим что преобразуем в инты (от каждого итога берем юнит прайс)
                    .sum(); // считаем сумму
            Zad2 zad2 = new Zad2(kodSupplier, sum);
            result.add(zad2);
        }
        Zadacha2Controller.resultlist=result;
        Stage dialog1 = new Stage();
        dialog1.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Zadacha2.fxml"));
        try {
            Parent root = loader.load();
            dialog1.setScene(new Scene(root, 500, 500));
            dialog1.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
