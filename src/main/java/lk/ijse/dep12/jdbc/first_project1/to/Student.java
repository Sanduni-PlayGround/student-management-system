package lk.ijse.dep12.jdbc.first_project1.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    String id; //actually within the database there is a integer value (auto generate (SERIAL)), but in UI we represent it as a
    //string value like S001, S002 etc...
    String name;
    String address;
    String contact;
}
