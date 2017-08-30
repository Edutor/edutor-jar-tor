/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dbutil.JDBCConnectionPool;
import dbutil.PSConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tobias Grundtvig
 */
public class Database2 extends HttpServlet
{

    @Override
    public void init() throws ServletException
    {
        ServletContext context = getServletContext();
        JDBCConnectionPool pool = (JDBCConnectionPool) context.getAttribute("JDBCConnectionPool");
        if(pool == null)
        {
            try
            {
                pool = new JDBCConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://10.0.0.42:3306/CBA_Authentication",
                                              "CBA", "cbashod7sd", 10);
                context.setAttribute("JDBCConnectionPool", pool);
            }
            catch(ClassNotFoundException ex)
            {
                Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException(ex);
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String title = "Database Result";
        String docType
               = "<!doctype html public \"-//w3c//dtd html 4.0 "
                 + "transitional//en\">\n";
        out.println(docType
                    + "<html>\n"
                    + "<head><title>" + title + "</title></head>\n"
                    + "<body bgcolor=\"#f0f0f0\">\n"
                    + "<h1 align=\"center\">" + title + "</h1>\n");

        ServletContext context = getServletContext();
        JDBCConnectionPool pool = (JDBCConnectionPool) context.getAttribute("JDBCConnectionPool");
        PSConnection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            conn = pool.getConnection();
            stmt = conn.getPreparedStatement("SELECT id, first_name, last_name FROM Users");
            // Execute SQL query
            rs = stmt.executeQuery();

            // Extract data from result set
            while(rs.next())
            {
                //Retrieve by column name
                int id = rs.getInt("id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");

                //Display values
                out.println("ID: " + id + "<br>");
                out.println(", First: " + first + "<br>");
                out.println(", Last: " + last + "<br>");
            }
            out.println("</body></html>");
        }
        catch(InterruptedException | SQLException ex)
        {
            Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException ex)
                {
                    Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null)
            {
                pool.returnConnection(conn);
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
  public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
