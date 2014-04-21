package diagnoser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


// input: ArrayList<String>
// input: smallTableExist = {T, F}, T: search small table, F: search mysql database
// output: ArrayList<String> disease_list
public class diagnoser {

	private static String url = "jdbc:mysql://localhost/CloudCare";
	/*
	 * You need to create a database called "CloudCare"
	 */
	private static String user = "root";/* put your user id here */// "pqichen";
	private static String password = "mypass";/* put your password here */// "pqichen";

	// query string :
	// select disease from disease_data where [arm$tingle] = 1;

	public static DiseaseMatrix diagnose(String symptom) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String query = "select disease from disease_data where " + symptom + " = 1";
		
		ArrayList<String> symptoms = new ArrayList<String>();
		HashSet<String> symptomSet = new HashSet<String>();
		HashMap<String, Integer[]> matrix = new HashMap<String, Integer[]>();
		HashMap<String, ArrayList<String>> preMatrix = new HashMap<String, ArrayList<String>>();
		
		DiseaseMatrix answer = new DiseaseMatrix();
		answer.matrix = matrix;
		answer.symptomIndex = symptoms;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()){
				String disease = rs.getString("disease");
				ArrayList<String> symptomList = new ArrayList<String>();
				preMatrix.put(disease, symptomList);
				ResultSet rs2 = null;
				String query2 = "select symptom from symptom_data where " + disease + " = 1";
				rs2 = st.executeQuery(query2);
				while(rs2.next()){
					symptomList.add(rs2.getString(0));
					symptomSet.add(rs2.getString(0));
				}
			}
			for(String s : symptomSet){
				symptoms.add(s);
			}
			int length = symptoms.size();
			for(String d : preMatrix.keySet()){
				Integer[] row = new Integer[length];
				matrix.put(d, row);
				for(String s : preMatrix.get(d)){
					int index = symptoms.indexOf(s);
					row[index] = 1;
				}
			}
		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(DiseaseMatrix.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		} finally{
			if (st!=null) st.close();
		}
		return answer;
	}
}
