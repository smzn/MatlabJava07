package matlabjava07;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;


public class MatlabJava07_lib {

	Future<MatlabEngine> eng;
	MatlabEngine ml;
	double data[][];
	
	public MatlabJava07_lib(double[][] data) {
		this.eng = MatlabEngine.startMatlabAsync();
		this.data = data;
		
		try {
			ml = eng.get();
			ml.putVariableAsync("data", data);
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//https://jp.mathworks.com/help/nnet/gs/classify-patterns-with-a-neural-network.html
	//https://jp.mathworks.com/help/nnet/examples/wine-classification.html
	public double[][] getNeural(int [][]targets) {
		double[][] outputs = null;
		try {
			ml.putVariableAsync("data", data);
			ml.putVariableAsync("outputs", outputs);
			ml.putVariableAsync("targets", targets);
			ml.eval("inputs = data(:,1:12);");
			ml.eval("inputs = inputs';");
			ml.eval("targets = targets';");
			ml.eval("hiddenLayerSize = 3;");
			ml.eval("net = patternnet(hiddenLayerSize);");
			ml.eval("net.divideParam.trainRatio = 70/100;");
			ml.eval("net.divideParam.valRatio = 15/100;");
			ml.eval("net.divideParam.testRatio = 15/100;");
			ml.eval("[net,tr] = train(net,inputs,targets)");
			ml.eval("pause(5);");
			//ml.eval("nntraintool;");
			ml.eval("outputs = net(inputs);");
			//ml.eval("errors = gsubtract(targets,outputs)"); //エラーになってしまう
			//ml.eval("performance = perform(net,targets,outputs)"); //エラーになってしまう
			
			ml.eval("view(net)");
			ml.eval("pause(5);");
			
			ml.eval("plotperform(tr)");
			ml.eval("saveas(gcf,'plotperform(tr).png');");
			ml.eval("pause(5);");
			
			ml.eval("plottrainstate(tr)");
			ml.eval("saveas(gcf,'plottrainstate(tr).png');");
			ml.eval("pause(5);");
			
			ml.eval("plotconfusion(targets,outputs);");
			ml.eval("saveas(gcf,'plotconfusion(targets,outputs).png');");
			ml.eval("pause(5);");
			
			ml.eval("[c,cm] = confusion(targets,outputs)");
			ml.eval("fprintf('Percentage Correct Classification   : %f%%\\n', 100*(1-c));");
			ml.eval("fprintf('Percentage Incorrect Classification : %f%%\\n', 100*c);");
			
			ml.eval("plotroc(targets,outputs)");
			ml.eval("saveas(gcf,'plotroc(targets,outputs).png')");
			ml.eval("pause(5);");
			
			//ml.eval("ploterrhist(errors);"); //エラーがでる
			//ml.eval("saveas(gcf,'ploterrhist(errors).png')");
			//ml.eval("pause(5);");
			
			Future<double[][]> futureEval_outputs = ml.getVariableAsync("outputs");
			outputs = futureEval_outputs.get();
			
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputs;
	}
	
}
