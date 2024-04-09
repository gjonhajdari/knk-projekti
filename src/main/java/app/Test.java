package app;

import repository.ConnectionUtil;

import java.sql.*;

public class Test {
  public static void main(String[] args) throws SQLException {
    printUser();
  }

  public static void printUser() throws SQLException {
    String sql = "SELECT * FROM student";

    Connection connection = ConnectionUtil.getConnection();
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);

    while (result.next()) {
      String resultId = result.getString("student_id");
      String resultFirstName = result.getString("first_name");
      String resultLastName = result.getString("last_name");

      System.out.println("ID: " + resultId);
      System.out.println("First Name: " + resultFirstName);
      System.out.println("Last Name: " + resultLastName);
    }
  }
}