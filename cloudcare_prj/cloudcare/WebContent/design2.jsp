<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="parser.ParseInput" %>
<%@ page import="wiki.wikiSearch" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Design by http://www.bluewebtemplates.com
Released for free under a Creative Commons Attribution 3.0 License
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Template</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="style2.css" />
<!-- CuFon: Enables smooth pretty custom font rendering. 100% SEO friendly. To disable, remove this section -->
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/georgia.js"></script>
<script type="text/javascript" src="js/cuf_run.js"></script>
<!-- CuFon ends -->
</head>
<body>
<div class="main">

  <div class="header">
    <div class="header_resize">
      <div class="logo"><h1><a href="index.html">CloudCare<br /><small>We are from cloud. We care.</small></a></h1></div>
      <div class="menu_nav">
        <ul>
          <li class="active"><a href="index.html">Home</a></li>
          <li><a href="support.html">Support</a></li>
          <li><a href="about.html">About Us</a></li>
          <li><a href="blog.html">Blog</a></li>
          <li><a href="contact.html">Contact Us</a></li>
        </ul>
        <div class="clr"></div>
      </div>
      <div class="clr"></div>
    </div>
  </div>

  <div class="content">
    <div class="content_resize">
      <div class="mainbar">
      
      
        <div class="article">

          <img src="images/img1.jpg" width="613" height="154" alt="img" />  
           <h2><span>Input your symptoms here</span></h2>
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
	   
        </div>
        
        
        
        <div class="article">
          
          <img src="images/img2.jpg" width="613" height="154" alt="img" />
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
				
				pstmt.setString(1,usefulValue.get(0)+"%");  
		    	pstmt.setString(2,usefulValue.get(0)+"%");
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
      </div>
      
      
      <div class="sidebar">
        <div class="gadget">
          <h2 class="star"><span>Sidebar</span> Menu</h2>
          <ul class="sb_menu">
            <li><a href="#">Home</a></li>
            <li><a href="#">TemplateInfo</a></li>
            <li><a href="#">Style Demo</a></li>
            <li><a href="#">Blog</a></li>
            <li><a href="#">Archives</a></li>
            <li><a href="http://www.dreamtemplate.com">Website Templates</a></li>
          </ul>
        </div>
        <div class="gadget">
          <h2 class="star"><span>Founder</span></h2>
          <ul class="ex_menu">
            <li><a href="http://www.dreamtemplate.com" title="Website Templates">Qichen Pan</a><br />Haha~ XD</li>
            <li><a href="http://www.templatesold.com" title="WordPress Themes">Ying Sheng</a><br />Love world!</li>
            <li><a href="http://www.imhosted.com" title="Affordable Hosting">Mengda Yang</a><br />Never give up!</li>
            <li><a href="http://www.dreamstock.com" title="Stock Photos">Shen Wu</a><br />I want a job!</li>
            
          </ul>
        </div>
      </div>
      <div class="clr"></div>
    </div>
  </div>

  <div class="fbg">
    <div class="fbg_resize">
      <div class="col c1">
        <h2>Care</h2>
        <a href="#"><img src="images/pix1.jpg" width="56" height="56" alt="pix" /></a>
        <a href="#"><img src="images/pix2.jpg" width="56" height="56" alt="pix" /></a>
        <a href="#"><img src="images/pix3.jpg" width="56" height="56" alt="pix" /></a>
        <a href="#"><img src="images/pix4.jpg" width="56" height="56" alt="pix" /></a>
        <a href="#"><img src="images/pix5.jpg" width="56" height="56" alt="pix" /></a>
        <a href="#"><img src="images/pix6.jpg" width="56" height="56" alt="pix" /></a>
      </div>
      <div class="col c2">
        <h2>Tell us about your advices</h2>
        <p></p>
      </div>
      <div class="col c3">
        <h2>About</h2>
        <img src="images/white.jpg" width="56" height="56" alt="pix" />
        <p>We are a team from CMU. <a href="#">Learn more...</a></p>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="footer">
    <div class="footer_resize">
      <p class="lf">&copy; Copyright MyWebSite. Designed by Blue <a href="http://www.bluewebtemplates.com">Website Templates</a></p>
      <ul class="fmenu">
        <li class="active"><a href="index.html">Home</a></li>
        <li><a href="support.html">Support</a></li>
        <li><a href="blog.html">Blog</a></li>
        <li><a href="about.html">About Us</a></li>
        <li><a href="contact.html">Contacts</a></li>
      </ul>
      <div class="clr"></div>
    </div>
  </div>
</div>
</body>
</html>
