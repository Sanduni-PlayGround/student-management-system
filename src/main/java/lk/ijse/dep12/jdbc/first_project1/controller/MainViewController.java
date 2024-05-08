package lk.ijse.dep12.jdbc.first_project1.controller;

import javafx.collections.ListChangeListener;
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
        btnDelete.setDisable(true);

        //mapping to the columns
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblStudent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tblStudent.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Student>) change -> {
            if(tblStudent.getSelectionModel().getSelectedItems().size() > 1){
                for(TextField txt : new TextField[]{txtId, txtName, txtAddress, txtContacts}){
                    txt.setText("Multiple Selection");
                }
            }else if(tblStudent.getSelectionModel().getSelectedItems().size() == 1){
                Student selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
                txtId.setText(selectedStudent.getId());
                txtName.setText(selectedStudent.getName());
                txtAddress.setText(selectedStudent.getAddress());
                txtContacts.setText(selectedStudent.getContact());
            }else{
                for (TextField txt : new TextField[]{txtId, txtName, txtAddress, txtContacts}) txt.clear();
                txtId.setText("GENERATED ID");
            }
        });



        loadAllStudents();

    }

    private void loadAllStudents(){//to load all the students in database to the table
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dep12_first_project", "postgres", "1234")) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("TABLE student");//take all the students in the database
            ObservableList<Student> studentList = tblStudent.getItems();
            while(rst.next()){
                String id = formatStudentId(rst.getInt("id"));
                String name = rst.getString("name");
                String address = rst.getString("address");
                String contact = rst.getString("contacts");

                Student student = new Student(id, name,address, contact);
                studentList.add(student);
            }

        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load student details, try again").show();
        }
    }

    private String formatStudentId(int id){
        return "S-%03d".formatted(id);//representation of the id in UI
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
