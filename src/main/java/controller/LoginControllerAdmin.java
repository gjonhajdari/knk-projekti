package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.dto.LoginUserDto;
import service.AdminService;
import service.Navigator;
import utils.Validator;

import java.sql.SQLException;

public class LoginControllerAdmin {
  @FXML
  private TextField txtUsername;
  @FXML
  private PasswordField pwdPassword;
  @FXML
  private Button btnLogin;
  @FXML
  private Text txtErrorMessage;

  @FXML
  private void initialize() {
    EventHandler<KeyEvent> enterKeyHandler = event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          handleLoginAdmin();
          if (txtErrorMessage.getText().isEmpty()) {
            Navigator.navigate(event, Navigator.DASHBOARD_ADMIN);
          }
        } catch (SQLException sqle) {
          sqle.printStackTrace();
        }
      }
    };

    pwdPassword.setOnKeyPressed(enterKeyHandler);
    btnLogin.setOnKeyPressed(enterKeyHandler);
  }

  @FXML
  private void handleLoginAdminClick(MouseEvent event) throws SQLException {
    handleLoginAdmin();
    if (txtErrorMessage.getText().isEmpty()) {
      Navigator.navigate(event, Navigator.DASHBOARD_ADMIN);
    }
  }

  private void handleLoginAdmin() throws SQLException {
    if (Validator.isEmpty(txtUsername.getText(), pwdPassword.getText())) {
      txtErrorMessage.setText("Please fill in all fields");
      return;
    }

    LoginUserDto loginUserDto = new LoginUserDto(
      Validator.clearSpaces(this.txtUsername.getText()),
      Validator.clearSpaces(this.pwdPassword.getText())
    );

    boolean isLogin = AdminService.login(loginUserDto);

    if(!isLogin) {
      txtErrorMessage.setText("Username or password is incorrect");
      return;
    }

    txtErrorMessage.setText("");
  }
}
