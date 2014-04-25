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
      ArrayList<String> disease = new ArrayList<String>();
      HashSet<String> checkDup = new HashSet<String>();
      BufferedReader br = new BufferedReader(new FileReader("unified_symptoms"));
      String line;
      while((line = br.readLine()) != null)
      {
        line = line.toLowerCase();
        if(!checkDup.contains(line))
        {
          sym.add(line);
          checkDup.add(line);
        }
      }
      br.close();
      
      checkDup.clear();
      br = new BufferedReader(new FileReader("disease"));
      while((line = br.readLine()) != null)
      {
        line = line.toLowerCase();
        if(!checkDup.contains(line))
        {
          disease.add(line);
          checkDup.add(line);
        }
      }
      br.close();

      Collections.sort(sym);
      Collections.sort(disease);
      
      for(int i = 0; i < sym.size() - 1; i++)
      {
        if(sym.get(i + 1).equals(sym.get(i)))
        {
          sym.remove(i + 1);
          i--;
        }
      }
      
      for(int i = 0; i < disease.size() - 1; i++)
      {
        if(disease.get(i + 1).equals(disease.get(i)))
        {
          disease.remove(i + 1);
          i--;
        }
      }
      
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
          
          String sql = "CREATE TABLE disease_data (disease VARCHAR(50),";
          
          for(int i = 0; i < sym.size() - 1; i++)
          {
            System.out.println(sym.get(i));
            sql += sym.get(i) + " INTEGER, ";
          }
          sql += sym.get(sym.size() - 1) + " INTEGER);";
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
          
          sql = "CREATE TABLE standard_symptom (symptom VARCHAR(100));";
          st.executeUpdate(sql);
          for(int i = 0; i < sym.size(); i++)
          {
            sql = "INSERT INTO standard_symptom (symptom) VALUES ('" + sym.get(i) + "');";
            st.executeUpdate(sql); 
          }

          sql = "INSERT INTO disease_data (disease, pulsate, abdomen, pain) VALUES ('abdominal_aortic_aneurysm', 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, vision, nausea) VALUES ('glaucoma', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, pain, vision, nausea, vomiting) VALUES ('angle_closure_glaucoma', 1, 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, tingle, weakness) VALUES ('cervical_spondylosis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, thirst, urination) VALUES ('diabetes',1 ,1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, pain, angina) VALUES ('cardiovascular_disease', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, unhappyness, sadness) VALUES ('depression', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, abdomen, gnawing, nausea, vomiting) VALUES ('gastritis', 1, 1, 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, coughing, sneezing) VALUES ('common_cold', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, stools, dehydration) VALUES ('diarrhea', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, pain, stiffness) VALUES ('arthritis', 1, 1);";
          st.executeUpdate(sql);
          sql = "INSERT INTO disease_data (disease, pain, discomfort) VALUES ('heart_attack', 1, 1);";
          st.executeUpdate(sql);         
            
          sql = "INSERT INTO symptom_data (symptom, abdominal_aortic_aneurysm) VALUES ('pulsate', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, abdominal_aortic_aneurysm, gastritis) VALUES ('abdomen', 1, 1);";
          st.executeUpdate(sql);     
          sql = "INSERT INTO symptom_data (symptom, abdominal_aortic_aneurysm, angle_closure_glaucoma, heart_attack, arthritis, cardiovascular_disease) VALUES ('pain', 1, 1, 1, 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, glaucoma, angle_closure_glaucoma) VALUES ('vision', 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, glaucoma, angle_closure_glaucoma, gastritis) VALUES ('nausea', 1, 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, angle_closure_glaucoma, gastritis) VALUES ('vomiting', 1, 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, cervical_spondylosis) VALUES ('weakness', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, cervical_spondylosis) VALUES ('tingle', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('thirst', 1);";
          st.executeUpdate(sql);         
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('urination', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, diabetes) VALUES ('weight', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, cardiovascular_disease) VALUES ('angina', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, depression) VALUES ('unhappyness', 1);";
          st.executeUpdate(sql);    
          sql = "INSERT INTO symptom_data (symptom, depression) VALUES ('sadness', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, common_cold) VALUES ('coughing', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, common_cold) VALUES ('sneezing', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, diarrhea) VALUES ('stools', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, diarrhea) VALUES ('dehydration', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, arthritis) VALUES ('stiffness', 1);";
          st.executeUpdate(sql); 
          sql = "INSERT INTO symptom_data (symptom, heart_attack) VALUES ('discomfort', 1);";
          st.executeUpdate(sql); 


          System.out.println("Success");
          
      } catch (SQLException ex) {
          Logger lgr = Logger.getLogger(CreateTable.class.getName());
          lgr.log(Level.SEVERE, ex.getMessage(), ex);

      } 
    }
}