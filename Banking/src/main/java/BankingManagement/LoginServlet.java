package BankingManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        String userQuery = "SELECT * FROM user WHERE email=? AND password=?";
        String accountQuery = "SELECT * FROM accounts WHERE email=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement userStmt = connection.prepareStatement(userQuery);
             PreparedStatement accountStmt = connection.prepareStatement(accountQuery)) {

            userStmt.setString(1, email);
            userStmt.setString(2, password);

            ResultSet userResult = userStmt.executeQuery();

            if (userResult.next()) {
        
                request.getSession().setAttribute("email", email);
                accountStmt.setString(1, email);
                ResultSet accountResult = accountStmt.executeQuery();

                if (accountResult.next()) {
                    response.sendRedirect("Dashboard.jsp");
                } else {
                    response.sendRedirect("OpenBankAccount.jsp");
                }
            } else {
                response.getWriter().println("<script type='text/javascript'>");
                response.getWriter().println("alert('Incorrect Email or Password');");
                response.getWriter().println("window.location.href = 'home.jsp';");
                response.getWriter().println("</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
