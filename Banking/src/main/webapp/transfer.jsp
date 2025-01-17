<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transfer Money</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"],
        input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <form action="TransferServlet" method="post">
        <label for="senderAccountNumber">Sender Account Number:</label>
        <input type="text" id="senderAccountNumber" name="senderAccountNumber" required><br>
        <label for="receiverAccountNumber">Receiver Account Number:</label>
        <input type="text" id="receiverAccountNumber" name="receiverAccountNumber" required><br>
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" required><br>
        <label for="securityPin">Security Pin:</label>
        <input type="password" id="securityPin" name="securityPin" required><br>
        <input type="submit" value="Transfer">
    </form>
</body>
</html>
