package sample.Controllers;

import javafx.fxml.FXML;

import java.awt.*;
import javafx.scene.control.TextField;
import sample.Models.WeatherManager;

public class SampleController {
    WeatherManager wm;
    @FXML
    private TextField textCity;

    public void buttonCLicked(){
        cityInput();
    }
    public void cityInput(){
         wm = new WeatherManager(textCity.getText());
    }
}
