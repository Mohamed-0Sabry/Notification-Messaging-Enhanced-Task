package com.login;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private AnchorPane loginPanel;
    
    @FXML
    private VBox loginForm;
    
    @FXML
    private VBox signupForm;
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label toggleLabel;
    
    @FXML
    private Hyperlink toggleLink;
    
    @FXML
    private TextField loginEmail;
    
    @FXML
    private PasswordField loginPassword;
    
    @FXML
    private TextField signupName;
    
    @FXML
    private TextField signupEmail;
    
    @FXML
    private PasswordField signupPassword;
    
    @FXML
    private PasswordField signupConfirmPassword;

    @FXML
    private Button togglePasswordBtn;
    
    @FXML
    private Label togglePasswordIcon;
    
    @FXML
    private ImageView illustrationImage;

    private boolean isPanelVisible = false;
    private boolean isLoginForm = true;

    // For toggling password visibility
    private boolean passwordVisible = false;
    private TextField visiblePasswordField = new TextField();

    @FXML
    public void initialize() {
        // Set up the visible password field to match the style and layout
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setStyle(loginPassword.getStyle());
        visiblePasswordField.setFont(loginPassword.getFont());
        visiblePasswordField.setPromptText("");
        // Bind the text properties
        visiblePasswordField.textProperty().bindBidirectional(loginPassword.textProperty());
        // Add the visible password field to the parent of the password field
        ((javafx.scene.layout.HBox) loginPassword.getParent()).getChildren().add(0, visiblePasswordField);
    }

    @FXML
    public void handleLoginSlide(ActionEvent event) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), loginPanel);
        if (!isPanelVisible) {
            slide.setToX(-loginPanel.getWidth()); // Slide in
            isPanelVisible = true;
        } else {
            slide.setToX(0); // Slide out
            isPanelVisible = false;
        }
        slide.play();
    }
    
    @FXML
    public void handleButtonHover(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle() + "; -fx-background-color: derive(#3ac569, 20%);");
    }
    
    @FXML
    public void handleButtonExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button.getId() != null && button.getId().equals("openLoginBtn")) {
            button.setStyle("-fx-background-color: #ff3b3b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        } else {
            button.setStyle("-fx-background-color: #3ac569; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand; -fx-padding: 10;");
        }
    }
    
    @FXML
    public void toggleForms(ActionEvent event) {
        isLoginForm = !isLoginForm;
        loginForm.setVisible(isLoginForm);
        loginForm.setManaged(isLoginForm);
        signupForm.setVisible(!isLoginForm);
        signupForm.setManaged(!isLoginForm);
        
        if (isLoginForm) {
            welcomeLabel.setText("Welcome Back!");
            toggleLabel.setText("Don't have an account?");
            toggleLink.setText("Sign Up");
        } else {
            welcomeLabel.setText("Create Account");
            toggleLabel.setText("Already have an account?");
            toggleLink.setText("Log In");
        }
    }
    
    @FXML
    public void handleLogin(ActionEvent event) {
        String email = loginEmail.getText();
        String password = loginPassword.getText();
        
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }
        
        // TODO: Implement actual login logic here
        showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    public void handleSignup(ActionEvent event) {
        String name = signupName.getText();
        String email = signupEmail.getText();
        String password = signupPassword.getText();
        String confirmPassword = signupConfirmPassword.getText();
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match", Alert.AlertType.ERROR);
            return;
        }
        
        // TODO: Implement actual signup logic here
        showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
        toggleForms(event); // Switch back to login form
    }
    
    @FXML
    public void togglePasswordVisibility(ActionEvent event) {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            visiblePasswordField.setText(loginPassword.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            loginPassword.setVisible(false);
            loginPassword.setManaged(false);
            togglePasswordIcon.setText("🙈");
        } else {
            loginPassword.setText(visiblePasswordField.getText());
            loginPassword.setVisible(true);
            loginPassword.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            togglePasswordIcon.setText("👁");
        }
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
