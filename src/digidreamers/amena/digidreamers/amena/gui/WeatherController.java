/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digidreamers.amena.gui;

import digidreamers.amena.services.JokesApiService;
import digidreamers.amena.services.WeatherApiService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ahlem
 */
public class WeatherController implements Initializable {

    @FXML
    private Label weatherLabel;
    @FXML
    private Button myButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void refreshWeatherClick(ActionEvent event) {
                try {
            Weather weatherApi = new Weather();
            String we = weatherApi.Weather();
            Label responseLabel = new Label(we.toString());

            weatherLabel.setText(we);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
