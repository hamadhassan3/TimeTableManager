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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
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

    public int BlocksUsed = 0;
    public int TransitionCount = 0;
    //private Pane ButtonsAnchor;
    public Button button1 = new Button();
    Button button2 = new Button();
    Button button3 = new Button();
    Button button4 = new Button();
    Button button5 = new Button();
    Button button6 = new Button();

    
    final int minSize = 30;
    
    @FXML
    private Pane ButtonsAnchor;

    Date lastClickTime;

    /**
     * Initializes the controller class.
     */
    String color[] = {"Red", "Yellow", "Green", "Orange", "Purple", "Brown", "Blue", "No Color"};

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

    List<String> listofrows = new ArrayList<>();
    @FXML
    private TableView<SlotButton> tableSlot;
    @FXML
    private TableColumn<SlotButton, Button> colSlotT2;
    @FXML
    private Label blockUsed;
    @FXML
    private Label trCount;

    TableView<Slot> gettable() {
        return table;
    }

    TableView<SlotButton> getslottable() {
        return tableSlot;
    }

    public TimeTable getTb() {
        return tb;
    }

    public void setTb(TimeTable tb) {
        this.tb = tb;
    }

    private TimeTable tb;

    Slot click = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //MainScene.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(20, 20, 20), rgb(30, 60, 80));");
        /*for (int i = 0; i < DateList.L.size(); i++) {
            String a = DateList.L.get(i);
            tablefortime.getItems().add(a);
        }*/
        //button1.setText("6");
        colNo.setCellValueFactory(new PropertyValueFactory<Slot, String>("num"));
        colDate.setCellValueFactory(new PropertyValueFactory<Slot, String>("date"));
        colDay.setCellValueFactory(new PropertyValueFactory<Slot, String>("day"));

        colSlotT2.setCellValueFactory(new PropertyValueFactory<SlotButton, Button>("but"));

        table.setItems(getSlots());

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
        button1.setStyle("-fx-background-color: red;");

        button2.setText("5");
        button2.setStyle("-fx-background-color: blue;");

        button3.setText("4");
        button3.setStyle("-fx-background-color: grey;");

        button4.setText("3");
        button4.setStyle("-fx-background-color: yellow;");

        button5.setText("2");
        button5.setStyle("-fx-background-color: orange;");

        button6.setText("1");
        button6.setStyle("-fx-background-color: purple;");

        button1.setMinSize(200, minSize*6);        
        button1.setPrefSize(200, minSize*6);
        button1.setMaxSize(200, minSize*6);
        button2.setPrefSize(200, minSize*5);
        button2.setMinSize(200, minSize*5);
        button2.setMaxSize(200, minSize*5);
        button3.setMinSize(200, minSize*4);
        button3.setPrefSize(200, minSize*4);
        button3.setMaxSize(200, minSize*4);
        button4.setPrefSize(200, minSize*3);
        button4.setMaxSize(200, minSize*3);
        button4.setMinSize(200, minSize*3);
        button5.setPrefSize(200, minSize*2);
        button5.setMinSize(200, minSize*2);
        button5.setMaxSize(200, minSize*2);
        button6.setPrefSize(200, minSize);
        button6.setPrefSize(200, minSize);
        button6.setMaxSize(200, minSize);
        button1.setAlignment(Pos.BOTTOM_CENTER);
        button2.setAlignment(Pos.BOTTOM_CENTER);
        button3.setAlignment(Pos.BOTTOM_CENTER);
        button4.setAlignment(Pos.BOTTOM_CENTER);
        button5.setAlignment(Pos.BOTTOM_CENTER);
        button6.setAlignment(Pos.BOTTOM_CENTER);        

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
        ButtonsAnchor.getChildren().add(button1);
        ButtonsAnchor.getChildren().add(button2);
        ButtonsAnchor.getChildren().add(button3);
        ButtonsAnchor.getChildren().add(button4);
        ButtonsAnchor.getChildren().add(button5);
        ButtonsAnchor.getChildren().add(button6);

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
            
            

            target.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != target
                            && event.getDragboard().hasString()) {

                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
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

                        target.setStyle(sourceB.getStyle());//EventFilter(), this);getItems().set(0, db.get), source)db.getString());
                        target.getParent().setStyle(sourceB.getStyle());
                        target.setVisible(true);

                        //target.setMinSize(sourceB.getWidth(), sourceB.getHeight());
                        //target.setMaxSize(sourceB.getWidth(), sourceB.getHeight());
                        //target.setPrefSize(sourceB.getWidth(), sourceB.getHeight());

                        target.setText(sourceB.getText());
                        setListenerOnButton2(target);
                        

                        for(int i = 0; i<tableSlot.getItems().size(); i++){
                            if(tableSlot.getItems().get(i).getBut().equals(target)){
                                for(int j = 1; j < Integer.parseInt(target.getText()); j++){
                                    Button b = new Button();
                                    
                                    b.setPrefSize(target.getWidth(), target.getHeight());
                                    b.setStyle(target.getStyle());
                                    b.setText(target.getText());
                                    tableSlot.getItems().set(i+j, new SlotButton(b));
                                    //tableSlot.getItems().get(i+j).getBut().getParent().setStyle(sourceB.getStyle());
                                }
                            }
                        }

                        success = true;
                    }


                    BlocksUsed++;
                    blockUsed.setText("Blocks Used: " + BlocksUsed);
                    trCount.setText("Transitions Count: " + TransitionCount);
                    event.setDropCompleted(success);


                    event.consume();
                }
            });

            lsb.add(new SlotButton(target));
            ls.add(new Slot(num, date, day));

        }

        this.tableSlot.setItems(lsb);

        return ls;
    }

    public void setListenerOnButton(Button source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
 /* allow any transfer mode */
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
                /* the drag and drop gesture ended */
 /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    //source.setText("");
                }
                else{
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
                event.consume();

            }
        });



    }

    public void setListenerOnButton2(Button source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
 /* allow any transfer mode */

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
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
                }
                event.consume();

                source.setStyle("");
                source.setText("");

                source.setMaxHeight(minSize);
                source.setPrefHeight(minSize);
                source.setMinHeight(minSize);

            }

        });




    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //initalize();
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
