package com.crud.java_crud_mysql;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TextArea Dlocation_List;

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
            // clear the info label
            info.setText("Info : ");
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
        // clear Dname, Mgr_ssn, Mgr_start_due
        Dname.setText("");
        Mgr_ssn.setText("");
        Mgr_start_due.setText("");
    }

    private void clearDL() {
        // clear Dnumber_DL, Dlocation_DL
        Dnumber_DL.setText("");
        Dlocation_DL.setText("");
        Dlocation_List.setText("");
    }

    public void onInsertClick(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (Dname.getText().isEmpty()) {
                info.setText("Info : Please enter a DName");
            } else if (Dnumber.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber");
            } else if (Mgr_ssn.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_ssn");
            } else if (Mgr_start_due.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_start_due");
            } else {
                if (isInt(Dnumber, "Please input number for Dnumber")) {
                    if (isInt(Mgr_ssn, "Please input number for Mgr_ssn") && isValidSsn("Please input 9 digits for Mgr_ssn")) {
                        if (isDate(Mgr_start_due, "Please input valid date for Mgr_start_due")) {
                            // Find if the Dnumber is already in the database
                            if (isDnumberExist(Dnumber.getText())) {
                                info.setText("Info : Error The Dnumber is already in the database. Try another number.");
                            } else {
                                statement.executeUpdate("INSERT INTO department VALUES ('" + Dname.getText() + "','" + Integer.parseInt(Dnumber.getText()) + "','" + Integer.parseInt(Mgr_ssn.getText()) + "','" + Mgr_start_due.getText() + "')");
                                info.setText("Info : Inserted successfully");
                            }
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

    private boolean isDnumberExist(String text) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department WHERE Dnumber = " + text);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isDLnumberExist(String text) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dept_location WHERE Dnumber = " + text);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
            if (Dnumber.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be updated");
            } else if (Dname.getText().isEmpty()) {
                info.setText("Info : Please enter a DName");
            } else if (Mgr_ssn.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_ssn");
            } else if (Mgr_start_due.getText().isEmpty()) {
                info.setText("Info : Please enter a Mgr_start_due");
            } else {
                if (!isDnumberExist(Dnumber.getText())) {
                    info.setText("Info : Error The Dnumber is not in the database. Try another number.");
                } else {
                    if (isInt(Dnumber, "Please input number for Dnumber")) {
                        if (isInt(Mgr_ssn, "Please input number for Mgr_ssn") && isValidSsn("Please input 9 digits for Mgr_ssn")) {
                            if (isDate(Mgr_start_due, "Please input valid date for Mgr_start_due")) {
                                statement.executeUpdate("UPDATE department SET Dname = '" + Dname.getText() + "', Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "', Mgr_ssn = '" + Integer.parseInt(Mgr_ssn.getText()) + "', Mgr_start_due = '" + Mgr_start_due.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "'");
                                info.setText("Info : Updated successfully");
                            }
                        }
                    }
                }
            }
            // statement.executeUpdate("UPDATE department SET Dname = '" + Dname.getText() + "', Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "', Mgr_ssn = '" + Integer.parseInt(Mgr_ssn.getText()) + "', Mgr_start_due = '" + Mgr_start_due.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "'");
            System.out.println("Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            if (Dnumber.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be deleted");
            } else {
                if (!isDnumberExist(Dnumber.getText())) {
                    info.setText("Info : Error The Dnumber is not in the database. Try another number.");
                } else if (isDLnumberExist(Dnumber.getText())) {
                    info.setText("Info : Error The Dnumber is in the dept_location table. Try after deleting that entry.");
                } else {
                    if (isInt(Dnumber, "Please input number for Dnumber")) {
                        statement.executeUpdate("DELETE FROM department WHERE Dnumber = '" + Integer.parseInt(Dnumber.getText()) + "'");
                        info.setText("Info : Deleted successfully");
                        // Clear the Fields
                        clearD();
                    }
                }
            }
            System.out.println("Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onSearchClick_DL(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnect();
            Statement statement = connection.createStatement();
            // clear the info label
            info.setText("Info : ");
            // Clear the Fields
            Dlocation_DL.setText("");
            Dlocation_List.setText("");

            if (Dnumber_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be searched");
            } else {
                if (isInt(Dnumber_DL, "Please input integer for Dnumber")) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM dept_location WHERE Dnumber = " + Integer.parseInt(Dnumber_DL.getText()));
                    if (resultSet.next()) {
                        // set Dlocation_DL to ""
                        Dlocation_DL.setText("");
                        Dlocation_List.setText("");

                        System.out.println(resultSet.getString("Dlocation"));
                        Dnumber_DL.setText(resultSet.getString("Dnumber"));
                        // append Dlocation with resultSet.getString("Dlocation")
                        Dlocation_List.setText(Dlocation_DL.getText() + resultSet.getString("Dlocation") + "\n");
                        Dlocation_DL.setText(resultSet.getString("Dlocation"));

                        while (resultSet.next()) {
                            System.out.println(resultSet.getString("Dlocation"));
                            Dnumber_DL.setText(resultSet.getString("Dnumber"));
                            // append Dlocation with resultSet.getString("Dlocation")
                            Dlocation_List.setText(Dlocation_List.getText() + resultSet.getString("Dlocation") + "\n");
                        }
                    } else {
                        info.setText("Info : No such Dnumber in the database");
                    }

                } else {
                    info.setText("Info : Please enter a DNumber to be searched");
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
            if (Dnumber_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be inserted");
            } else if (Dlocation_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a Dlocation");
            } else {
                // if Dnumber is not in the database
                if (!isDnumberExist(Dnumber_DL.getText())) {
                    info.setText("Info : Error The Dnumber is not in the database. Try another number.");
                } else {
                    if (isInt(Dnumber_DL, "Please input number for Dnumber")) {
                        statement.executeUpdate("INSERT INTO dept_location VALUES ('" + Integer.parseInt(Dnumber_DL.getText()) + "','" + Dlocation_DL.getText() + "')");
                        info.setText("Info : Inserted successfully");
                    }
                }
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
            if (Dnumber_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be updated");
            } else if (Dlocation_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a Dlocation");
            } else {
                if (!isDnumberExist(Dnumber_DL.getText()) && !isDLnumberExist(Dnumber_DL.getText())) {
                    info.setText("Info : Error The Dnumber is not in the database. Try another number.");
                } else {
                    if (isInt(Dnumber_DL, "Please input number for Dnumber")) {
                        statement.executeUpdate("UPDATE dept_location SET Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "', Dlocation = '" + Dlocation_DL.getText() + "' WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
                        info.setText("Info : Updated successfully");
                    }
                }

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
            if (Dnumber_DL.getText().isEmpty()) {
                info.setText("Info : Please enter a DNumber to be deleted");
            } else {
                if (!isDLnumberExist(Dnumber_DL.getText())) {
                    info.setText("Info : Error The Dnumber is not in the database. Try another number.");
                } else {
                    if (isInt(Dnumber_DL, "Please input number for Dnumber")) {
                        statement.executeUpdate("DELETE FROM dept_location WHERE Dnumber = '" + Integer.parseInt(Dnumber_DL.getText()) + "'");
                        info.setText("Info : Deleted successfully");
                        clearDL();
                    }
                }
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




/*
Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("No Dnumber found");
                            alert.setContentText("There is not Dlocation under this Dnumber. Are you sure you want to insert?");
                            alert.showAndWait().ifPresent(rs -> {
                                if (rs == ButtonType.OK) {
                                    System.out.println("Pressed OK.");
                                    try {
                                        statement.executeUpdate("INSERT INTO department VALUES ('" + Dname.getText() + "','" + Integer.parseInt(Dnumber.getText()) + "','" + Integer.parseInt(Mgr_ssn.getText()) + "','" + Mgr_start_due.getText() + "')");
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    info.setText("Info : Inserted");
                                }
                                else if (rs == ButtonType.CANCEL) {
                                    System.out.println("Pressed CANCEL.");
                                }
                            });
* */