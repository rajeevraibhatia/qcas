/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import qcas.backEnd.Test;

/**
 * FXML Controller class
 *
 * @author aayush
 */
public class MCQuestionController implements Initializable {

    Test testobject = new Test();
    @FXML
    private Label lblQuestion;
    @FXML
    private RadioButton rbA;
    @FXML
    private RadioButton rbB;
    @FXML
    private RadioButton rbC;
    @FXML
    private RadioButton rbD;
    @FXML
    private ToggleGroup tgAnswers;
    private String answer;
    @FXML
    private Label lblHeading;
    @FXML
    private TextArea txtQuestion;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rbA.setToggleGroup(tgAnswers);
        rbB.setToggleGroup(tgAnswers);
        rbC.setToggleGroup(tgAnswers);
        rbD.setToggleGroup(tgAnswers);

        tgAnswers.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (tgAnswers.getSelectedToggle() != null) {
                    answer = ((RadioButton) tgAnswers.getSelectedToggle()).getText(); // ; .getUserData().toString();
                    //System.out.println(tgAnswers.getSelectedToggle().getUserData().toString());
                }
            }
        });
    }

    /**
     * previous button click
     *
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    @FXML
    protected void handlePreviousButtonAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
    }

    /**
     * next button click
     * @param event
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    protected void handleNextButtonAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException, IOException, ParseException {
        String a = answer; //.g;
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        int currentQuestion = testobject.getCurrentQuestionNumber();
        String correctAnswer = testobject.getQuestionList().get(currentQuestion).getAnswerString();
        testobject.setCurrentQuestionNumber(currentQuestion + 1); //.getQuestionList().get(currectQuestion).getAnswer();
        Parent root1 = null;
        FXMLLoader fxmlLoader = null;
        if (a == null) {
            testobject.setUnansweredQuestions(testobject.getUnansweredQuestions() + 1);
        } else if (correctAnswer.equals(answer)) {
            testobject.setCorrectQuestions(testobject.getCorrectQuestions() + 1);
        } else if (!correctAnswer.equals(answer)) {
            testobject.setIncorrectQuestions(testobject.getIncorrectQuestions() + 1);
        }

        if (currentQuestion == (testobject.getNumberOfQuestions() - 1)) {
            currentQuestion++;
            stage.setTitle("Test Report");
            fxmlLoader = new FXMLLoader(getClass().getResource("EndTest.fxml"));
            root1 = (Parent) fxmlLoader.load();
            //createTestResult();
            EndTestController controller = fxmlLoader.<EndTestController>getController();
            controller.initData(testobject);
        } else {
            stage.setTitle("Test");
            currentQuestion++;
            String nextQuestionType = testobject.getQuestionList().get(currentQuestion).getQuestionType();

            if (nextQuestionType.equals("MC")) {
                fxmlLoader = new FXMLLoader(getClass().getResource("MCQuestion.fxml"));
                root1 = (Parent) fxmlLoader.load();
                MCQuestionController controller = fxmlLoader.<MCQuestionController>getController();
                controller.initData(testobject);
            } else if (nextQuestionType.equals("MA")) {
                fxmlLoader = new FXMLLoader(getClass().getResource("MAQuestion.fxml"));
                root1 = (Parent) fxmlLoader.load();
                MAQuestionController controller = fxmlLoader.<MAQuestionController>getController();
                controller.initData(testobject);
            } else if (nextQuestionType.equals("TF")) {
                fxmlLoader = new FXMLLoader(getClass().getResource("TFNew.fxml"));
                root1 = (Parent) fxmlLoader.load();
                TFNewController controller = fxmlLoader.<TFNewController>getController();
                controller.initData(testobject);
            } else if (nextQuestionType.equals("FIB")) {
                fxmlLoader = new FXMLLoader(getClass().getResource("FIBQuestion.fxml"));
                root1 = (Parent) fxmlLoader.load();
                FIBQuestionController controller = fxmlLoader.<FIBQuestionController>getController();
                controller.initData(testobject);
            }
        }

        //Stage stage = new Stage();
        stage.setScene(new Scene(root1, 630, 510));
        stage.show();
    }

    /**
     * end test button
     * @param event
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    protected void handleEndTestButtonAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException, IOException, ParseException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //createTestResult();
        Parent root1 = null;
        FXMLLoader fxmlLoader = null;
        fxmlLoader = new FXMLLoader(getClass().getResource("EndTest.fxml"));
        root1 = (Parent) fxmlLoader.load();
        stage.setTitle("Test Report");
        EndTestController controller = fxmlLoader.<EndTestController>getController();
        controller.initData(testobject);
        stage.setScene(new Scene(root1, 630, 510));
        stage.show();
    }

    /**
     * initialize resourcess
     * @param test
     */
    @FXML
    public void initData(Test test) {
        testobject = test;
        String difficulty = (testobject.getQuestionList().get(testobject.getCurrentQuestionNumber())).getDifficulty();
        lblHeading.setText(lblHeading.getText() + " - " + difficulty);

        // String difficulty = (testobject.getQuestionList().get(testobject.getCurrentQuestionNumber() + 1)).getDifficulty();
//        txtQuestion.setText("Q " + (testobject.getCurrentQuestionNumber() + 1) + ". " + (testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber()).getQuestion());
        lblQuestion.setText("Q " + (testobject.getCurrentQuestionNumber() + 1) + ". " + (testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber()).getQuestion());
        //lblQuestion.setText(((testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber())).getQuestion());
        rbA.setText(((testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber())).getOptionA());
        rbB.setText(((testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber())).getOptionB());
        rbC.setText(((testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber())).getOptionC());
        rbD.setText(((testobject.getQuestionList()).get(testobject.getCurrentQuestionNumber())).getOptionD());
    }

}
