/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import qcas.backEnd.DatabaseManager;
import qcas.backEnd.User;

/*
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
 */
/**
 *
 * @author aayush
 */
public class FXMLHomeController extends AnchorPane implements Initializable {

    @FXML
    private Text actiontarget;
    @FXML
    private TextField usernamefield;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> cmbLoginType;

    private QCAS application;
   User userObject = new User();

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {

        String username = usernamefield.getText();
        String password = passwordField.getText();
        DatabaseManager dbManager = new DatabaseManager();
        //actiontarget.setText("invalid credentials");
        User user = null;
        if(!username.isEmpty() && !password.isEmpty() && !cmbLoginType.getValue().isEmpty()){
            user = dbManager.login(username, password, cmbLoginType.getValue());
        }else{
            usernamefield.clear();
            passwordField.clear();
            actiontarget.setText("Please enter a valid username and password !");
            return;
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if (user != null && user.getUserID() > 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GenTest.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                GenTestController controller = fxmlLoader.<GenTestController>getController();
                controller.initData(user);
                stage.setTitle("Take Test");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            passwordField.clear(); //.setText("");
            actiontarget.setText("Invalid Credentials!");
        }
    }

    public void setApp(QCAS application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actiontarget.setText("");
        usernamefield.setPromptText("Username");
        passwordField.setPromptText("Password");
    }

    @FXML
    public void initData(User user) {
        userObject = user;
    }
}
