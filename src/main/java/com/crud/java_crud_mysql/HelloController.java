package com.crud.java_crud_mysql;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.crud.java_crud_mysql.MyJDBC.DBConnect;

public class HelloController {
    @FXML
    private TextField Dname;
    @FXML
    private TextField Dnumber;

    @FXML
    private TextField Dnumber_DL;

    @FXML
    private TextField Mgr_ssn;
    @FXML
    private TextField Mgr_start_due;

    @FXML
    private TextField Dlocation_DL;

    @FXML
    private Label info;

    // update the label with the current time
    @FXML
    protected void onTimeButtonClick() {
        Dname.setText(String.format("The time is %tT", System.currentTimeMillis()));
    }

    @FXML
    protected void onSearchClick() {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            // Clear the Fields
            clearD();

            if (Dnumber.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber");
            } else {
                if (isInt(Dnumber, "Please input number for Dnumber")) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM department WHERE Dnumber = " + Dnumber.getText());
                    if (resultSet.next()) {
                        Dname.setText(resultSet.getString("Dname"));
                        Mgr_ssn.setText(resultSet.getString("Mgr_ssn"));
                        Mgr_start_due.setText(resultSet.getString("Mgr_start_due"));
                    } else {
                        info.setText("Info : No such department");
                    }
                } else {
                    info.setText("Info : Invalid Dnumber");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearD() {
        // clear the info label
        info.setText("Info : ");
        // clear Dname, Mgr_ssn, Mgr_start_due
        Dname.setText("");
        Mgr_ssn.setText("");
        Mgr_start_due.setText("");
    }

    public void onInsertClick(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (Dname.getText().isEmpty()) {
                info.setText("Info : Please enter a DName");
            } else if (Dnumber.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber");
            }
            else if (Mgr_ssn.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_ssn");
            }
            else if (Mgr_start_due.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_start_due");
            }
            else {
                if (isInt(Dnumber, "Please input number for Dnumber")) {
                    if (isInt(Mgr_ssn, "Please input number for Mgr_ssn") && isValidSsn("Please input 9 digits for Mgr_ssn")) {
                        if (isDate(Mgr_start_due, "Please input valid date for Mgr_start_due")) {
                            statement.executeUpdate("INSERT INTO department VALUES ('" + Dname.getText() + "','" + Integer.parseInt(Dnumber.getText()) + "','" + Integer.parseInt(Mgr_ssn.getText()) + "','" + Mgr_start_due.getText() + "')");
                            info.setText("Info : Inserted");
                        }
                    }
                }
            }

            //statement.executeUpdate("INSERT INTO department VALUES ('" + Dname.getText() + "','" + Integer.parseInt(Dnumber.getText()) + "','" + Integer.parseInt(Mgr_ssn.getText()) + "','" + Mgr_start_due.getText() + "')");
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidSsn(String msg) {
        if (Mgr_ssn.getText().length() == 9) {
            return true;
        } else {
            info.setText("Info : " + msg);
            return false;
        }
    }

    public void onUpdateClick(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE department SET Dname = '" + Dname.getText() + "', Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "', Mgr_ssn = '" + Integer.parseInt(Mgr_ssn.getText()) + "', Mgr_start_due = '" + Mgr_start_due.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "'");
            System.out.println("Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM department WHERE Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "'");
            System.out.println("Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onSearchClick_DL(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (isInt(Dnumber_DL, "Please input integer for Dnumber")) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM dept_location WHERE Dnumber = " + Integer.parseInt(Dnumber_DL.getText()));
                while (resultSet.next()) {
                    Dlocation_DL.setText(resultSet.getString("Dlocation"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void onInsertClick_DL(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (isInt(Dnumber_DL, "Please input integer for Dnumber")) {
                statement.executeUpdate("INSERT INTO dept_location VALUES ('" + Integer.parseInt(Dnumber_DL.getText()) + "','" + Dlocation_DL.getText() + "')");
                System.out.println("Inserted");
            }
            //statement.executeUpdate("INSERT INTO dept_location VALUES ('" + Integer.parseInt(Dnumber_DL.getText()) + "','" + Dlocation_DL.getText() + "')");
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateClick_DL(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (isInt(Dnumber_DL, "Please input integer for Dnumber")) {
                statement.executeUpdate("UPDATE dept_location SET Dlocation = '" + Dlocation_DL.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
                System.out.println("Updated");
            }
            //statement.executeUpdate("UPDATE dept_location SET Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "', Dlocation = '" + Dlocation_DL.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
            System.out.println("Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteClick_DL(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (isInt(Dnumber_DL, "Please input integer for Dnumber")) {
                statement.executeUpdate("DELETE FROM dept_location WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
                System.out.println("Deleted");
            }
            //statement.executeUpdate("DELETE FROM dept_location WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
            System.out.println("Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isInt(TextField f, String msg) {
        try {
            Integer.parseInt(f.getText());
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Info : " + msg);
            info.setText("Info : " + msg);
            return false;
        }
    }

    private boolean isDate(TextField f, String msg) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.parse(f.getText());
            return true;
        } catch (ParseException e) {
            System.out.println("Info : " + msg);
            info.setText("Info : " + msg);
            return false;
        }
    }

//    // on key pressed event in the text field
//    @FXML
//    protected void onKeyPressedDnumber_DL(ActionEvent actionEvent) {
//        Dnumber.setText(Dnumber_DL.getText().toUpperCase());
//    }
//
//    public static void numericOnly (javafx.scene.control.TextField textField) {
//        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                textField.setText(newValue.replaceAll("[^\\d]", ""));
//            }
//        });
//    }


//    public class DateValidatorUsingDateFormat implements DateValidator {
//        private String dateFormat;
//
//        public DateValidatorUsingDateFormat(String dateFormat) {
//            this.dateFormat = dateFormat;
//        }
//
//        @Override
//        public boolean isValid(String dateStr) {
//            DateFormat sdf = new SimpleDateFormat(this.dateFormat);
//            sdf.setLenient(false);
//            try {
//                sdf.parse(dateStr);
//            } catch (ParseException e) {
//                return false;
//            }
//            return true;
//        }
//    }
}