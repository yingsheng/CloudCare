package diagnoser;

import java.util.*;

public class DiseaseMatrix {
	public HashMap<String, Integer[]> matrix;
	public ArrayList<String> symptomIndex;

	static public DiseaseMatrix genRandData(Integer row, Integer col) {
		DiseaseMatrix answer = new DiseaseMatrix();
		Random r = new Random(System.nanoTime());

		for (int i = 0; i < col; ++i) {
			String f = "f" + r.nextInt(col / 2 + 1);
			String l = "$l" + r.nextInt(col / 2 + 1);
			answer.symptomIndex.add(f + l);
		}
		
		Integer pubIndex = r.nextInt(col);
		
		for(int i = 0; i < row; ++i){
			String d = "d" + i;
			Integer[] array = new Integer[col];
			for(int j = 0; j < col; ++j){
				array[j] = r.nextInt(2);
			}
			array[pubIndex] = 1;
		}

		return answer;
	}
}
