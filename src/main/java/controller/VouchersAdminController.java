package controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import service.AdminSession;
import service.Navigator;

public class VouchersAdminController {
    @FXML
    private void handleDashboardClick(MouseEvent me) { Navigator.navigate(me, Navigator.DASHBOARD_ADMIN); }
    @FXML
    private void handleCreateClick(MouseEvent me) { Navigator.navigate(me, Navigator.CREATE_VOUCHER); }
    @FXML
    private void handleProfileClick(MouseEvent me) { Navigator.navigate(me, Navigator.PROFILE_ADMIN); }
    @FXML
    private void handleLogoutClick(MouseEvent me) {
      AdminSession.getInstance(null).cleanAdminSession();
      Navigator.navigate(me, Navigator.LOGIN_PAGE_ADMIN);
    }
}
