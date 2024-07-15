<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="BankingManagement.DatabaseConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InfinityBank</title>
    <link rel="stylesheet" href="dashboard.css">
</head>
<body>
    <div class="header">
        <div class="nav">
            <span class="brand">InfinityBank</span>
            <nav>
                <a href="Dashboard.jsp">Dashboard</a>
                <a href="TransactionsHistory.jsp">Transactions History</a>
                <p class="user">
                    <% 
                        String email = (String) session.getAttribute("email");
                        if (email != null) {
                            String query = "SELECT full_name FROM accounts WHERE email = ?";
                            try (Connection connection = DatabaseConnection.getConnection();
                                 PreparedStatement userStmt = connection.prepareStatement(query)) {
                                userStmt.setString(1, email);
                                ResultSet rs = userStmt.executeQuery();
                                if (rs.next()) {
                                    out.print(rs.getString("full_name"));
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            response.sendRedirect("login.jsp");
                        }
                    %>
                </p>
                <a href="home.jsp" class="signout">Sign Out</a>
            </nav>
        </div>
    </div>
    <div class="content">
        <div class="success-message">WELCOME TO INFINITY BANK</div>
        <div class="account-section">
            <button class="add-account" onclick="window.location.href='OpenBankAccount.jsp'">Add an Account</button>
            <div class="balance-section">
                <h2>Total Accounts Balance: 
                    <span class="total-balance">
                        <% 
                            double totalBalance = 0.0;
                            String balanceQuery = "SELECT (balance) AS total_balance FROM accounts WHERE email = ?";
                            try (Connection connection = DatabaseConnection.getConnection();
                                 PreparedStatement balanceStmt = connection.prepareStatement(balanceQuery)) {
                                balanceStmt.setString(1, email);
                                ResultSet balanceRs = balanceStmt.executeQuery();
                                if (balanceRs.next()) {
                                    totalBalance = balanceRs.getDouble("total_balance");
                                    out.print(totalBalance);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        %>
                    </span>
                </h2>
                <div class="account-details">
                    <h3>Your Accounts</h3>
                    <table>
                        <tr>
                            <th>Account Name</th>
                            <th>Account Number</th>
                            <th>Account Balance</th>
                        </tr>
                        <% 
                            String accountQuery = "SELECT full_name, account_number, balance FROM accounts WHERE email = ?";
                            try (Connection connection = DatabaseConnection.getConnection();
                                 PreparedStatement accountStmt = connection.prepareStatement(accountQuery)) {
                                accountStmt.setString(1, email);
                                ResultSet accountRs = accountStmt.executeQuery();
                                while (accountRs.next()) {
                                    String fullName = accountRs.getString("full_name");
                                    long accountNumber = accountRs.getLong("account_number");
                                    double balance = accountRs.getDouble("balance");
                        %>
                        <tr>
                            <td><%= fullName %></td>
                            <td><%= accountNumber %></td>
                            <td><%= balance %></td>
                        </tr>
                        <% 
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        %>
                    </table>
                </div>
            </div>
            <div class="transact-section">
                <h3>Perform a Transaction</h3>
                <div class="transaction-buttons">
                    <button onclick="window.location.href='credit.jsp'">Credit Money</button>
                    <button onclick="window.location.href='debit.jsp'">Debit Money</button>
                    <button onclick="window.location.href='transfer.jsp'">Transfer Money</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
