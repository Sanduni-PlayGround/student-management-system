package lk.ijse.dep12.jdbc.first_project1.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dep12.jdbc.first_project1.to.Student;

import java.sql.*;

public class MainViewController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContacts;
    public Button btnNewStudent;
    public Button btnSave;
    public Button btnDelete;
    public TableView<Student> tblStudent;//mention the Student as the model of the table

    public void initialize(){
        btnDelete.setDisable(true);//delete button is disabled when the UI is load

        //within the table there is an observable array list. in that observable array list there are students are going to be stored

        //mapping to the columns
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        //take the column list of the table, for the first column set a PropertyValueFactory type CellValueFactory

        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//go to the SelectionModel of the table
        //set the SelectionMode as MULTIPLE rows
        // to select multiple items (Students) at once
        // (enable multiple selection, by default only single row can be selected in a table)

        loadAllStudents();

    }

    private void loadAllStudents(){//when this method invoked, load all the students in database to the table
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dep12_first_project", "postgres", "1234")) {//create a connector to database
            Statement stm = connection.createStatement();//here we create regular statement, since we are going to execute this once (only one time (when app is started)), we are not going to
            //execute this again and again. also since we don't take user inputs (just get data from database) for this section sql injections won't happen therefore no need to use prepared statements.
            ResultSet rst = stm.executeQuery("TABLE student");//take all the students in the database
            ObservableList<Student> studentList = tblStudent.getItems();//this getItems() method will return the ObservableList of the table in UI (tblStudent)
            //here we get the observable list of the table out, since we need to add the records into table in UI (tblStudent) according to
            //records in database table (Student)
            while(rst.next()){}
            rst.next();//get the first student in the (ResultSet rst) (in the dataBase)

            String id = formatStudentId(rst.getInt("id"));//get the value of the id column (String we set here should be the exact name of the column in table student in the database),
            // and format it to a string, set it to a variable calls id
            String name = rst.getString("name");
            String address = rst.getString("address");
            String contact = rst.getString("contacts");

            Student student = new Student(id, name,address, contact); //create a new Student object (to represent that particular person in database) passing all those values
            studentList.add(student); //add that student into the observable list (calls studentList)
        }catch (SQLException e){//if there is a SQL exception throws,
            e.printStackTrace();//print it
            new Alert(Alert.AlertType.ERROR, "Failed to load student details, try again").show();
        }
    }

    private String formatStudentId(int id){//to generate a id as S001, S002, ... according to the
        //integer value set by the database management system default (since we used SERIAL for id)
        return "S-%03d".formatted(id);//even though in database ids are represent as 1, 2, 3, ....
        //in UI ids will show as S001, S002, S003, ...
    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void tblStudentOnKeyPressed(KeyEvent keyEvent) {
    }
}
