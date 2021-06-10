package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Models.WeatherManager;

public class SampleController {
    private WeatherManager wm = new WeatherManager("");
    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;

    @FXML
    private Label day0;
    @FXML
    private Label day1;
    @FXML
    private Label day2;
    @FXML
    private Label day3;
    @FXML
    private Label day4;

    @FXML
    private Label day01;
    @FXML
    private Label day11;
    @FXML
    private Label day21;
    @FXML
    private Label day31;
    @FXML
    private Label day41;
    @FXML
    private Label pres0;
    @FXML
    private Label pres1;
    @FXML
    private Label pres2;
    @FXML
    private Label pres3;
    @FXML
    private Label pres4;
    @FXML
    private Label maxPres;

    @FXML
    private Label temp011;
    @FXML
    private Label temp111;
    @FXML
    private Label temp211;
    @FXML
    private Label temp311;
    @FXML
    private Label temp411;
    @FXML
    private Label finDay;

    @FXML
    private Label forecast;
    @FXML
    private Label atmsTit;
    @FXML
    private Label maxPr;
    @FXML
    private Label minRazn;
    @FXML
    private Label raznDa;


    public void buttonCLicked() {
        cityInput();
    }

    public void cityInput() {
        if (wm.getWeather(latitude.getText().toString(), longitude.getText().toString())) outputWeather();
        outputWeather();
    }

    public void outputWeather() {


        day0.setText(wm.str.get(0));
        day1.setText(wm.str.get(1));
        day2.setText(wm.str.get(2));
        day3.setText(wm.str.get(3));
        day4.setText(wm.str.get(4));

        day01.setText(wm.str.get(0));
        day11.setText(wm.str.get(1));
        day21.setText(wm.str.get(2));
        day31.setText(wm.str.get(3));
        day41.setText(wm.str.get(4));

        pres0.setText(wm.temp0.get(0));
        pres1.setText(wm.temp0.get(1));
        pres2.setText(wm.temp0.get(2));
        pres3.setText(wm.temp0.get(3));
        pres4.setText(wm.temp0.get(4));

        temp011.setText(wm.razn.get(0).toString());
        temp111.setText(wm.razn.get(1).toString());
        temp211.setText(wm.razn.get(2).toString());
        temp311.setText(wm.razn.get(3).toString());
        temp411.setText(wm.razn.get(4).toString());

        maxPres.setText(wm.max.toString());
        finDay.setText(wm.convertUTC(wm.minDay));

        forecast.setVisible(true);
        atmsTit.setVisible(true);
        maxPr.setVisible(true);
        minRazn.setVisible(true);
        raznDa.setVisible(true);


    }
}
