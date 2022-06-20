# JAVA_CRUD_MYSQL

## First create MySQL Database and Tables

```
    CREATE DATABASE ABC_company;
    USE ABC_company;
```
    
Then create Department Table and Insert Data:

```
    CREATE TABLE Department ( Dname varchar(20) NOT NULL, Dnumber int(5) NOT NULL, Mgr_ssn int(10) NOT NULL, Mgr_start_due date  NOT NULL, PRIMARY KEY(Dnumber));
    
    mysql> INSERT INTO Department VALUES('Research', 5, 333445555, '1988-05-22'),('Administration', 4, 987654321, '1995-01-01'),('Headquarters', 1, 888665555, '1981-06-19');
```

Then create Dept_location Table and Insert Data:

```
    CREATE TABLE DEPT_LOCATION(Dnumber INT(2) NOT NULL,Dlocation VARCHAR(20) NOT NULL,PRIMARY KEY(Dnumber,Dlocation),FOREIGN KEY(Dnumber) REFERENCES DEPARTMENT(Dnumber));
    
    INSERT INTO DEPT_LOCATION VALUES(1,'Houston'),(4,'Stafford'),(5,'Bellarie'),(5,'Sugarland'),(5,'Houston');
```

The result of these command can be seen in [Assignment2.out]((https://github.com/timnirmal/JAVA_CRUD_MYSQL/blob/master/Assignment2.out)) file.

## [HelloController](https://github.com/timnirmal/JAVA_CRUD_MYSQL/blob/master/src/main/java/com/crud/java_crud_mysql/HelloController.java) 

HelloController.java file includes all the CRUD operations and other helping method replated to those.
- onSeachClick()
- onInsertClick()
- onUpdateClick()
- onDeleteClick()
- onSearchClick_DL()
- onInsertClick_DL()
- onUpdateClick_DL()
- onDeleteClick_DL()

## [MyJDBC](https://github.com/timnirmal/JAVA_CRUD_MYSQL/blob/master/src/main/java/com/crud/java_crud_mysql/MyJDBC.java)

This have the connection to MySQL database with JDBC.

## [Hello-view.fxml](https://github.com/timnirmal/JAVA_CRUD_MYSQL/blob/master/src/main/resources/com/crud/java_crud_mysql/hello-view.fxml)

This is GUI file for the CRUD operations.

## [HelloApplication](https://github.com/timnirmal/JAVA_CRUD_MYSQL/blob/master/src/main/java/com/crud/java_crud_mysql/HelloApplication.java)

This is the main application file.

