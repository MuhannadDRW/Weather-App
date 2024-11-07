package com.example.weather;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class HelloApplication extends Application {

    TextField tf_city;


    Label lb_temperatureValue = new Label();
    Label lb_windSpeedValue = new Label();
    Label lb_humidityValue = new Label();
    Label lb_weatherStateValue = new Label();

    CheckBox cb_americanSys;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        // First section in the Scene

        // message
        Label lb_text = new Label("Weather Application");
        lb_text.setFont(new Font("Times new Romain",45));


        HBox lb_box = new HBox(lb_text);
        lb_box.setAlignment(Pos.TOP_CENTER);
        lb_box.setMargin(lb_text, new Insets(20,0,0,0));

        // -----------------------------------------------------------------------------------------------

        /*  Second section in the Scene
            search button, and check if the user want to get the weather info
            in US system or the other system.
            make text field to get the city name by it
        */

        // Search Button
        Button btn_search = new Button("Search");
        btn_search.setPrefSize(100, 10);
        btn_search.setOnAction(e-> searchBTN());

        cb_americanSys = new CheckBox("American System");

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(cb_americanSys, btn_search);


        Label lb_city = new Label("Enter city name:");
        lb_city.setFont(new Font("Times new Romain",20));

        tf_city = new TextField();
        tf_city.setPromptText("Enter city name");
        tf_city.setPrefSize(150, 30);

        VBox vCity  = new VBox(10);
        vCity.getChildren().addAll(lb_city,tf_city);


        HBox searchPart = new HBox(30);
        searchPart.getChildren().addAll(vCity, vBox);
        searchPart.setAlignment(Pos.TOP_CENTER);
        searchPart.setMargin (btn_search,new Insets(20,0,0,10));
        searchPart.setMargin (tf_city,new Insets(20,10,0,0));
        // ------------------------------------------------------------------------------------

        // Third section in the Scene (shows the weather info)


        Label lb_humidity = new Label("Humidity: ");
        lb_humidity.setFont(new Font("Times new Romain",25));

        // to group humidity with its value
        HBox hHumidity = new HBox(20);
        hHumidity.getChildren().addAll(lb_humidity, lb_humidityValue);

        Label lb_temperature = new Label("temperature: ");
        lb_temperature.setFont(new Font("Times new Romain",25));

        // to group temp with its value
        HBox hTemp = new HBox(20);
        hTemp.getChildren().addAll(lb_temperature, lb_temperatureValue);


        // To group humidity & temperature
        VBox vHumidity_temp = new VBox(50);
        vHumidity_temp.getChildren().addAll(hHumidity, hTemp);



        Label lb_windSpeed = new Label("Wind Speed: ");
        lb_windSpeed.setFont(new Font("Times new Romain",25));

        // to group wind Speed with its value
        HBox hWinSpeed = new HBox(20);
        hWinSpeed.getChildren().addAll(lb_windSpeed, lb_windSpeedValue);


        Label lb_weatherState = new Label("Weather State: ");
        lb_weatherState.setFont(new Font("Times new Romain",25));

        // to group weather state with its value
        HBox hState = new HBox(20);
        hState.getChildren().addAll(lb_weatherState, lb_weatherStateValue);

        // To group Wind Speed & Weather State
        VBox vWinSpe_weathStat = new VBox(50);
        vWinSpe_weathStat.getChildren().addAll(hWinSpeed,hState);



        // To group previous groups side by side
        HBox hBeforeRoot = new HBox(100);
        hBeforeRoot.getChildren().addAll(vHumidity_temp,vWinSpe_weathStat);
        hBeforeRoot.setAlignment(Pos.CENTER);
        // ------------------------------------------------------------------------------

        // Last section of the Scene (History Button)

        Button btn_history = new Button("History Search");
        btn_history.setPrefSize(115, 10);
        btn_history.setOnAction(e-> historyBTN());

        HBox hHistory = new HBox(60);
        hHistory.getChildren().addAll(btn_history);
        hHistory.setAlignment(Pos.BOTTOM_CENTER);

        //-------------------------------------------------------------------------------------

        // The main Node
        VBox root = new VBox(20);
        root.getChildren().addAll(lb_box,searchPart,hBeforeRoot,hHistory);
        root.setMargin(hBeforeRoot, new Insets(80));
        root.setMargin(searchPart, new Insets(20,0,0,0));

        // make scene and stage
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Weather App");
        stage.setResizable(false); // to  prevent change the window size
        stage.setScene(scene);
        stage.show();
    }

    public void searchBTN(){
        String city = tf_city.getText();
        if(isAlpha(city)){
            if(GetAPI.request(city, cb_americanSys) == 0) {
               String dgree = "Cْ";
               String speed = "m/s";

               if(cb_americanSys.isSelected()){
                   dgree = "Fْ";
                   speed = "mph";
               }

               lb_humidityValue.setText(Weather.getSearchHistory().getLast().getHumidity() + " %");
               lb_humidityValue.setFont(new Font("Times new Romain",25));


               lb_temperatureValue.setText(String.format(Locale.ENGLISH,
                       "%.2f", Weather.getSearchHistory().getLast().getTemp()) + dgree);
               lb_temperatureValue.setFont(new Font("Times new Romain",25));


               lb_weatherStateValue.setText(Weather.getSearchHistory().getLast().getState());
               lb_weatherStateValue.setFont(new Font("Times new Romain",25));


               lb_windSpeedValue.setText(String.format(Locale.ENGLISH,"%.2f",
                       Weather.getSearchHistory().getLast().getWindSpeed()) + speed);
               lb_windSpeedValue.setFont(new Font("Times new Romain",25));

            }else {requestError();}
        }

        else {nameError(city);}
    }

    public void historyBTN(){

        TableColumn<Weather, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setPrefWidth(100);

        TableColumn<Weather, String> city = new TableColumn<>("City");
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        city.setPrefWidth(100);

        TableColumn<Weather, Boolean> isUsSys = new TableColumn<>("US System");
        isUsSys.setCellValueFactory(new PropertyValueFactory<>("usSys"));
        isUsSys.setPrefWidth(100);

        TableColumn<Weather, Double> temp = new TableColumn<>("Temperature");
        temp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        temp.setPrefWidth(100);

        TableColumn<Weather, Double> windSpeed = new TableColumn<>("Wind Speed");
        windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        windSpeed.setPrefWidth(100);

        TableColumn<Weather, Double> humidity = new TableColumn<>("Humidity");
        humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        humidity.setPrefWidth(100);

        TableColumn<Weather, String> state = new TableColumn<>("State");
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        state.setPrefWidth(100);

        TableView<Weather> table = new TableView<>();
        table.setMaxWidth(700);
        table.setMaxHeight(800);

        table.getColumns().addAll(date, city, isUsSys, temp, windSpeed, humidity, state);
        table.setPadding(new Insets(10));

        table.setItems(Weather.getSearchHistory());

        Scene archivesScene = new Scene(table, 720, 500);

        Stage stage = new Stage();
        stage.setTitle("Archives");
        stage.setScene(archivesScene);
        stage.setResizable(false); // to  prevent change the window size
        stage.show();
    }

    public void requestError(){
        Label lb_message = new Label("Server Error: city not found");
        lb_message.setFont(new Font("Times new Romain", 20));
        lb_message.setTextFill(Color.RED);
        lb_message.setAlignment(Pos.CENTER);

        Scene requestErrorScene = new Scene(lb_message, 400,150);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(requestErrorScene);
        stage.setTitle("Server Error");
        stage.show();
    }

    public void nameError(String cityName){

        Label lb_message  =new Label("Invalid City Name");
        lb_message.setFont(new Font("Times new Romain", 20));
        lb_message.setTextFill(Color.RED);
        lb_message.setAlignment(Pos.CENTER);

        Scene nameErrorScene = new Scene(lb_message, 400,150);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(nameErrorScene);
        stage.setTitle("Invalid Name");
        stage.show();
    }

    public boolean isAlpha(String n){
        for(int i = 0; i<n.length(); i++){
            if(n.charAt(i) == ' '){
                continue;
            }
            else if (!Character.isLetter(n.charAt(i))){
                return false;
            }
        }
        return true;
    }

}