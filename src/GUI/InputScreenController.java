/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import admtimetable.DateList;
import admtimetable.Slot;
import admtimetable.SlotButton;
import admtimetable.TimeTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hamad
 */
public class InputScreenController extends Application implements Initializable {

    @FXML
    private Label title;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private CheckBox satTransitionCheck;
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
    @FXML
    private Button imp;

    Stage s;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void start(Stage primaryStage) throws Exception {
        s = new Stage();
    }

    @FXML
    private void goToTemplate(ActionEvent event) throws IOException {
        LocalDate stDate = startDate.getValue();
        LocalDate enDate = endDate.getValue();
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
        } else if (enDate.getDayOfYear() < stDate.getDayOfYear() && enDate.getYear() <= stDate.getYear()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("Start Date cannot be greater than End Date");
            al.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Please Enter valid dates.")));
            al.showAndWait();
        } else {

            Calendar stDate2 = Calendar.getInstance();
            stDate2.set(stDate.getYear(), stDate.getMonthValue() - 1, stDate.getDayOfMonth());

            Calendar enDate2 = Calendar.getInstance();
            enDate2.set(enDate.getYear(), enDate.getMonthValue() - 1, enDate.getDayOfMonth());

            Calendar C = Calendar.getInstance();
            Calendar C2 = Calendar.getInstance();
            C = stDate2;
            C2 = enDate2;
            int i = 10;
            int counter = 0;
            String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
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

            boolean satTCheck = satTransitionCheck.isSelected();

            boolean mon = monOkCheck.isSelected();
            boolean tue = tueOKCheck.isSelected();
            boolean wed = wedOKCheck.isSelected();
            boolean thur = thurOKCheck.isSelected();
            boolean fri = friOKCheck.isSelected();
            boolean sat = satOKCheck.isSelected();
            boolean sun = sunOKCheck.isSelected();

            TimeTable.setTimeTable(stDate2, enDate2,true, satTCheck, mon, tue, wed, thur, fri, sat, sun);


            
            //Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/TimeTable.fxml"));
            Parent root = (Parent) loader.load();

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

    @FXML
    private void importFromFFile(ActionEvent event){
        try {
            List<TimeTable> TList = new ArrayList<>();
            String row;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open  File");
            File file = fileChooser.showOpenDialog(s);
            
            BufferedReader csvReader;
            String[] firstLine = null;
            Calendar stDate2 = Calendar.getInstance();
            Calendar enDate2 = Calendar.getInstance();
            
            boolean first = false;
            
            List<SlotButton> lsb = new ArrayList<SlotButton>();
            List<Slot> ls = new ArrayList<Slot>();
            
            int i = 0;
            csvReader = new BufferedReader(new FileReader(file));
            if ((row = csvReader.readLine()) != null) {
                firstLine = row.split(",");
            }
            
            while ((row = csvReader.readLine()) != null) {
                
                String[] data = row.split(",");
                
                String ind = data[0];
                String date = data[1];
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate stDate = LocalDate.parse(date, formatter);
                Calendar stDateCal = Calendar.getInstance();
                stDateCal.set(stDate.getYear(), stDate.getMonthValue() - 1, stDate.getDayOfMonth());
                
                if (!first) {
                    stDate2 = stDateCal;
                    first = true;
                }
                
                enDate2 = stDateCal;
                
                Slot s = new Slot(data[0], data[1], data[2]);
                
                String str1 = data[7];
                String str2 = data[6];
                
                if (str1.equals("null")) {
                    str1 = "";
                }
                if (str2.equals("null")) {
                    str2 = "";
                }
                
                s.setColor(str1);
                s.setEvent(str2);
                
                ls.add(s);
                
                SlotButton sb = new SlotButton(new Button());
                sb.setColor(data[4]);
                sb.setNum(Integer.parseInt(data[3]));
                sb.setNumInsideBut(Integer.parseInt(data[5]));
                
                lsb.add(sb);
                
            }
            
            boolean[] bb = new boolean[9];
            int bbInd = 0;
            
            if (firstLine != null) {
                for (int k = 0; k<9; k++) {
                    if (firstLine[k].equalsIgnoreCase("false")) {
                        bb[bbInd++] = false;
                    } else {
                        bb[bbInd++] = true;
                    }
                }
            }
            
            Calendar C = stDate2;
            Calendar C2 = enDate2;
            int counter = 0;
            String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
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
            
            
            TimeTable.setTimeTable(stDate2, enDate2, bb[0], bb[1], bb[2], bb[3], bb[4], bb[5], bb[6], bb[7], bb[8]);
            TimeTable.tb.blocksUsed = Integer.parseInt(firstLine[9]);
            TimeTable.tb.transitionCount = Integer.parseInt(firstLine[10]);
            
            TimeTable.tb.l1 = Integer.parseInt(firstLine[11]);
            TimeTable.tb.l2 = Integer.parseInt(firstLine[12]);
            TimeTable.tb.l3 = Integer.parseInt(firstLine[13]);
            TimeTable.tb.l4 = Integer.parseInt(firstLine[14]);
            TimeTable.tb.l5 = Integer.parseInt(firstLine[15]);
            TimeTable.tb.l6 = Integer.parseInt(firstLine[16]);
            TimeTable.tb.l7 = Integer.parseInt(firstLine[17]);
            TimeTable.tb.l8 = Integer.parseInt(firstLine[18]);
            
            
            
            TimeTable.tb.ls = ls;
            TimeTable.tb.lsb = lsb;
            
            //Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/TimeTable.fxml"));
            Parent root = (Parent) loader.load();
            
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
