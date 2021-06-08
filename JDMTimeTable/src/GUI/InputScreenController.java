/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import admtimetable.DateList;
import admtimetable.TimeTable;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hamad
 */
public class InputScreenController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private CheckBox satTransitionCheck;
    @FXML
    private CheckBox sunTransitionCheck;
    @FXML
    private CheckBox monOkCheck;
    @FXML
    private CheckBox tueOKCheck;
    @FXML
    private CheckBox wedOKCheck;
    @FXML
    private CheckBox thurOKCheck;
    @FXML
    private CheckBox friOKCheck;
    @FXML
    private CheckBox satOKCheck;
    @FXML
    private CheckBox sunOKCheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void goToTemplate(ActionEvent event) throws IOException {
        LocalDate stDate = startDate.getValue();
        Calendar stDate2 = Calendar.getInstance();
        stDate2.set(stDate.getYear(), stDate.getMonthValue() - 1, stDate.getDayOfMonth());

        LocalDate enDate = endDate.getValue();

        Calendar enDate2 = Calendar.getInstance();
        enDate2.set(enDate.getYear(), enDate.getMonthValue() - 1, enDate.getDayOfMonth());

        Calendar C = Calendar.getInstance();
        Calendar C2 = Calendar.getInstance();
        C = stDate2;
        C2 = enDate2;
        
        if (startDate.getValue() == null) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Missing Input");
            al.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter a Start Date for the Template.")));
            al.showAndWait();
        } else if (endDate.getValue() == null) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Missing Input");
            al.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter an End Date for the Template.")));
            al.showAndWait();
        } 
        else if(enDate.getDayOfYear() < stDate.getDayOfYear() && enDate.getYear() <= stDate.getYear()){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Start Date cannot be greater than End Date");
            al.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter valid dates.")));
            al.showAndWait();        
        }
        else {

            int i = 10;
            int counter = 0;
             String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
            while ((C.get(Calendar.DATE) != C2.get(Calendar.DATE)) || (C.get(Calendar.MONTH) != C2.get(Calendar.MONTH)))// && (C.get(Calendar.YEAR) != C2.get(Calendar.YEAR)) && ) {
            {
                Date date = C.getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String date1 = format1.format(date);
                String day = days[C.get(Calendar.DAY_OF_WEEK) - 1];
                date1 = date1 + " ";
                date1 = date1 + day;
                DateList.L.add(counter, date1);
                counter++;
                C.add(Calendar.DAY_OF_YEAR, 1);

            }

            boolean sunTCheck = sunTransitionCheck.isSelected();
            boolean satTCheck = satTransitionCheck.isSelected();

            boolean mon = monOkCheck.isSelected();
            boolean tue = tueOKCheck.isSelected();
            boolean wed = wedOKCheck.isSelected();
            boolean thur = thurOKCheck.isSelected();
            boolean fri = friOKCheck.isSelected();
            boolean sat = satOKCheck.isSelected();
            boolean sun = sunOKCheck.isSelected();

            TimeTable tt = new TimeTable(stDate2, enDate2, sunTCheck, satTCheck, mon, tue, wed, thur, fri, sat, sun);

            //Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/TimeTable.fxml"));
            Parent root = (Parent)loader.load();

            TimeTableController tbc = loader.getController();
            tbc.setTb(tt);
            
            
             Stage stage = new Stage();
            stage.setTitle("Timetable Generation");
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();

            
            // Create the Scene
            Scene scene = new Scene(root);
            // Set the Scene to the Stage
            stage.setScene(scene);
            // Set the Title to the Stage
            stage.setTitle("Baseball Time Table");
            // Display the Stage



            

            stage.show();


            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());

        }
    }

}
