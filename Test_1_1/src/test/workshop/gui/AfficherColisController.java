/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.workshop.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import test.workshop.model.Colis;
import test.workshop.services.ColisCRUD;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AfficherColisController implements Initializable {

    @FXML
    private ListView<Colis> docListView;
    @FXML
    private Button afficherBtn;
    @FXML
    private Button quitterBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAfficherBtn(ActionEvent event) {
      
        ColisCRUD c = new ColisCRUD();
        
        List<Colis> list=c.afficher2();
        ObservableList<Colis> observableList = FXCollections.observableArrayList(list);
        docListView.setItems(observableList);
    }

    @FXML
    private void handleQuitterBtn(ActionEvent event) {
                 Stage stage = (Stage) quitterBtn.getScene().getWindow();
        stage.close();
    }
    
}
