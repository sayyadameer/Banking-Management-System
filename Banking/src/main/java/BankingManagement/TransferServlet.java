package BankingManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long senderAccountNumber = Long.parseLong(request.getParameter("senderAccountNumber"));
        long receiverAccountNumber = Long.parseLong(request.getParameter("receiverAccountNumber"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String securityPin = request.getParameter("securityPin");

        try (Connection connection = DatabaseConnection.getConnection()) {
            AccountManager accountManager = new AccountManager(connection);
            accountManager.transfer_money(senderAccountNumber, receiverAccountNumber, amount, securityPin);
            response.sendRedirect("Dashboard.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
