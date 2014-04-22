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
      ArrayList<String> disease = new ArrayList<String>();
      HashSet<String> checkDup = new HashSet<String>();
      BufferedReader br = new BufferedReader(new FileReader("symptoms"));
      String line;
      while((line = br.readLine()) != null)
      {
        line.toLowerCase();
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
        line.toLowerCase();
        if(!checkDup.contains(line))
        {
          part.add(line);
          checkDup.add(line);
        }
      }
      br.close();

      checkDup.clear();
      br = new BufferedReader(new FileReader("disease"));
      while((line = br.readLine()) != null)
      {

        //System.out.println(line);
        line.toLowerCase();
        if(!checkDup.contains(line))
        {
          disease.add(line);
          checkDup.add(line);
        }
      }
      br.close();
      
      Collections.sort(part);
      Collections.sort(sym);
      Collections.sort(disease);
      
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
      

      
      Connection con = null;
      Statement st = null;
      ResultSet rs = null;

      String url = "jdbc:mysql://localhost/CloudCare"; /* You need to create a database called "CloudCare" */
      String user = "root";/* put your user id here */ //"pqichen";
      String password = "pqc1991526";/* put your password here */ //"pqichen";
      try {
          Class.forName("com.mysql.jdbc.Driver").newInstance(); 
          con = DriverManager.getConnection(url, user, password);
          st = con.createStatement();
          
          String sql = "CREATE TABLE disease_data (disease VARCHAR(50),";
          
          for(int i = 0; i < comb.size() - 1; i++)
          {
            //System.out.println(comb.get(i));
            sql += comb.get(i) + " INTEGER, ";
          }
          sql += comb.get(comb.size() - 1) + " INTEGER);";
          //System.out.println(Integer.toString(comb.size()));
          st.executeUpdate(sql);
          
          sql = "CREATE TABLE symptom_data (symptom VARCHAR(50),";
          
          for(int i = 0; i < disease.size() - 1; i++)
          {
            System.out.println(disease.get(i));
            sql += disease.get(i) + " INTEGER, ";
          }
          sql += disease.get(disease.size() - 1) + " INTEGER);";
          //System.out.println(sql);
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
          sql = "INSERT INTO disease_data (disease, navel$pulsate, abdomen$pain) VALUES ('abdominal_aortic_aneurysm', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, eye$vision, null$nausea) VALUES ('glaucoma', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, eye$pain, eye$vision, null$nausea, null$vomiting) VALUES ('angle_closure_glaucoma', 1, 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, arm$tingle, arm$weakness) VALUES ('cervical_spondylosis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, mouth$thirst, urine$urination) VALUES ('diabetes',1 ,1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, chest$pain, chest$angina) VALUES ('cardiovascular_disease', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, feel$unhappyness, feel$sadness) VALUES ('depression', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, abdomen$gnawing, null$nausea, null$vomiting) VALUES ('gastritis', 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, null$coughing, null$sneezing) VALUES ('common_cold', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, null$stools, null$dehydration) VALUES ('diarrhea', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, joint$pain, null$stiffness) VALUES ('arthritis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, chest$pain, back$discomfort) VALUES ('heart_attack', 1, 1);";
          st.executeUpdate(sql);         
            
          sql = "INSERT INTO symptom_data (symptom, abdominal_aortic_aneurysm) VALUES ('navel$pulsate', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, abdominal_aortic_aneurysm) VALUES ('abdomen$pain', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, glaucoma, angle_closure_glaucoma) VALUES ('eye$vision', 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, glaucoma, angle_closure_glaucoma) VALUES ('null$nausea', 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, angle_closure_glaucoma) VALUES ('null$vomiting', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, angle_closure_glaucoma) VALUES ('eye_pain', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, cervical_spondylosis) VALUES ('arm$weakness', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, cervical_spondylosis) VALUES ('arm$tingle', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('mouth$thirst', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('urine$urination', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('null$weight', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, cardiovascular_disease) VALUES ('chest$angina', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, cardiovascular_disease, heart_attack) VALUES ('chest$pain', 1, 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, depression) VALUES ('feel$unhappyness', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, depression) VALUES ('feel$sadness', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, common_cold) VALUES ('null$coughing', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, common_cold) VALUES ('null$sneezing', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, diarrhea) VALUES ('null$stools', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, diarrhea) VALUES ('null$dehydration', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, arthritis) VALUES ('joint$pain', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, arthritis) VALUES ('null$stiffness', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, heart_attack) VALUES ('back$discomfort', 1);";
          st.executeUpdate(sql); 


          
      } catch (SQLException ex) {
          Logger lgr = Logger.getLogger(CreateTable.class.getName());
          lgr.log(Level.SEVERE, ex.getMessage(), ex);

      } 
    }
}