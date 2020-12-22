package ru.vmk.shahova.databaseUi.ui.page;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Zadacha2Controller implements Initializable {
    @FXML
    private TableColumn<Zad2, Integer> tfKodSup;
    @FXML
    private TableColumn<Zad2, Integer> tfUnitPrice;
    @FXML
    private TableView<Zad2> tableZad2;
    public static List<Zad2> resultlist;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableZad2.setItems(FXCollections.observableList(resultlist));
        tfKodSup.setCellValueFactory(new PropertyValueFactory<>("codeSupplier"));
        tfUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tfKodSup.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tfUnitPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }
}
