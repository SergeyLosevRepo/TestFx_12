package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private CollectioAdressBook collectioAdressBook = new CollectioAdressBook();

    @FXML public Button btnChange;
    @FXML public Button btnDelete;
    @FXML public Button btnAdd;
    @FXML public CustomTextField txtSearch;
    @FXML public Button btnSearch;
    @FXML public TableView tblData;
    @FXML public Label lblCount;
    @FXML public TableColumn<Person, String> colFio;
    @FXML public TableColumn<Person, String> colPhone;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;
    private Stage stage;
    private ResourceBundle resourceBundle;
    private ObservableList<Person> backupList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.resourceBundle = resources;
        colFio.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
        try {
            setupClearButtonField(txtSearch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initListeners();
        fillData();
        initLoader();




    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }



    @FXML
    public void pressButton(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (!(source instanceof  Button)){
            return;
        }

        Button clickButton = (Button) source;


        switch (clickButton.getId()){

            case "btnAdd":
                editDialogController.setPerson(new Person());
                showDialog();
                if (editDialogController.getPerson().getFio() == "") return;
                collectioAdressBook.add(editDialogController.getPerson());
                backupList.add(editDialogController.getPerson());
                break;

            case "btnChange":
                Person selectPerson = (Person) tblData.getSelectionModel().getSelectedItem();
                if (!personIsSelected(selectPerson)) return;
                editDialogController.setPerson(selectPerson);
                showDialog();
                break;

            case "btnDelete":
                Person selectPerson1 = (Person) tblData.getSelectionModel().getSelectedItem();
                if (!personIsSelected(selectPerson1)) return;
                collectioAdressBook.delete(selectPerson1);
                backupList.remove(selectPerson1);
                break;

            case "btnSearch":
               search();
                break;

        }

    }

    private boolean personIsSelected(Person person){
        if (person == null) {
            DialogManager.showInfoDialog(resourceBundle.getString("eror"), resourceBundle.getString("select_person"));
            return false;
        }
        return true;
    }

    private void search(){
        collectioAdressBook.getPersonList().clear();
        for (Person person : backupList){
            if (person.getFio().toLowerCase().contains(txtSearch.getText().toLowerCase()) || person.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase())){
                collectioAdressBook.getPersonList().add(person);
            }
        }
    }

    private void showDialog() {
        if (editDialogStage == null) {
        editDialogStage = new Stage();
        editDialogStage.setTitle(resourceBundle.getString("edit"));
        editDialogStage.setMinHeight(150);
        editDialogStage.setMinWidth(300);
        editDialogStage.setResizable(false);
        editDialogStage.setScene(new Scene(fxmlEdit));
        editDialogStage.initModality(Modality.WINDOW_MODAL);
        editDialogStage.initOwner(stage);
        }

        editDialogStage.showAndWait();
    }

    private void initListeners(){

        collectioAdressBook.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });

        tblData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    editDialogController.setPerson((Person) tblData.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });

    }

    private void fillData() {
        collectioAdressBook.fillTestData();
        tblData.setItems(collectioAdressBook.getPersonList());
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectioAdressBook.getPersonList());
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("edit.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("controller.Locale", new Locale("en")));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCountLabel(){
        lblCount.setText(resourceBundle.getString("count") + ": " + collectioAdressBook.getPersonList().size());
    }

    private void setupClearButtonField(CustomTextField customTextField)  throws Exception {
        Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
        m.setAccessible(true);
        m.invoke(null, customTextField, customTextField.rightProperty());
    }


}
