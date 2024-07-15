package BankingManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OpenAccountServlet")
public class OpenAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OpenAccountServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        double initialDeposit = Double.parseDouble(request.getParameter("initialDeposit"));
        String securityPin = request.getParameter("securityPin");
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            long accountNumber = generateAccountNumber(connection);

            String openAccountQuery = "INSERT INTO accounts (account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery)) {
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, initialDeposit);
                preparedStatement.setString(5, securityPin);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("Dashboard.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("open-account-failure.html");
        }
    }

    private long generateAccountNumber(Connection connection) {
        long initialAccountNumber = 10000100;
        String query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                long lastAccountNumber = resultSet.getLong("account_number");
                return lastAccountNumber + 1;
            } else {
                return initialAccountNumber;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return initialAccountNumber;
        }
    }
}
