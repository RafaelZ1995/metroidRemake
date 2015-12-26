package neonPlatformer.abilities;

import java.util.Stack;

public class TimeRewind {

	private Stack<Float> xCor = new Stack<Float>();
	private Stack<Float> yCor = new Stack<Float>();
	
	//duration of ability
	private float duration;
	
	public TimeRewind(float duration){
		
	}
	
	public void tick(float x, float y){
		xCor.add(x);
		yCor.add(y);
		
	}
	
	//getters
	public Stack<Float> getxCor() {
		return xCor;
	}


	public Stack<Float> getyCor() {
		return yCor;
	}


}
