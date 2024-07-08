/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 6/29/24

 */

package lk.ijse.aad.aad_crud_sample_project;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/studentController")
public class StudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Save a student
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
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
        JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonArray = reader.readArray();
        for (int i=0; i < jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            System.out.println(jsonObject.getString("city"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
