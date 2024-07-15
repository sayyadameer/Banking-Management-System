<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <h2>Login</h2>
            <p>Fill in the fields below to log into your account</p>
            <div class="message">
                Account Registered successfully please proceed to login
            </div>
            <form action="LoginServlet" method="post">
                <input type="email" placeholder="Enter email" name="email" required>
                    <input type="password" placeholder="Password" name="password" required>
                <button type="submit" class="login-button">Login</button>
            </form>
            <div class="signup">
                <p>Don't have an account? <a href="register.jsp">Sign Up</a></p>
            </div>
        </div>
    </div>
</body>
</html>
