package service;

import model.Admin;
import model.dto.AdminChangePasswordDto;
import model.dto.LoginUserDto;
import repository.AdminRepository;

import java.sql.SQLException;

public class AdminService {
  public static boolean login(LoginUserDto loginData) {
    Admin admin = AdminRepository.getByUsername(loginData.getStudentId());

    if (admin == null) {
      return false;
    }

    String password = loginData.getPassword();
    String salt = admin.getSalt();
    String hashedPassword = admin.getHashedPassword();

    if (!PasswordHasher.compareSaltedHash(password, salt, hashedPassword)) {
      return false;
    }

    AdminSession.getInstance(admin);
    return true;
  }

  public static boolean updatePassword(AdminChangePasswordDto saveData) {
    String username = AdminSession.getInstance(null).getUsername();
    Admin admin = AdminRepository.getByUsername(username);

    if (admin == null) {
      return false;
    }

    String oldPassword = saveData.getOldPassword();
    String newPassword = saveData.getNewPassword();

    String salt = PasswordHasher.generateSalt();
    String newHashedPassword = PasswordHasher.generateSaltedHash(newPassword, salt);

    if (!PasswordHasher.compareSaltedHash(oldPassword, admin.getSalt(), admin.getHashedPassword())) {
      return false;
    }

    return AdminRepository.updatePassword(admin.getId(), newHashedPassword, salt);
  }
}
