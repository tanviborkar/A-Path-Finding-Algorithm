
public class AStarBO {

	private int x;
	private int y;
	private boolean isObserved = false;
	private int functionValue;
	private int actualCost;
	private int heuristicCost;
	private boolean blockedState = true;
	private AStarBO parent;
	private int newHeuristicCost;

	public AStarBO() {
		super();
	}

	public AStarBO(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public AStarBO(int x, int y, boolean isObserved, int functionValue, int actualCost, int heuristicCost,
			boolean blockedState, AStarBO parent) {
		super();
		this.x = x;
		this.y = y;
		this.isObserved = isObserved;
		this.functionValue = functionValue;
		this.actualCost = actualCost;
		this.heuristicCost = heuristicCost;
		this.blockedState = blockedState;
		this.parent = parent;
	}

	public boolean isBlockedState() {
		return blockedState;
	}

	public void setBlockedState(boolean blockedState) {
		this.blockedState = blockedState;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isObserved() {
		return isObserved;
	}

	public void setObserved(boolean isObserved) {
		this.isObserved = isObserved;
	}

	public int getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(int functionValue) {
		this.functionValue = functionValue;
	}

	public int getActualCost() {
		return actualCost;
	}

	public void setActualCost(int actualCost) {
		this.actualCost = actualCost;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(int heuristicCost) {
		this.heuristicCost = heuristicCost;
	}

	public AStarBO getParent() {
		return parent;
	}

	public void setParent(AStarBO parent) {
		this.parent = parent;
	}
	
	public int getNewHeuristicCost() {
		return newHeuristicCost;
	}

	public void setNewHeuristicCost(int newHeuristicCost) {
		this.newHeuristicCost = newHeuristicCost;
	}

	@Override
	public String toString() {
		return "X - "+x+ " Y - "+y+" newHeuristicCost - "+newHeuristicCost+ "actual cost - "+actualCost;
	}
	  public boolean equals(Object currentObj) {
	        boolean retVal = false;

	        if (currentObj instanceof AStarBO){
	        	AStarBO obj = (AStarBO) currentObj;
	        	if((obj.x == this.x) && (obj.y == this.y)){
	        		retVal = true;
	        	}
	        }
	     return retVal;
	  }
}
