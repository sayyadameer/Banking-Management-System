package BankingManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String fullName = firstName + " " + lastName;

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (userExists(email, connection)) {
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('User Already Exists for this Email Address!!');");
                response.getWriter().println("window.location.href = 'home.jsp';");
                response.getWriter().println("</script>");
                return;
            }

            String sql = "INSERT INTO user (full_name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, fullName);
                statement.setString(2, email);
                statement.setString(3, password);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    response.setContentType("text/html");
                    response.getWriter().println("<script type='text/javascript'>");
                    response.getWriter().println("alert('Registration Successful!');");
                    response.getWriter().println("window.location.href = 'login.jsp';");
                    response.getWriter().println("</script>");
                } else {
                    response.sendRedirect("error.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private boolean userExists(String email, Connection connection) {
        String query = "SELECT 1 FROM user WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
}
