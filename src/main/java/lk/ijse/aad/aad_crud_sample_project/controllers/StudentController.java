/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 6/29/24

 */

package lk.ijse.aad.aad_crud_sample_project.controllers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aad.aad_crud_sample_project.dto.StudentDTO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/studentController")
public class StudentController extends HttpServlet {

    Connection connection;
    static String SAVE_STUDENT = "INSERT INTO student (id, name, email, city, level) VALUES(?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id = ?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    static String UPDATE_STUDENT = "UPDATE student SET name = ?, email = ?, city = ?, level = ? WHERE id = ?";

    @Override
    public void init() throws ServletException {
        var driver = getServletContext().getInitParameter("driver-class");
        var dbURL = getServletContext().getInitParameter("dbURL");
        var userName = getServletContext().getInitParameter("dbUserName");
        var password = getServletContext().getInitParameter("dbPassword");
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(dbURL,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Get Student details
        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");
        try (var writer = resp.getWriter()){  //The try-with-resources statement ensures that the PrintWriter is automatically closed at the end of the statement. This avoids resource leaks, which can occur if the resource is not properly closed.
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                studentDTO.setId(resultSet.getString(1));
                studentDTO.setName(resultSet.getString(2));
                studentDTO.setEmail(resultSet.getString(3));
                studentDTO.setCity(resultSet.getString(4));
                studentDTO.setLevel(resultSet.getString(5));
            }
            System.out.println(studentDTO);

            //following is the way of writing the data by using json
            resp.setContentType("application/json");  //this is used to inform the client that this is a json type
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(studentDTO, resp.getWriter());

//            writer.write(studentDTO.toString());  //
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Save a student
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        String id = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
        studentDTO.setId(id);
        System.out.println(studentDTO);

        //Persist Data
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(5, studentDTO.getLevel());
            if(ps.executeUpdate() != 0 ){
                resp.getWriter().write("Student Saved");
            }else {
                resp.getWriter().write("Student not Saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>(){}
                .getClass().getGenericSuperclass());
        studentDTOList.forEach(System.out::println);*/
        //process
//        BufferedReader reader = req.getReader();  //to read the body of the request
//        StringBuilder sb = new StringBuilder();  // to build a single string from multiple lines read from the request body.
//        PrintWriter writer = resp.getWriter();  //to write the response back to the client
//        reader.lines().forEach(line -> sb.append(line+"\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();

        //JSON manipulate with Parson
        /*JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.getString("email"));*/

        //Reading an array with JSON
        /*JsonReader reader = Json.createReader(req.getReader()); //creating a json reader
        JsonArray jsonArray = reader.readArray(); //reading the array using the above reader
        for (int i=0; i < jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.getJsonObject(i); //filtering each object from the array
            System.out.println(jsonObject.getString("city"));
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Delete a student
        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");
        if (studentId == null || studentId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Student ID is required");
            return;
        }
        try (var writer = resp.getWriter()){
            var ps = connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1, studentId);
            int rowsAffected = ps.executeUpdate();
            System.out.println(studentDTO);

            if (rowsAffected != 0){
                resp.getWriter().write("Student Deleted");
            } else {
                resp.getWriter().write("Delete Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Update a student
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
        System.out.println(studentDTO);

        try(var writer = resp.getWriter()){
            var ps = connection.prepareStatement(UPDATE_STUDENT);
            ps.setString(1, studentDTO.getName());
            ps.setString(2, studentDTO.getEmail());
            ps.setString(3, studentDTO.getCity());
            ps.setString(4, studentDTO.getLevel());
            ps.setString(5, studentDTO.getId());

            var rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0){
                resp.getWriter().write("Student Updated");
            } else {
                resp.getWriter().write("Update Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
