
public class Agent {

	private int currentX;
	private int currentY;
	private int actualCost;
	
	public Agent() {
		super();
	}

	public Agent(int currentX, int currentY) {
		super();
		this.currentX = currentX;
		this.currentY = currentY;
	}
	
	public int getCurrentX() {
		return currentX;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	public int getCurrentY() {
		return currentY;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	public int getActualCost() {
		return actualCost;
	}
	public void setActualCost(int actualCost) {
		this.actualCost = actualCost;
	}
	
	@Override
	  public boolean equals(Object currentObj) {
	        boolean retVal = false;

	        if (currentObj instanceof Agent){
	        	Agent obj = (Agent) currentObj;
	        	if((obj.currentX == this.currentX) && (obj.currentY == this.currentY)){
	        		retVal = true;
	        	}
	        }
	     return retVal;
	  }
	
	@Override
	public String toString() {
		return "("+currentX+","+currentY+")"; 
	}
}
