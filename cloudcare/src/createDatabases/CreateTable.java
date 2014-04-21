package createDatabases;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateTable {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
      ArrayList<String> sym = new ArrayList<String>();
      ArrayList<String> part = new ArrayList<String>();
      HashSet<String> checkDup = new HashSet<String>();
      BufferedReader br = new BufferedReader(new FileReader("symptoms"));
      String line;
      while((line = br.readLine()) != null)
      {
        if(!checkDup.contains(line))
        {
          sym.add(line);
          checkDup.add(line);
        }
      }
      br.close();
      
      checkDup.clear();
      br = new BufferedReader(new FileReader("parts"));
      while((line = br.readLine()) != null)
      {
        if(!checkDup.contains(line))
        {
          part.add(line);
          checkDup.add(line);
        }
      }
      br.close();

      Collections.sort(part);
      Collections.sort(sym);
      
      for(int i = 0; i < part.size() - 1; i++)
      {
        if(part.get(i + 1).equals(part.get(i)))
        {
          part.remove(i + 1);
          i--;
        }
      }
      
      for(int i = 0; i < sym.size() - 1; i++)
      {
        if(sym.get(i + 1).equals(sym.get(i)))
        {
          sym.remove(i + 1);
          i--;
        }
      }
      
      ArrayList<String> comb = new ArrayList<String>();
      for(int i = 0; i < sym.size(); i++)
      {
        for(int j = 0; j < part.size(); j++)
        {
          String tmp = part.get(j) + "$" + sym.get(i);
          comb.add(tmp);
        }
        String tmp = "null$" + sym.get(i);
        comb.add(tmp);
      }
      
      String sql = "CREATE TABLE disease_data (disease VARCHAR(50),";
      
      for(int i = 0; i < comb.size() - 1; i++)
      {
       // System.out.println(comb.get(i));
        sql += comb.get(i) + " INTEGER, ";
      }
      sql += comb.get(comb.size() - 1) + " INTEGER);";
      //System.out.println(sql);
      
      Connection con = null;
      Statement st = null;
      ResultSet rs = null;

      String url = "jdbc:mysql://localhost/CloudCare"; /* You need to create a database called "CloudCare" */
      String user = "root";/* put your user id here */ //"pqichen";
      String password = "mypass";/* put your password here */ //"pqichen";
      try {
          Class.forName("com.mysql.jdbc.Driver").newInstance(); 
          con = DriverManager.getConnection(url, user, password);
          st = con.createStatement();
          st.executeUpdate(sql);
          sql = "CREATE TABLE standard_part (part VARCHAR(100));";
          st.executeUpdate(sql);
          sql = "CREATE TABLE standard_symptom (symptom VARCHAR(100));";
          st.executeUpdate(sql);
          for(int i = 0; i < sym.size(); i++)
          {
            sql = "INSERT INTO standard_symptom (symptom) VALUES ('" + sym.get(i) + "');";
            st.executeUpdate(sql); 
          }
          for(int i = 0; i < part.size(); i++)
          {
            sql = "INSERT INTO standard_part (part) VALUES ('" + part.get(i) + "');";
            st.executeUpdate(sql); 
          }
          //System.out.println("Success");
          sql = "INSERT INTO disease_data (disease, navel$pulsate, abdomen$pain) VALUES ('abdominal aortic aneurysm', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, skin$dry, skin$scaly) VALUES ('teen depression', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, eye$lose_vision, eye$tunnel_vision) VALUES ('glaucoma （primary open-angle glaucoma)', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, eye$pain, null$nausea, null$vomiting) VALUES ('angle-closure glaucomax', 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, arm$tingle, arm$weakness) VALUES ('cervical spondylosis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, mouth$thirst, urine$frequent_urination) VALUES ('diabetes',1 ,1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, chest$pain, chest$angina) VALUES ('cardiovascular disease', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, feel$unhappyness, feel$sadness) VALUES ('depression (major depressive disorder)', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, abdomen$gnawing, null$nausea, null$vomiting) VALUES ('gastritis', 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, null$coughing, null$sneezing) VALUES ('common cold', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, null$liquid_stools, null$dehydration) VALUES ('diarrhea', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, joint$pain, null$stiffness) VALUES ('arthritis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, null$excessive_thrist, null$weight_change) VALUES ('diabetes', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, chest$pain, back$discomfort) VALUES ('heart attack', 1, 1);";
          st.executeUpdate(sql);
      } catch (SQLException ex) {
          Logger lgr = Logger.getLogger(CreateTable.class.getName());
          lgr.log(Level.SEVERE, ex.getMessage(), ex);

      } 
    }
}