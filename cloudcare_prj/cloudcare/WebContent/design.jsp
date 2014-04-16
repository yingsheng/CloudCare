<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="parser.ParseInput" %>
<%@ page import="wiki.wikiSearch" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<!-- --------------------------------------------- --> 
<html>

<head>
<title>Chat - Customer Module</title>
<link type="text/css" rel="stylesheet" href="style2.css" />
</head>


<BODY background="images1.gif">
<body BACKGROUND="background.png">
<div id = allWrap>
	
	<div id="wrapper2">
		<div id="menu">
	        <p class="welcome">Welcome, <b><?php echo $_SESSION['name']; ?></b></p>
	        <p class="logout"><a id="exit" href="#">Exit Chat</a></p>
	        <div style="clear:both"></div>
	    </div>      
	    <div id="chatbox"><%
	    String contents = "";
	    // put ipt to log.html
	    String ipt = request.getParameter("usermsg"); 
	    PrintWriter pw = new PrintWriter(new FileWriter("log.html", true));
	    pw.write("<div class='msgln'> <b> user </b>: "+ ipt + "<br></div>");
	    pw.close();
	    
	    

	    File f = new File("log.html");
	    
	    if(f.exists()) {
		    BufferedReader br = new BufferedReader(new FileReader("log.html"));
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		
		        while (line != null) {
		            sb.append(line);
		            sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        contents = sb.toString();
		        br.close();	   
		        
		}	    	    
	    //out.println(ipt);
	    if (!contents.isEmpty()) {out.println(contents);}
	    
		%>
		</div>	     
	    <form name="message" method="GET">
	        <input name="usermsg" type="text" id="usermsg" size="63" />
	        <input name="submitmsg" type="submit"  id="submitmsg" value="Send" />
	    </form>	  
	</div>
	
	
	<div id = "wrapper1">
		<div id="menu">
	        <p class="welcome"><b>Your diagnose is: </b></p>
	        <div style="clear:both"></div>
	    </div> 
	    <div id="chatbox">
	    <% 
		if (ipt!=null) {
		ParseInput PI = new ParseInput();
		try {
		    String connectionURL = "jdbc:mysql://localhost/sytest";
		    Connection connection = null; 
		    Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		    connection = DriverManager.getConnection(connectionURL, "root", "mypass");
		
		    String sym1 = ipt;
		    ArrayList<String> usefulValue = PI.getSymptoms(sym1);
		
		    if (usefulValue!=null){
		
		        PreparedStatement pstmt;      
		        pstmt = connection.prepareStatement("SELECT disease FROM disease WHERE sympton1 LIKE (?) or sympton2 Like (?)");  
		
		    	
				pstmt.setString(1,usefulValue.get(0));  
		    	pstmt.setString(2,usefulValue.get(0));
		        ResultSet rs2 = pstmt.executeQuery();
			
		        String x = "No disesase found.";
		        while (rs2.next()) {
		            x = rs2.getString("disease");
		        }
		    	out.println("<b>"+x+"</b><br />");
		    	if (!x.equals("No disesase found.")) {
		    		out.println(wikiSearch.returnWiki(x));
		    	}
		    	connection.close();
		    } 
		  	   
		} catch(Exception ex){
		    out.println("Unable to connect to database"+ex);
		} 
		
		}
		%>
	    </div>
		
	</div>
</div>
</body>
</html>




