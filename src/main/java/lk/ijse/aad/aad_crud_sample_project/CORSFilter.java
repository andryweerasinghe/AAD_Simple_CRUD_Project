/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 8/10/24

 */

package lk.ijse.aad.aad_crud_sample_project;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var origin = req.getHeader("Origin");      //getting the origin header of the host domain
        var configuredOrigin = getServletContext().getInitParameter("origin");    //getting the saved origin to be allowed from the web.xml

        if(origin.contains(configuredOrigin)){
            res.setHeader("Access-Control-Allow-Origin", origin);   //allowing the 'origin' domain to enter the backend
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");    //allowing access to the 'origin' domain to access the methods
            res.setHeader("Access-Control-Allow-Headers", "Content-Type");     //allowing access to the content-type
            res.setHeader("Access-Control-Expose-Headers", "Content-Type");    //allowing the javascript engine of the browser to access the content-type (javascript engine is the unit of a browser which executes javascript codes)
        }
        chain.doFilter(req, res);    //After setting the headers, the request is passed along the filter chain to the next filter or servlet
    }
}
