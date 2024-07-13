package lk.ijse.aad.aad_crud_sample_project.persistance;

import lk.ijse.aad.aad_crud_sample_project.dto.StudentDTO;

import java.sql.Connection;

public interface Data {
    StudentDTO getStudent(String id, Connection connection) throws Exception;
    boolean saveStudent(StudentDTO studentDTO, Connection connection) throws Exception;
    boolean deleteStudent(String id, Connection connection) throws Exception;
    boolean updateStudent(String id, StudentDTO studentDTO, Connection connection) throws Exception;
}
