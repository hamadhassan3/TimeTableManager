/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import admtimetable.*;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javaws.Main;
import java.awt.Color;
import java.awt.Font;
import static java.awt.SystemColor.menu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author hamad
 */
public class TimeTableController extends Application implements Initializable {

    double x;
    double y;
    TimeTableController var = this;
    public int BlocksUsed = 0;
    public int TransitionCount = 0;
    int b1Used = 0;
    int b2Used = 0;
    int b3Used = 0;
    int b4Used = 0;
    int b5Used = 0;
    int b6Used = 0;
    int b7Used = 0;
    int b8Used = 0;

    //private Pane ButtonsAnchor;
    public Button button1 = new Button();
    Button button2 = new Button();
    Button button3 = new Button();
    Button button4 = new Button();
    Button button5 = new Button();
    Button button6 = new Button();
    Button buttonOFF = new Button();
    Button buttonAllStar = new Button();

    final int minSize = 30;

    @FXML
    private Pane ButtonsAnchor;

    Date lastClickTime;

    int counter = -1;
    int prevButt = -1;

    /**
     * Initializes the controller class.
     */
    String color[] = {"Red", "Yellow", "Green", "Orange", "Purple", "Brown", "Blue", "No Color"};
    String butColors[] = {"Red", "Blue", "Grey", "Yellow", "Orange", "Purple", "black", "gold"};

    double boundsInParentX = -1;
    double boundsInParentY = -1;
    public ListView<String> tablefortime;
    @FXML
    private TableView<Slot> table;
    @FXML
    private TableColumn<Slot, String> colNo;
    @FXML
    private TableColumn<Slot, String> colDate;
    @FXML
    private TableColumn<Slot, String> colDay;
    private TableColumn<Slot, Button> colSlot;

    Stage s;
    List<String> listofrows = new ArrayList<>();
    @FXML
    private TableView<SlotButton> tableSlot;
    @FXML
    private TableColumn<SlotButton, Button> colSlotT2;
    @FXML
    private Label blockUsed;
    @FXML
    private Label trCount;
    @FXML
    private Button exp;
    @FXML
    private Button delButton;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Label l5;
    @FXML
    private Label l6;
    @FXML
    private Label l7;
    @FXML
    private Label l8;

    TableView<Slot> gettable() {
        return table;
    }

    TableView<SlotButton> getslottable() {
        return tableSlot;
    }

    Slot click = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        this.BlocksUsed = TimeTable.tb.blocksUsed;
        this.TransitionCount = TimeTable.tb.transitionCount;

        this.b1Used = TimeTable.tb.l1;
        
        this.b2Used = TimeTable.tb.l2;
        this.b3Used = TimeTable.tb.l3;
        this.b4Used = TimeTable.tb.l4;
        this.b5Used = TimeTable.tb.l5;
        this.b6Used = TimeTable.tb.l6;
        this.b7Used = TimeTable.tb.l7;
        this.b8Used = TimeTable.tb.l8;

        l1.setText("1 Used: " + Integer.toString(b1Used));
        l2.setText("2 Used: " + Integer.toString(b2Used));
        l3.setText("3 Used: " + Integer.toString(b3Used));
        l4.setText("4 Used: " + Integer.toString(b4Used));
        l5.setText("5 Used: " + Integer.toString(b5Used));
        l6.setText("6 Used: " + Integer.toString(b6Used));
        l7.setText("OFF Used: " + Integer.toString(b7Used));
        l8.setText("All Star Used: " + Integer.toString(b8Used));

        this.blockUsed.setText("Blocks Used: " + BlocksUsed);
        //this.blockUsed.setText("Blocks Used: " + TransitionCount);        
        this.trCount.setText("Tranisition Count: " + TransitionCount);

        colNo.setCellValueFactory(new PropertyValueFactory<Slot, String>("num"));
        colDate.setCellValueFactory(new PropertyValueFactory<Slot, String>("date"));
        colDay.setCellValueFactory(new PropertyValueFactory<Slot, String>("day"));

        colSlotT2.setCellValueFactory(new PropertyValueFactory<SlotButton, Button>("but"));

        table.setItems(getSlots());

        delButton.setStyle("fx-background-color: transparent;-fx-background-image: url('/GUI/del.png')");

        tableSlot.setRowFactory(tv -> new TableRow<SlotButton>() {
            @Override
            public void updateItem(SlotButton item, boolean empty) {

                if (item == null) {
                    setStyle("");
                } else if (item.getColor() == null) {
                    setStyle("");
                    item.getBut().setStyle("");
                    item.getBut().setText("");
                } else {
                    if (item.getColor().equalsIgnoreCase("Off Day")) {
                        setStyle("-fx-background-color: black");
                        item.getBut().setStyle("-fx-background-color: black; -fx-text-fill: white;");
                        item.getBut().setText("Off Day");

                    } else {
                        boolean found = false;
                        for (int i = 0; i < butColors.length; i++) {
                            if (item.getColor().equals(butColors[i])) {

                                if (item.getNum() == 1) {
                                    //Add up down left and right border
                                    setStyle("-fx-border-width: 5 5 5 5;-fx-border-color: " + "black" + ";");
                                } else if (item.getNumInsideBut() == 0) {
                                    //For up left right
                                    setStyle("-fx-border-width: 5 5 0 5;-fx-border-color: " + "black" + ";");
                                } else if (item.getNumInsideBut() == item.getNum() - 1) {
                                    //Add left right and down border
                                    setStyle("-fx-border-width: 0 5 5 5;-fx-border-color: " + "black" + ";");
                                } else {
                                    setStyle("-fx-border-width: 0 5 0 5;-fx-border-color: " + "black" + ";");
                                }

                                item.getBut().setText(Integer.toString(item.getNum()));

                                setStyle(getStyle() + "-fx-background-color: " + item.getColor() + ";");

                                if (i == 6) {
                                    item.getBut().setText("OFF");
                                    item.getBut().setTextFill(Paint.valueOf("white"));
                                } else if (i == 7) {
                                    item.getBut().setText("All Star");
                                    item.getBut().setTextFill(Paint.valueOf("white"));
                                } else {
                                    item.getBut().setTextFill(Paint.valueOf("black"));
                                }

                                item.getBut().setStyle("-fx-background-color: " + item.getColor() + ";");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            setStyle("");
                            item.getBut().setStyle("");
                        }
                    }
                }

            }
        });

        table.setRowFactory(tv -> new TableRow<Slot>() {
            @Override
            public void updateItem(Slot item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setStyle("");
                } else {
                    if (item.getColor().equals("No Color")) {
                        setStyle("");
                    } else {
                        boolean found = false;
                        for (int i = 0; i < color.length; i++) {
                            if (item.getColor().equals(color[i])) {
                                setStyle("-fx-background-color: " + color[i] + ";");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            setStyle("");
                        }
                    }
                }
            }
        });

        table.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            if ((event.getTarget() instanceof TableColumnHeader) | event.isDragDetect()) {
                // PerformYourActionHere

                ScrollBar scrollBarOne;
                ScrollBar scrollBarTwo;

                scrollBarOne = (ScrollBar) table.lookup(".scroll-bar:vertical");
                scrollBarTwo = (ScrollBar) tableSlot.lookup(".scroll-bar:vertical");
                if (scrollBarOne != null && scrollBarTwo != null) {
                    //scrollBarTwo.setBlockIncrement(34);
                    scrollBarOne.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            scrollBarTwo.setValue(scrollBarOne.getValue());
                        }
                    });
                }

            }
        });

        tableSlot.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            if ((event.getTarget() instanceof TableColumnHeader) | event.isDragDetect()) {
                // PerformYourActionHere

                ScrollBar scrollBarOne;
                ScrollBar scrollBarTwo;

                scrollBarOne = (ScrollBar) table.lookup(".scroll-bar:vertical");
                scrollBarTwo = (ScrollBar) tableSlot.lookup(".scroll-bar:vertical");
                if (scrollBarOne != null && scrollBarTwo != null) {
                    //scrollBarTwo.setBlockIncrement(34);
                    scrollBarTwo.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                            scrollBarOne.setValue(scrollBarTwo.getValue());
                        }
                    });
                }

            }
        });
        exp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open  File");
                File file = fileChooser.showOpenDialog(s);

                FileWriter csvWriter = null;
                try {
                    csvWriter = new FileWriter(file);

                    csvWriter.append(Boolean.toString(TimeTable.tb.satTCheck));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.sunTCheck));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.mon));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.tue));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.wed));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.thur));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.fri));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.sat));
                    csvWriter.append(",");
                    csvWriter.append(Boolean.toString(TimeTable.tb.sun));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(BlocksUsed));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(TransitionCount));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b1Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b2Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b3Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b4Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b5Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b6Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b7Used));
                    csvWriter.append(",");
                    csvWriter.append(Integer.toString(b8Used));
                    csvWriter.append("\n");
                    for (int i = 0; i < table.getItems().size(); i++) {
                        String i2 = Integer.toString(i);
                        try {
                            csvWriter.append(i2);
                            csvWriter.append(",");
                        } catch (IOException ex) {
                            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            csvWriter.append(table.getItems().get(i).getDate());
                            csvWriter.append(",");
                        } catch (IOException ex) {
                            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            csvWriter.append(table.getItems().get(i).getDay());
                            csvWriter.append(",");
                        } catch (IOException ex) {
                            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String temp = Integer.toString(tableSlot.getItems().get(i).getNum());
                        try {
                            csvWriter.append(temp);
                            csvWriter.append(",");

                        } catch (IOException ex) {
                            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        try {
                            csvWriter.append(tableSlot.getItems().get(i).getColor());
                            csvWriter.append(",");
                            csvWriter.append(Integer.toString(tableSlot.getItems().get(i).getNumInsideBut()));
                            csvWriter.append(",");
                            String strEv = table.getItems().get(i).getEvent();
                            if (strEv.isEmpty()) {
                                strEv = null;
                            }
                            csvWriter.append(strEv);
                            csvWriter.append(",");
                            String strCol = table.getItems().get(i).getColor();
                            if (strCol.isEmpty()) {
                                strCol = null;
                            }
                            csvWriter.append(strCol);
                            csvWriter.append(",");

                            csvWriter.append("\n");

                        } catch (IOException ex) {
                            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                } catch (IOException ex) {
                    Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    csvWriter.flush();
                } catch (IOException ex) {
                    Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    csvWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Slot row = table.getSelectionModel().getSelectedItem();

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {

                    Object object = table.getSelectionModel().selectedItemProperty().get();
                    int index = table.getSelectionModel().selectedIndexProperty().get();

                    // create a choice dialog 
                    ChoiceDialog<String> d = new ChoiceDialog<>("Color", color);

                    //ChoiceDialog d = new ChoiceDialog(days[1], days); 
                    d.setHeaderText("Add Color");
                    d.setContentText("Select the color!");

                    String color2;
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        //System.out.println("Your name: " + result.get());

                        color2 = result.get();
                        //Setting the color inside the slot
                        if (!color2.equals("Color")) {
                            row.setColor(color2);
                        }
                    }

                } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

                    System.out.println("Double Click");
                    Object object = table.getSelectionModel().selectedItemProperty().get();
                    int index = table.getSelectionModel().selectedIndexProperty().get();
                    TextInputDialog dialog = new TextInputDialog("walter");
                    dialog.setTitle("EVENT BOX");

                    dialog.setHeaderText(row.getEvent());

                    dialog.setContentText("Enter the event:");

                    String s;
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        s = result.get();
                        row.setEvent(s);
                    }

                }

                table.refresh();
            }
        });

        //Initialize with add event
        button1.setText("6");
        button1.setStyle("-fx-background-color: " + butColors[0] + ";");

        button2.setText("5");
        button2.setStyle("-fx-background-color: " + butColors[1] + ";");

        button3.setText("4");
        button3.setStyle("-fx-background-color: " + butColors[2] + ";");

        button4.setText("3");
        button4.setStyle("-fx-background-color: " + butColors[3] + ";");

        button5.setText("2");
        button5.setStyle("-fx-background-color: " + butColors[4] + ";");

        button6.setText("1");
        button6.setStyle("-fx-background-color: " + butColors[5] + ";");

        buttonOFF.setText("OFF");
        buttonOFF.setStyle("-fx-background-color: " + butColors[6] + ";");

        buttonAllStar.setText("All Star");
        buttonAllStar.setStyle("-fx-background-color: " + butColors[7] + ";");

        buttonOFF.setTextFill(Paint.valueOf("white"));
        buttonAllStar.setTextFill(Paint.valueOf("white"));

        buttonAllStar.setMinSize(200, minSize * 8);
        buttonAllStar.setPrefSize(200, minSize * 8);
        buttonAllStar.setMaxSize(200, minSize * 8);
        buttonOFF.setMinSize(200, minSize * 7);
        buttonOFF.setPrefSize(200, minSize * 7);
        buttonOFF.setMaxSize(200, minSize * 7);
        button1.setMinSize(200, minSize * 6);
        button1.setPrefSize(200, minSize * 6);
        button1.setMaxSize(200, minSize * 6);
        button2.setPrefSize(200, minSize * 5);
        button2.setMinSize(200, minSize * 5);
        button2.setMaxSize(200, minSize * 5);
        button3.setMinSize(200, minSize * 4);
        button3.setPrefSize(200, minSize * 4);
        button3.setMaxSize(200, minSize * 4);
        button4.setPrefSize(200, minSize * 3);
        button4.setMaxSize(200, minSize * 3);
        button4.setMinSize(200, minSize * 3);
        button5.setPrefSize(200, minSize * 2);
        button5.setMinSize(200, minSize * 2);
        button5.setMaxSize(200, minSize * 2);
        button6.setMinSize(200, minSize);
        button6.setPrefSize(200, minSize);
        button6.setMaxSize(200, minSize);

        buttonAllStar.setAlignment(Pos.BOTTOM_CENTER);
        buttonOFF.setAlignment(Pos.BOTTOM_CENTER);

        button1.setAlignment(Pos.BOTTOM_CENTER);
        button2.setAlignment(Pos.BOTTOM_CENTER);
        button3.setAlignment(Pos.BOTTOM_CENTER);
        button4.setAlignment(Pos.BOTTOM_CENTER);
        button5.setAlignment(Pos.BOTTOM_CENTER);
        button6.setAlignment(Pos.BOTTOM_CENTER);

        setListenerOnButton(buttonAllStar);
        setListenerOnButton(buttonOFF);
        setListenerOnButton(button1);
        setListenerOnButton(button2);
        setListenerOnButton(button3);
        setListenerOnButton(button4);
        setListenerOnButton(button5);
        setListenerOnButton(button6);

        /*Draggable.Nature drag1 = new Draggable.Nature(button1);
        Draggable.Nature drag2 = new Draggable.Nature(button2);
        Draggable.Nature drag3 = new Draggable.Nature(button3);
        Draggable.Nature drag4 = new Draggable.Nature(button4);
        Draggable.Nature drag5 = new Draggable.Nature(button5);
        Draggable.Nature drag6 = new Draggable.Nature(button6);*/
        ButtonsAnchor.getChildren().add(buttonAllStar);
        ButtonsAnchor.getChildren().add(buttonOFF);
        ButtonsAnchor.getChildren().add(button1);
        ButtonsAnchor.getChildren().add(button2);
        ButtonsAnchor.getChildren().add(button3);
        ButtonsAnchor.getChildren().add(button4);
        ButtonsAnchor.getChildren().add(button5);
        ButtonsAnchor.getChildren().add(button6);

        setDeleteDropOnButton(delButton);

    }

    public void setDeleteDropOnButton(Button source) {
        source.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != source && event.getDragboard().hasString()) {
                    Button sourceB = (Button) event.getGestureSource();
                    if (!sourceB.equals(button1) && !sourceB.equals(button2) && !sourceB.equals(button3)
                            && !sourceB.equals(button4) && !sourceB.equals(button5) && !sourceB.equals(button6)
                            && !sourceB.equals(buttonOFF) && !sourceB.equals(buttonAllStar)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                }

                event.consume();
            }

        });

        source.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != source
                        && event.getDragboard().hasString()) {
                    Button sourceB = (Button) event.getGestureSource();
                    if (!sourceB.equals(button1) && !sourceB.equals(button2) && !sourceB.equals(button3)
                            && !sourceB.equals(button4) && !sourceB.equals(button5) && !sourceB.equals(button6)
                            && !sourceB.equals(buttonOFF) && !sourceB.equals(buttonAllStar)) {
                        source.setStyle("fx-background-color: transparent;-fx-background-image: url('/GUI/delOpen.png')");
                    }
                }

                event.consume();
            }
        });

        source.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                source.setStyle("fx-background-color: transparent;-fx-background-image: url('/GUI/del.png')");

                event.consume();
            }
        });

        source.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    Button sourceB = (Button) event.getGestureSource();
                    //String txt = sourceB.getText();

                    if (sourceB.getText().equalsIgnoreCase("All Star")) {
                        b8Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("OFF")) {
                        b7Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("1")) {
                        b1Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("2")) {
                        b2Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("3")) {
                        b3Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("4")) {
                        b4Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("5")) {
                        b5Used--;
                    } else if (sourceB.getText().equalsIgnoreCase("6")) {
                        b6Used--;
                    }

                    l1.setText("1 Used: " + Integer.toString(b1Used));
                    l2.setText("2 Used: " + Integer.toString(b2Used));
                    l3.setText("3 Used: " + Integer.toString(b3Used));
                    l4.setText("4 Used: " + Integer.toString(b4Used));
                    l5.setText("5 Used: " + Integer.toString(b5Used));
                    l6.setText("6 Used: " + Integer.toString(b6Used));
                    l7.setText("OFF Used: " + Integer.toString(b7Used));
                    l8.setText("All Star Used: " + Integer.toString(b8Used));

                    for (int i = 0; i < table.getItems().size(); i++) {
                        if (tableSlot.getItems().get(i).getBut().equals(sourceB)) {
                            for (int j = 0; j < tableSlot.getItems().get(i).getNum(); j++) {
                                tableSlot.getItems().get(i + j).setColor(null);
                                tableSlot.getItems().get(i + j).setNumInsideBut(-1);
                                tableSlot.getItems().get(i + j).getBut().setText("");
                                deleterboy(tableSlot.getItems().get(i + j).getBut());
                            }
                        }
                    }
                    int transCount = 0;
                    for (int i = 0; i < table.getItems().size(); i++) {
                        if (table.getItems().get(i).getDay().equalsIgnoreCase("Friday")) {
                            if (i + 1 < table.getItems().size()) {

                                if (tableSlot.getItems().get(i).getNumInsideBut() != -1 && tableSlot.getItems().get(i + 1).getNumInsideBut() != -1 && tableSlot.getItems().get(i).getColor() != null && tableSlot.getItems().get(i + 1).getColor() != null) {
                                    if (!tableSlot.getItems().get(i).getColor().equals(tableSlot.getItems().get(i + 1).getColor())) {

                                        transCount++;
                                    } else if (tableSlot.getItems().get(i).getNumInsideBut() != tableSlot.getItems().get(i + 1).getNumInsideBut() - 1) {
                                        transCount++;
                                    }

                                }

                            }
                        }
                    }

                    TransitionCount = transCount;
                    trCount.setText("Transitions Count: " + TransitionCount);
                    //BlockUsed
                    BlocksUsed--;
                    blockUsed.setText("Blocks: " + Integer.toString(BlocksUsed));

                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }

        });

    }

    public ObservableList<Slot> getSlots() {

        ObservableList<Slot> ls = FXCollections.observableArrayList();
        ObservableList<SlotButton> lsb = FXCollections.observableArrayList();

        for (int i = 0; i < DateList.L.size(); i++) {
            String num = Integer.toString(i + 1);
            String[] str = DateList.L.get(i).split(" ");
            String date = str[0];
            String day = str[1];
            Button target = new Button();

            //tableSlot.setStyle("-fx-padding: 0 0 0 0;");
            target.setPrefSize(200, 30);
            target.setMaxSize(200, 30);
            target.setMinSize(200, 30);

            deleterboy(target);

            SlotButton sb = new SlotButton(target);
            /*if (isValidDay(day) == false) {
                sb.setIsOff(true);
                sb.setColor("Off Day");
            }*/
            lsb.add(sb);
            ls.add(new Slot(num, date, day));

        }

        for (int i = 0; i < TimeTable.tb.lsb.size() - 1; i++) {
            ls.get(i).setColor(TimeTable.tb.ls.get(i).getColor());
            ls.get(i).setDate(TimeTable.tb.ls.get(i).getDate());
            ls.get(i).setDay(TimeTable.tb.ls.get(i).getDay());
            ls.get(i).setEvent(TimeTable.tb.ls.get(i).getEvent());
            ls.get(i).setNum(TimeTable.tb.ls.get(i).getNum());

            String str = TimeTable.tb.lsb.get(i).getColor();
            if (str.equals("null")) {
                str = null;
            }

            lsb.get(i).setColor(str);
            lsb.get(i).setNum(TimeTable.tb.lsb.get(i).getNum());
            lsb.get(i).setNumInsideBut(TimeTable.tb.lsb.get(i).getNumInsideBut());

        }

        this.tableSlot.setItems(lsb);

        return ls;
    }

    public void deleterboy(Button target) {

        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                    int counter = 0;
                    boolean flag = true;
                    double buttonNumber = 0;

                    for (int i = 0; i < tableSlot.getItems().size(); i++) {
                        if (tableSlot.getItems().get(i).getBut().equals(target)) {
                            Button dumba = (Button) event.getGestureSource();
                            double l = dumba.getHeight();
                            buttonNumber = l / minSize;
                            if (dumba.getText().equalsIgnoreCase("All Star") || dumba.getText().equals("OFF")) {
                                buttonNumber = 1;
                            }
                            if (i + buttonNumber > tableSlot.getItems().size()) {
                                flag = false;
                            }
                            for (int lun = i; lun < i + buttonNumber && lun < tableSlot.getItems().size(); lun++) {
                                if (tableSlot.getItems().get(lun).getColor() != null) {
                                    flag = false;
                                }
                            }
                            for (int lun = 0; lun < tableSlot.getItems().size(); lun++) {
                                if (tableSlot.getItems().get(lun).getBut().equals(event.getGestureSource())) {
                                    l = tableSlot.getItems().get(lun).getNum();
                                    buttonNumber = l;

                                    if (i + buttonNumber > tableSlot.getItems().size()) {
                                        flag = false;
                                    }
                                    for (int lun2 = i; lun2 < i + buttonNumber && lun2 < tableSlot.getItems().size(); lun2++) {
                                        if (tableSlot.getItems().get(lun2).getColor() != null) {
                                            flag = false;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (TimeTable.tb.satTCheck == false) {
                        for (int i = 0; i < tableSlot.getItems().size(); i++) {
                            if (tableSlot.getItems().get(i).getBut().equals(target)) {
                                Button number = (Button) event.getGestureSource();
                                int number2 = (int) (number.getHeight() / minSize);

                                if (table.getItems().get(i).getDay().equalsIgnoreCase("Sunday")) {
                                    if (i - 1 >= 0 && tableSlot.getItems().get(i - 1).getColor() != null) {

                                        flag = false;
                                        break;
                                    }
                                } else if (number2 + i - 1 < tableSlot.getItems().size()) {
                                    if (table.getItems().get(i + number2 - 1).getDay().equalsIgnoreCase("saturday")) {
                                        if (i + 1 < tableSlot.getItems().size() && tableSlot.getItems().get(i + number2).getColor() != null) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                }

                            }

                        }
                    }
                    Button number = (Button) event.getGestureSource();
                    int number2 = (int) (number.getHeight() / minSize);
                    if (number2 == 7) {
                        for (int i = 0; i < tableSlot.getItems().size(); i++) {
                            if (tableSlot.getItems().get(i).getBut().equals(target)) {

                                if (table.getItems().get(i).getDay().equalsIgnoreCase("monday")) {
                                    if (TimeTable.tb.mon == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("tuesday")) {
                                    if (TimeTable.tb.tue == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("wednesday")) {
                                    if (TimeTable.tb.wed == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("thursday")) {
                                    if (TimeTable.tb.thur == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("friday")) {
                                    if (TimeTable.tb.fri == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("Saturday")) {
                                    if (TimeTable.tb.sat == false) {
                                        flag = false;
                                    }
                                } else if (table.getItems().get(i).getDay().equalsIgnoreCase("Sunday")) {
                                    if (TimeTable.tb.sun == false) {
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }
                    if (flag == true) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target
                        && event.getDragboard().hasString()) {
                    if (target.getStyle().equals("")) {

                        target.setStyle("-fx-background-color: grey;");
                    }
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (target.getStyle().equals("-fx-background-color: grey;")) {
                    target.setStyle("");

                }
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Button sourceB = (Button) event.getGestureSource();

                    setListenerOnButton2(target);

                    for (int i = 0; i < tableSlot.getItems().size(); i++) {
                        if (tableSlot.getItems().get(i).getBut().equals(target)) {
                            if (sourceB.getText().equalsIgnoreCase("OFF")) {
                                String st = sourceB.getStyle();
                                for (String color : butColors) {
                                    String s = st.toLowerCase();
                                    String ss = color.toLowerCase();
                                    if (s.contains(ss)) {
                                        tableSlot.getItems().get(i).setColor("Off Day");
                                        tableSlot.getItems().get(i).setNumInsideBut(0);
                                        break;
                                    }
                                }
                                tableSlot.getItems().get(i).setNum(1);
                            }
                            else if (sourceB.getText().equalsIgnoreCase("All Star")) {
                                String st = sourceB.getStyle();
                                for (String color : butColors) {
                                    String s = st.toLowerCase();
                                    String ss = color.toLowerCase();
                                    if (s.contains(ss)) {
                                        tableSlot.getItems().get(i).setColor(color);
                                        tableSlot.getItems().get(i).setNumInsideBut(0);
                                        break;
                                    }
                                }
                                tableSlot.getItems().get(i).setNum(1);
                            } else {
                                for (int j = 0; j < Integer.parseInt(sourceB.getText()); j++) {
                                    Button b = new Button();
                                    b.setMinSize(target.getWidth(), target.getHeight());
                                    b.setPrefSize(target.getWidth(), target.getHeight());
                                    String st = sourceB.getStyle();
                                    for (String color : butColors) {
                                        String s = st.toLowerCase();
                                        String ss = color.toLowerCase();
                                        if (s.contains(ss)) {
                                            tableSlot.getItems().get(i + j).setColor(color);
                                            tableSlot.getItems().get(i + j).setNumInsideBut(j);
                                            if (j != 0) {
                                                tableSlot.getItems().get(i + j).setBut(b);
                                            }
                                            break;
                                        }
                                    }
                                    tableSlot.getItems().get(i + j).setNum(Integer.parseInt(sourceB.getText()));
                                }
                            }

                            break;
                        }
                    }
                    int height = 0;
                    for (int i = 0; i < tableSlot.getItems().size(); i++) {
                        if (sourceB.equals(tableSlot.getItems().get(i).getBut())) {

                            height = tableSlot.getItems().get(i).getNum();
                            for (int j = i; j < i + height; j++) {
                                tableSlot.getItems().get(j).setNumInsideBut(-1);
                                tableSlot.getItems().get(j).setColor(null);
                                tableSlot.getItems().get(j).getBut().setText("");
                                //setListenerOnButton2(tableSlot.getItems().get(j).getBut());
                                deleterboy(tableSlot.getItems().get(j).getBut());

                            }
                            break;
                        }
                    }

                    int transCount = 0;
                    for (int i = 0; i < table.getItems().size(); i++) {
                        if (table.getItems().get(i).getDay().equalsIgnoreCase("Friday")) {
                            if (i + 1 < table.getItems().size()) {

                                if (tableSlot.getItems().get(i).getNumInsideBut() != -1 && tableSlot.getItems().get(i + 1).getNumInsideBut() != -1 && tableSlot.getItems().get(i).getColor() != null && tableSlot.getItems().get(i + 1).getColor() != null) {
                                    if (!tableSlot.getItems().get(i).getColor().equals(tableSlot.getItems().get(i + 1).getColor())) {

                                        transCount++;
                                    } else if (tableSlot.getItems().get(i).getNumInsideBut() != tableSlot.getItems().get(i + 1).getNumInsideBut() - 1) {
                                        transCount++;
                                    }

                                }

                            }
                        }
                    }

                    TransitionCount = transCount;
                    trCount.setText("Transitions Count: " + TransitionCount);

                    success = true;
                    tableSlot.refresh();
                }

                event.setDropCompleted(success);
                event.consume();
            }

        });

    }

    public boolean isValidDay(String day) {
        boolean toRet = true;

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        TimeTable tb = TimeTable.tb;

        if (day.equals(days[0])) {
            if (tb.mon) {
                toRet = false;
            }
        } else if (day.equals(days[1])) {
            if (tb.tue) {
                toRet = false;
            }
        } else if (day.equals(days[2])) {
            if (tb.wed) {
                toRet = false;
            }
        } else if (day.equals(days[3])) {
            if (tb.thur) {
                toRet = false;
            }
        } else if (day.equals(days[4])) {
            if (tb.fri) {
                toRet = false;
            }
        } else if (day.equals(days[5])) {
            if (tb.sat) {
                toRet = false;
            }
        } else if (day.equals(days[6])) {
            if (tb.sun) {
                toRet = false;
            }
        }

        return toRet;
    }

    public void setListenerOnButton(Button source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                String txt = "" + source.getText().toString();
                content.putString(txt);
                db.setContent(content);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    //source.setText("");

                    BlocksUsed++;
                    blockUsed.setText("Blocks Used: " + BlocksUsed);
                    if (source.getText().equalsIgnoreCase("All Star")) {
                        b8Used++;
                    } else if (source.getText().equalsIgnoreCase("OFF")) {
                        b7Used++;
                    } else if (source.getText().equalsIgnoreCase("1")) {
                        b1Used++;
                    } else if (source.getText().equalsIgnoreCase("2")) {
                        b2Used++;
                    } else if (source.getText().equalsIgnoreCase("3")) {
                        b3Used++;
                    } else if (source.getText().equalsIgnoreCase("4")) {
                        b4Used++;
                    } else if (source.getText().equalsIgnoreCase("5")) {
                        b5Used++;
                    } else if (source.getText().equalsIgnoreCase("6")) {
                        b6Used++;
                    }

                    l1.setText("1 Used: " + Integer.toString(b1Used));
                    l2.setText("2 Used: " + Integer.toString(b2Used));
                    l3.setText("3 Used: " + Integer.toString(b3Used));
                    l4.setText("4 Used: " + Integer.toString(b4Used));
                    l5.setText("5 Used: " + Integer.toString(b5Used));
                    l6.setText("6 Used: " + Integer.toString(b6Used));
                    l7.setText("OFF Used: " + Integer.toString(b7Used));
                    l8.setText("All Star Used: " + Integer.toString(b8Used));

                } else {
                    //*********************************THE BEEEP**********************************

                    java.awt.Toolkit.getDefaultToolkit().beep();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in placing the block");
                    alert.setContentText("Please Place the block in correct slot!");

                    alert.showAndWait();
                }
                event.consume();

            }
        });

    }

    public void setListenerOnButton2(Button source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();

                content.putString(source.getText());
                db.setContent(content);
                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag and drop gesture ended */
 /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                    source.setText("");
                } else {
                    //*********************************THE BEEEP**********************************
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error in placing the block");
                    alert.setContentText("Please Place the block in correct slot!");

                    alert.showAndWait();

                }
                event.consume();
            }

        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        s = new Stage();
    }

    @FXML
    private void onScroll(ScrollEvent event) {

    }

    @FXML
    private void onScrollFn(ScrollEvent event) {

    }

    @FXML
    private void onScrollSt(ScrollEvent event) {
    }

}
