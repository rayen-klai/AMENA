package amena.gui;

import amena.gui.DashboardClient.DashboardController;
import static amena.gui.ProfilController.semail;
import amena.model.User;
import amena.services.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static test.workshop.gui.ConfirmerController.ACCOUNT_SID;
import static test.workshop.gui.ConfirmerController.AUTH_TOKEN;

public class LoginAccountController implements Initializable {

    public static final String ACCOUNT_SID = "*********************";
    public static final String AUTH_TOKEN = "**********************";

    @FXML
    private TextField fxemail;
    @FXML
    private TextField fxmotpass;
    @FXML
    private Button fxconnetc;

    private User user;
    private UserService userService;
    @FXML
    private Button fxCreate;
    @FXML
    private Text fxoubier;
    @FXML
    private AnchorPane logpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Validate user input and show error messages if necessary.
     */
    private boolean validateInput() throws SQLException, NoSuchAlgorithmException, IOException {
        boolean isValid = true;
        String email = fxemail.getText();
        String password = fxmotpass.getText();
        password = hashPassword(password);

        UserService user = new UserService();

        // Validate email address
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
            isValid = false;
            fxemail.setStyle("-fx-border-color: red;");
        } else {
            fxemail.setStyle("");
        }

        // Validate password
        if (password.isEmpty()) {
            isValid = false;
            fxmotpass.setStyle("-fx-border-color: red;");
        } else {
            fxmotpass.setStyle("");
        }

        if (!user.FoundUser(email, password)) {
            isValid = false;
            User u = user.getUserByEmai(email);
            if (!u.isStatus()) {
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                // Envoi du SMS
                Message message = Message.creator(new PhoneNumber("+21625363115"),
                        new PhoneNumber("++12766630621"),
                        "Bonjour,vous pouvez activer votre compte s il vous plait !").create();

                // Affichage du SID du message dans une fenêtre de dialogue
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation de livraison");
                alert.setHeaderText(null);
                alert.setContentText("Le message a été envoyé avec succès ! SID : " + message.getSid());
                alert.showAndWait();

                // Redirect to activate account page
                isValid = false;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivationC.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        }

        return isValid;

    }

    /*
    @FXML
    private void handleConnectButtonAction() throws IOException, SQLException, NoSuchAlgorithmException {
        if (validateInput()) {

            //User u = userService.getUserByEmai(email); // Récupérer l'utilisateur connecté
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profil.fxml"));

            Parent root = loader.load();
            ProfilController dc = loader.getController();
            String email = fxemail.getText();

            dc.setUserInformation(email);

            // Create a new scene with the loaded FXML file and show it
            Scene scene = new Scene(root);
            Stage stage = (Stage) fxconnetc.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }}

        /*/
    @FXML
    private void handleConnectButtonAction() throws IOException, SQLException, NoSuchAlgorithmException {
        String email = fxemail.getText();
        ProfilController.semail = email;
        System.out.println(email);
        UserService u = new UserService();
        User user = u.getUserByEmai(email);
        System.out.println(user);

        if (validateInput()) {

            System.out.println(email);

            System.out.println(user.getRole().equals("Admin"));
            if (user != null) {
                FXMLLoader loader;
                Parent root;
                if (user.getRole().equals("Transporteur")) {
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("DashboardTransporteur/dashboard.fxml"));
                    Parent root2 = loader2.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root2));
                    stage.show();
                    return ;
                } 
                if (user.getRole().equals("Client")) {

                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("DashboardClient/dashboard.fxml"));
                    Parent root2 = loader2.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root2));
                    stage.show();
                   
                    return ;
                }
             if (user.getRole().equals("Admin")) {
                System.out.println("aaa");
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("dashboard/First3.fxml"));
                    Parent root2 = loader2.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root2));
                    stage.show();
                    return ;
                
            }

            }}
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encodedhash);
    }

    @FXML
    private void CretateAccount(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void ForgetPassword(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgetPasword.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
