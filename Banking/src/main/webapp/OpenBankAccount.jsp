<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open New Account - InfinityBank</title>
    <link rel="stylesheet" href="openbankaccount.css">
</head>
<body>
    <div class="form-container">
        <h2>Open a New Account</h2>
        <form action="OpenAccountServlet" method="post">
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="initialDeposit">Initial Deposit:</label>
                <input type="number" id="initialDeposit" name="initialDeposit" step="0.01" required>
            </div>
            <div class="form-group">
                <label for="securityPin">Security Pin:</label>
                <input type="password" id="securityPin" name="securityPin" required>
            </div>
            <button type="submit">Open Account</button>
        </form>
    </div>
</body>
</html>
