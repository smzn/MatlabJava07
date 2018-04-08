package matlabjava07;

import java.util.Arrays;

public class MatlabJava07_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double data_neural[][] = new double[64][14];
		MySQL mysql = new MySQL(1); //csv取り込み
		data_neural = mysql.getCSV("csv/neural.csv", 64, 14);
		System.out.println("selectDataneural = "+Arrays.deepToString(data_neural));
		int targets[][] = new int[64][2]; //全データから結果データ部分を取り出す
		for(int i = 0; i < data_neural.length; i++) {
			for(int j = 0; j < 2; j++) targets[i][j] = (int) data_neural[i][12 + j];
		}
		System.out.println("targets = "+Arrays.deepToString(targets));
		
		MatlabJava07_lib mlib = new MatlabJava07_lib(data_neural);
		double [][] neural_result = mlib.getNeural(targets);
		System.out.println("NeuralNetwork = "+Arrays.deepToString(neural_result));

	}

}
