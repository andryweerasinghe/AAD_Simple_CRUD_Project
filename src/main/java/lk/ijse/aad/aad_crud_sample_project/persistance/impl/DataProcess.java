/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 7/13/24

 */

package lk.ijse.aad.aad_crud_sample_project.persistance.impl;

import lk.ijse.aad.aad_crud_sample_project.dto.StudentDTO;
import lk.ijse.aad.aad_crud_sample_project.persistance.Data;

import java.sql.Connection;
import java.sql.SQLException;

public class DataProcess implements Data {

    static String SAVE_STUDENT = "INSERT INTO student (id, name, email, city, level) VALUES(?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id = ?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    static String UPDATE_STUDENT = "UPDATE student SET name = ?, email = ?, city = ?, level = ? WHERE id = ?";

    @Override
    public StudentDTO getStudent(String id, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(GET_STUDENT);
            StudentDTO studentDTO = new StudentDTO();
            ps.setString(1, id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString(1));
                studentDTO.setName(resultSet.getString(2));
                studentDTO.setEmail(resultSet.getString(3));
                studentDTO.setCity(resultSet.getString(4));
                studentDTO.setLevel(resultSet.getString(5));
            }
            return studentDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveStudent(StudentDTO studentDTO, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(5, studentDTO.getLevel());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_STUDENT);
        ps.setString(1, id);

        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateStudent(String id, StudentDTO studentDTO, Connection connection) throws Exception {
        var ps = connection.prepareStatement(UPDATE_STUDENT);
        ps.setString(1, studentDTO.getName());
        ps.setString(2, studentDTO.getEmail());
        ps.setString(3, studentDTO.getCity());
        ps.setString(4, studentDTO.getLevel());
        ps.setString(5, id);

        return ps.executeUpdate() != 0;
    }
}
