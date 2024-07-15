package BankingManagement;

import java.sql.*;

public class AccountManager {
    private Connection connection;

    public AccountManager(Connection connection) {
        this.connection = connection;
    }

    public void credit_money(long accountNumber, double amount, String securityPin) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, securityPin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                PreparedStatement updateStmt = connection.prepareStatement(creditQuery);
                updateStmt.setDouble(1, amount);
                updateStmt.setLong(2, accountNumber);
                updateStmt.executeUpdate();
                connection.commit();
            } else {
                connection.rollback();
                throw new SQLException("Invalid security pin.");
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
        
    }

    public void debit_money(long accountNumber, double amount, String securityPin) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, securityPin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance");
                if (amount <= currentBalance) {
                    String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(debitQuery);
                    updateStmt.setDouble(1, amount);
                    updateStmt.setLong(2, accountNumber);
                    updateStmt.executeUpdate();
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Insufficient balance.");
                }
            } else {
                connection.rollback();
                throw new SQLException("Invalid security pin.");
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void transfer_money(long senderAccountNumber, long receiverAccountNumber, double amount, String securityPin) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement senderStmt = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
            senderStmt.setLong(1, senderAccountNumber);
            senderStmt.setString(2, securityPin);
            ResultSet senderRs = senderStmt.executeQuery();

            if (senderRs.next()) {
                double senderBalance = senderRs.getDouble("balance");
                if (amount <= senderBalance) {
                    String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                    String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

                    PreparedStatement debitStmt = connection.prepareStatement(debitQuery);
                    PreparedStatement creditStmt = connection.prepareStatement(creditQuery);

                    debitStmt.setDouble(1, amount);
                    debitStmt.setLong(2, senderAccountNumber);
                    creditStmt.setDouble(1, amount);
                    creditStmt.setLong(2, receiverAccountNumber);

                    debitStmt.executeUpdate();
                    creditStmt.executeUpdate();
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Insufficient balance.");
                }
            } else {
                connection.rollback();
                throw new SQLException("Invalid security pin.");
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public double getBalance(long accountNumber, String securityPin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?");
        preparedStatement.setLong(1, accountNumber);
        preparedStatement.setString(2, securityPin);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("balance");
        } else {
            throw new SQLException("Invalid security pin.");
        }
       
    }
}
