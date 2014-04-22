package parser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StandardSymptomQuery {
  Connection con;
  Statement st;
  ResultSet rs;

  public StandardSymptomQuery() throws InstantiationException, IllegalAccessException, ClassNotFoundException
  {
    String url = "jdbc:mysql://localhost:3306/CloudCare"; /* You need to create a database called "CloudCare" */
    String user = "pqichen";/* put your user id here */ //"pqichen";
    String password = "pqichen";/* put your password here */ //"pqichen";
    try 
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance(); 
        con = DriverManager.getConnection(url, user, password);
        st = con.createStatement();
    } catch (SQLException ex) 
    {
        Logger lgr = Logger.getLogger(StandardSymptomQuery.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }
  
  public String getStandardPart(String UserInput) throws SQLException
  {
    String query = "SELECT part FROM standard_part WHERE part = '" + UserInput + "';";
    rs = st.executeQuery(query);
    if(!rs.next()) return null;
    String result = rs.getString("part");
    return result;
  }
  
  public String getStandardSymptom(String UserInput) throws SQLException
  {
    String query = "SELECT symptom FROM standard_symptom WHERE symptom = '" + UserInput + "';";
    rs = st.executeQuery(query);
    if(!rs.next()) return null;
    String result = rs.getString("symptom");
    return result;
  }
}
