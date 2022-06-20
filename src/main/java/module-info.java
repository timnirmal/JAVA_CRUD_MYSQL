module com.crud.java_crud_mysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.crud.java_crud_mysql to javafx.fxml;
    exports com.crud.java_crud_mysql;
}