<<<<<<< HEAD
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="parser.ParseInput" %>
=======
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="InputFormat/src/ParserInput.java" %>
>>>>>>> pqichen

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>

</body>
</html>


<html>
<head>
<<<<<<< HEAD
<title>Take symptom input from user</title>
=======
<title>Take sympton input from user</title>
>>>>>>> pqichen
</head>

<body>
<center>
<<<<<<< HEAD
<h1>Please input your symptom here</h1>
<form action="pqctest.jsp" method="GET">
symptom: <input type="text" name="s1">
=======
<h1>Please input your sympton here</h1>
<form action="sytest.jsp" method="GET">
sympton: <input type="text" name="s1">
>>>>>>> pqichen
<input type="submit" value="Submit" />
</form>
</body>
<%
<<<<<<< HEAD
	ParseInput PI = new ParseInput();
=======
    ParseInput PI = new ParseInput();

>>>>>>> pqichen
%>
</html>




<html> 
<head> 
<title>Connection with mysql database</title>
</head> 
<body>
<h3>Connect to database </h3>
<h3>The diagnose is </h3>
<% 
try {
    String connectionURL = "jdbc:mysql://localhost/sytest";
    Connection connection = null; 
    Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    connection = DriverManager.getConnection(connectionURL, "root", "mypass");
    /*
    if(!connection.isClosed())
         out.println("Successfully connected to " + "MySQL server using TCP/IP...");
    Statement stmt = connection.createStatement(); 
    ResultSet rs = stmt.executeQuery("SELECT disease FROM disease WHERE sympton1 LIKE 'coughing'");
    */
<<<<<<< HEAD

=======
    
>>>>>>> pqichen
    PreparedStatement pstmt;      
    pstmt = connection.prepareStatement("SELECT disease FROM disease WHERE sympton1 LIKE (?) or sympton2 Like (?)");  
    String sym1 = request.getParameter("s1");
    ArrayList<String> usefulValue = PI.getSymptoms(sym1);
<<<<<<< HEAD
    
	pstmt.setString(1,usefulValue.get(0));  
    pstmt.setString(2,usefulValue.get(0));  
    ResultSet rs2 = pstmt.executeQuery();
	
    String x = usefulValue.get(0);
=======

	pstmt.setString(1,usefulValue.get(0));  
    pstmt.setString(2,usefulValue.get(0));  
    ResultSet rs2 = pstmt.executeQuery();

    String x = null;
>>>>>>> pqichen
    while (rs2.next()) {
        x = rs2.getString("disease");
    }
	out.println(x);   
    connection.close();
}catch(Exception ex){
    out.println("Unable to connect to database"+ex);
} 
%>
</font>
</body> 
</html>
<<<<<<< HEAD
=======



>>>>>>> pqichen
