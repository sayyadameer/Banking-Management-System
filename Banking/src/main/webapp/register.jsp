<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - InfinityBank</title>
    <link rel="stylesheet" href="register.css">
</head>
<body>
    <div class="container">
        <form action="register" method="post">
            <h1>Register</h1>
            <p>Fill in the fields below to register for an account</p>
            <div class="input-group">
                <input type="text" name="firstName" placeholder="Enter First Name.." required>
                <input type="text" name="lastName" placeholder="Enter Last Name.." required>
            </div>
            <input type="email" name="email" placeholder="Enter Email.." required>
            <div class="input-group">
                <input type="password" name="password" placeholder="Enter Password.." required>
                <input type="password" name="confirmPassword" placeholder="Confirm Password.." required>
            </div>
            <button type="submit" class="button register">Register</button>
            <p>Already have an account? <a href="login.jsp">Sign In</a></p>
            <a href="home.jsp" class="back">Back</a>
        </form>
    </div>
</body>
</html>
