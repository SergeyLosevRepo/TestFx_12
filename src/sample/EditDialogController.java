package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 13.07.17.
 */
public class EditDialogController implements Initializable{
    @FXML public TextField txtName;
    @FXML public TextField txtPhone;
    @FXML public Button btnOk;
    @FXML public Button btnCancel;
    private Person person;
    private ResourceBundle resourceBundle;

    public void actionOk(ActionEvent actionEvent) {
        this.person.setPhone(txtPhone.getText());
        this.person.setFio(txtName.getText());
        actionCancel(actionEvent);
    }

    public void setPerson (Person person){
        this.person = person;
        txtName.setText(person.getFio());
        txtPhone.setText(person.getPhone());
    }

    public void actionCancel(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public Person getPerson() {
        return this.person;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }
}
