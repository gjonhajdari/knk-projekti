package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.dto.AdminChangePasswordDto;
import service.AdminService;
import service.AdminSession;
import service.Navigator;
import utils.Validator;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfileAdminController {
  @FXML
  private PasswordField pwdOldPassword;
  @FXML
  private PasswordField pwdNewPassword;
  @FXML
  private PasswordField pwdConfirmPassword;
  @FXML
  private ToggleGroup languageGroup;
  @FXML
  private RadioButton radioAlb;
  @FXML
  private RadioButton radioEng;
  @FXML
  private Button btnSave;
  @FXML
  private Text txtErrorMessage;
  @FXML
  private Text txtSuccessMessage;

  @FXML
  private void initialize() {
    // Set up the Save button to handle Enter key press
    btnSave.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          handleSaveClick(null);
        } catch (SQLException sqle) {
          sqle.printStackTrace();
        }
      }
    });

    // Set up the language radio buttons with a toggle group
    languageGroup = new ToggleGroup();
    radioAlb.setToggleGroup(languageGroup);
    radioEng.setToggleGroup(languageGroup);
    radioAlb.setUserData("sq");
    radioEng.setUserData("en");

    // Add a listener to handle language change
    languageGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        String selectedLanguage = newValue.getUserData().toString();
        changeLanguage(selectedLanguage);
      }
    });

    // Set the selected language based on the current locale
    String currentLanguage = Locale.getDefault().getLanguage();
    if (currentLanguage.equals("sq")) {
      radioAlb.setSelected(true);
    } else {
      radioEng.setSelected(true);
    }
  }

  private void changeLanguage(String languageCode) {
    Locale locale = new Locale(languageCode);
    Locale.setDefault(locale);
    ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
    Navigator.reloadCurrentScene(bundle);
  }

  @FXML
  private void handleSaveClick(MouseEvent me) throws SQLException {
    if (Validator.isEmpty(pwdOldPassword.getText(), pwdNewPassword.getText(), pwdConfirmPassword.getText())) {
      txtErrorMessage.setText("Please fill in all fields.");
      return;
    }

    AdminChangePasswordDto adminSaveDto = new AdminChangePasswordDto(
            pwdOldPassword.getText(),
            pwdNewPassword.getText()
    );

    boolean isSaved = AdminService.updatePassword(adminSaveDto);

    if (!isSaved) {
      txtErrorMessage.setText("Password is incorrect.");
      return;
    }

    if (!pwdNewPassword.getText().equals(pwdConfirmPassword.getText())) {
      txtErrorMessage.setText("Passwords do not match.");
      return;
    }

    txtSuccessMessage.setText("Password changed successfully");
    txtErrorMessage.setText("");
  }

  @FXML
  private void handleDashboardClick(MouseEvent me) {
    Navigator.navigate(me, Navigator.DASHBOARD_ADMIN);
  }

  @FXML
  private void handleVouchersClick(MouseEvent me) {
    Navigator.navigate(me, Navigator.VOUCHERS_ADMIN);
  }

  @FXML
  private void handleCreateClick(MouseEvent me) {
    Navigator.navigate(me, Navigator.CREATE_VOUCHER);
  }

  @FXML
  private void handleHelpClick(MouseEvent me) {
    Navigator.navigate(me, Navigator.HELP_ADMIN);
  }

  @FXML
  private void handleLogoutClick(MouseEvent me) {
    AdminSession.clearAdminSession();
    Navigator.navigate(me, Navigator.HOME_PAGE);
  }
}
