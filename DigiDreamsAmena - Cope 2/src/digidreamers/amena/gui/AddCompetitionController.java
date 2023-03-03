/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digidreamers.amena.gui;

import digideramers.amena.models.Competition;
import digidreamers.amena.services.CompetitionCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ahlem
 */
public class AddCompetitionController implements Initializable {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfDate_deb;
    @FXML
    private TextField tfDate_fin;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfNbp;
    @FXML
    private AnchorPane pane2;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnRetourner;

    public boolean verif_Num(String num) {
        int i = 0;
        for (i = 0; i < num.length(); i++) {
            if (Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean verif_Num2(String num) {
        int i = 0;
        for (i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean testTitle(String tit) {

        if (tit.length() == 0) {
            return false;
        }
        if (!verif_Num(tit)) {
            return false;
        }

        return true;

    }

    public static boolean estDateValide(String date) {
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void saveCompetition(ActionEvent event) {

        try {
            String title = tfTitle.getText();
            System.out.println(testTitle(title));

            if (!testTitle(title) || !verif_Num2(tfType.getText()) || !verif_Num2(tfNbp.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Vérifiez vos champs !");
                alert.showAndWait();
            } else {
                Date date_deb = Date.valueOf(tfDate_deb.getText());
                Date date_fin = Date.valueOf(tfDate_fin.getText());

                int type = Integer.parseInt(tfType.getText());

                int nbp = Integer.parseInt(tfNbp.getText());
                Competition c = new Competition(title, date_deb, date_fin, type, nbp);
                CompetitionCRUD cr = new CompetitionCRUD();
                cr.ajouter(c);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionGamification.fxml"));
                Parent root = loader.load();
                GestionGamificationController dcc = loader.getController();
                tfTitle.getScene().setRoot(root);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }

    }

    @FXML
    private void retournerversComp(ActionEvent event) {
        try {
            Parent sv;
            sv = (AnchorPane) FXMLLoader.load(getClass().getResource("GestionGamification.fxml"));
            pane2.getChildren().removeAll();
            pane2.getChildren().setAll(sv);
        } catch (IOException ex) {
            Logger.getLogger(GestionGamificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
