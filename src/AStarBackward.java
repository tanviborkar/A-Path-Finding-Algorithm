import java.util.ArrayList;
import java.util.List;

public class AStarBackward {
//f(n)= g(n)+h(n);
//g(n)=Actual cost;
//h(n)=Manhattan Distance of current node and the goal node
	private Boolean[][] maze;
	private List<AStarBO> closedNodesList;
	private int actualCost = 0;
	private int heuristicValue;
	private boolean isGoalFound = false;
	private MetaDataBO initialData;
	private BinaryHeapImplementation heapObj;
	private List<Agent> agentsPath;
	boolean isTargetBlocked = false;
	private List<AStarBO> blockedNodesList;
	private List<AStarBO> nodesToAdd;
	private List<AStarBO> nodesObserved;
	private List<AStarBO> nodesExpanded;
	//private int counter = 0;
	//private int noOfUnblockedCells;
	private List<AStarBO> actualExpandedList;
	
	public AStarBackward(Boolean[][] maze, MetaDataBO initialData, int noOfUnblockedCells, int gType)
	{
		this.maze = maze;
		this.initialData = initialData;
		closedNodesList = new ArrayList<AStarBO>();
		heapObj = new BinaryHeapImplementation(gType);
		agentsPath = new ArrayList<Agent>();
		blockedNodesList = new ArrayList<AStarBO>();
		nodesToAdd = new ArrayList<AStarBO>();
		nodesObserved = new ArrayList<AStarBO>();
		nodesExpanded = new ArrayList<AStarBO>();
		//this.noOfUnblockedCells = noOfUnblockedCells;
		//counter = noOfUnblockedCells*noOfUnblockedCells;
		actualExpandedList = new ArrayList<AStarBO>();
	}
	
	//code for repeated backward A*
	public boolean startRepeatedBackwardAStar(){
		if(checkStartEndConditions()){
			generateAndMoveAgent(initialData.getStartXPoint(), initialData.getStartYPoint());
			
			if(!agentsPath.isEmpty()){
				while((!isTargetBlocked) && (!isGoalNode(agentsPath.get((agentsPath.size() - 1)).getCurrentX(), agentsPath.get((agentsPath.size() - 1)).getCurrentY(), initialData.getActualEndXPoint(), initialData.getActualEndYPoint()))){
					swapPoints();
					findBlockedCells(initialData.getEndXPoint(), initialData.getEndYPoint());
					findPathUsingAStar();
					if((closedNodesList != null)  && (!closedNodesList.isEmpty())){
						addExpandedNodes(closedNodesList);
						checkAgentAndMove();
					}
					clearResourcesForNextIteration();
				}
				if(!isTargetBlocked){
					for(Agent agent : agentsPath)
					{
						if(agentsPath.indexOf(agent) == 0){
							System.out.print("Start Node - "+agent.toString());
						}
						else if(agentsPath.indexOf(agent) == agentsPath.size() - 1){
							System.out.print(" --> End Node - "+agent.toString());
						}
						else{
							System.out.print(" --> "+agent.toString());
						}
					}
					System.out.println();
				}
				else{
					System.out.println("Target cannot be reached");
				}
			}
			else{
				System.out.println("Path not found");
			}
			System.out.println("No of nodes expanded : "+actualExpandedList.size());
			System.out.println("No of agent movements : "+(agentsPath.size() - 1));
			return true;
		}
		else{
			return false;
		}
		
	}
	
	//function for A*
	public void findPathUsingAStar(){
		heapObj.insertIntoHeap(generateCellObject(initialData.getStartXPoint(), initialData.getStartYPoint(), false, null));
		
		if(!heapObj.isOpenQueueEmpty()){
			AStarBO currentNodeObj = null;
			while((!heapObj.isOpenQueueEmpty()) && (!isGoalFound) /*&& (counter>0)*/){
				actualCost++;
				currentNodeObj = heapObj.fetchAndRemoveMinFromHeap();
				generateFourNeighbors(currentNodeObj);
				if(currentNodeObj.getParent() == null){
					closedNodesList.add(currentNodeObj);
				}
				else{
					modifyClosedList(currentNodeObj);
				}
			}
			if(isGoalFound){
				AStarBO targetCell = generateCellObject(initialData.getEndXPoint(), initialData.getEndYPoint(), false, currentNodeObj);
				modifyClosedList(targetCell);
			}
			else{
				/*if(counter>0){*/
					nodesObserved.add(new AStarBO(agentsPath.get(agentsPath.size() - 1).getCurrentX(), agentsPath.get(agentsPath.size() - 1).getCurrentY()));
					agentsPath.remove(agentsPath.size() - 1);
					closedNodesList.clear();
					if(agentsPath.isEmpty()){
						isTargetBlocked = true;
						System.out.println("Target cannot be reached");
					}
				/*}
				else{
					System.out.println("Target cannot be reacded");
					isTargetBlocked = true;
				}*/
			}
		}
	}
	
	//function to check whether the goal state is reached
	public boolean isGoalNode(int currentX, int currentY, int goalX, int goalY){
		if((currentX == goalX) && (currentY == goalY)){
			return true;
		}
		else{
			return false;
		}
	}
	
	//function to generate objects
	public AStarBO generateCellObject(int currentX, int currentY, boolean checkBlocked, AStarBO parentCell){
		AStarBO cellObject = new AStarBO();
		cellObject.setX(currentX);
		cellObject.setY(currentY);
		heuristicValue = hofn(currentX, currentY, initialData.getEndXPoint(), initialData.getEndYPoint());
		if(parentCell == null){
			cellObject.setActualCost(actualCost);
		}
		else{
			cellObject.setActualCost((parentCell.getActualCost() + 1));
		}
		cellObject.setParent(parentCell);
		cellObject.setHeuristicCost(heuristicValue);
		cellObject.setFunctionValue(heuristicValue + cellObject.getActualCost());
		
		return cellObject;
	}
	
	//function to get the neighbors of a cell
	public void generateFourNeighbors(AStarBO currentNodeObj){
		if(isNodeToBeGenerated(currentNodeObj.getX(), currentNodeObj.getY() -1)){
			insertIntoOpenNode(getNeighboringCells(currentNodeObj.getX(), currentNodeObj.getY() -1, isStartNode(currentNodeObj.getX(), currentNodeObj.getY()), currentNodeObj));
		}
		if(isNodeToBeGenerated(currentNodeObj.getX(), currentNodeObj.getY() +1)){
			insertIntoOpenNode(getNeighboringCells(currentNodeObj.getX(), currentNodeObj.getY() +1, isStartNode(currentNodeObj.getX(), currentNodeObj.getY()), currentNodeObj));
		}
		if(isNodeToBeGenerated(currentNodeObj.getX() -1, currentNodeObj.getY())){
			insertIntoOpenNode(getNeighboringCells(currentNodeObj.getX() - 1, currentNodeObj.getY(), isStartNode(currentNodeObj.getX(), currentNodeObj.getY()), currentNodeObj));
		}
		if(isNodeToBeGenerated(currentNodeObj.getX() +1, currentNodeObj.getY())){
			insertIntoOpenNode(getNeighboringCells(currentNodeObj.getX() + 1, currentNodeObj.getY(), isStartNode(currentNodeObj.getX(), currentNodeObj.getY()), currentNodeObj));
		}
	}
	
	public void insertIntoOpenNode(AStarBO neighborNode){
		if(neighborNode != null){
			if((!blockedNodesList.contains(neighborNode))){
				if(!heapObj.containsNode(neighborNode)){
					if(!closedNodesList.contains(neighborNode)){
						heapObj.insertIntoHeap(neighborNode);
					}
				}
				else{
					compareAndInsertNodes(neighborNode);
				}
			}
			//counter--;
		}
	}
	
	public AStarBO getNeighboringCells(int neighborX, int neighborY, boolean checkBlocked, AStarBO parentCell){
		if(exists(neighborX, neighborY, initialData.getGridSize()) == 1){
			if(isGoalNode(neighborX, neighborY, initialData.getEndXPoint(), initialData.getEndYPoint())){
				isGoalFound = true;
			}
			else {
				return generateCellObject(neighborX, neighborY, checkBlocked, parentCell);
			}
		}
		return null;
	}
	
	public void compareAndInsertNodes(AStarBO currentObj){
		AStarBO existingNode = heapObj.retrieveElementFromQueue(currentObj);
		
		if(currentObj.getActualCost() < existingNode.getActualCost()){
			heapObj.removeFromHeap(existingNode);
			heapObj.insertIntoHeap(currentObj);
		}
	}
	
	public int hofn(int a,int b, int c,int d)
	{
		int hdist=0;
		int horizontal= c-a;
		int vertical=d-b;
		if(horizontal<0)
		{
			horizontal*=(-1);
		}
		if(vertical<0)
		{
			vertical*=(-1);
		}
		hdist=horizontal+vertical;
		return hdist;
	}
	
	public int exists(int a,int b,int gridsize)
	{	
		if(a>=0&&a<gridsize&&b>=0&&b<gridsize)
		{
			return 1;
		}
		else
		return 0;
	}

	public int block(int a,int b)
	{
		if(this.maze[a][b]==false)
		{
			return 0;
		}
		else{
			return 1;
		}
	}
	
	//code for creating and moving agents
	public void generateAndMoveAgent(int moveX, int moveY){
		Agent agent = new Agent();
		agent.setCurrentX(moveX);
		agent.setCurrentY(moveY);
		agentsPath.add(agent);
	}
	
	//checking for start node
	public boolean isStartNode(int x, int y){
		if((x == initialData.getStartXPoint()) && (y == initialData.getStartYPoint())){
			return true;
		}
		else{
			return false;
		}
	}
	
	//checking the path returned by A* and adding it to actual path
	public void checkAgentAndMove(){
		for(int i=(closedNodesList.size() - 2); i>=0; i--){
				AStarBO currentCell = closedNodesList.get(i);
				if(agentsPath.contains(new Agent(currentCell.getX(), currentCell.getY()))){
					int index = agentsPath.indexOf(new Agent(currentCell.getX(), currentCell.getY()));
					removeAllChildrenOfAgent(index);
				}
				else{
					if(block(currentCell.getX(), currentCell.getY()) == 1){
						generateAndMoveAgent(currentCell.getX(), currentCell.getY());
					}
					else{
						break;
					}
				}
			}		
	}
	
	//code to clear and set data for next iteration of A*
	public void clearResourcesForNextIteration(){
		heapObj.clearList();
		closedNodesList.clear();
		nodesExpanded.clear();
		actualCost = 0;
		if(!agentsPath.isEmpty()){
			initialData.setStartXPoint(agentsPath.get(agentsPath.size() - 1).getCurrentX());
			initialData.setStartYPoint(agentsPath.get(agentsPath.size() - 1).getCurrentY());
		}
		isGoalFound = false;
		//counter = noOfUnblockedCells * noOfUnblockedCells;
	}
	
	//code to clear stuff before call to backward function
	public void clearAllResources(){
		clearResourcesForNextIteration();
		agentsPath.clear();
		blockedNodesList.clear();
		isTargetBlocked = false;
	}
	
	//code to check whether the start and end nodes are the same
	public boolean checkStartEndConditions(){
		if(isGoalNode(initialData.getStartXPoint(),  initialData.getStartYPoint(), initialData.getActualEndXPoint(), initialData.getActualEndYPoint())){
			System.out.println("Start state is also the end state");
			return false;
		}
		else if(!maze[initialData.getStartXPoint()][initialData.getStartYPoint()]){
			System.out.println("The start state is blocked. Select a new start state");
			return false;
		}
		else if(!maze[initialData.getEndXPoint()][initialData.getEndYPoint()]){
			System.out.println("Target is blocked. Select a different target state");
			return false;
		}
		return true;
	}
	
	//code to swap start and end points
	public void swapPoints(){
		initialData.setEndXPoint(initialData.getStartXPoint());
		initialData.setEndYPoint(initialData.getStartYPoint());
		initialData.setStartXPoint(initialData.getActualEndXPoint());
		initialData.setStartYPoint(initialData.getActualEndYPoint());
	}
	
	//code to add blocked cells in list for backward A*
	public void findBlockedCells(int startX, int startY){
		if(exists(startX+1, startY, initialData.getGridSize())== 1){
			if(block(startX+1, startY) == 0){
				blockedNodesList.add(new AStarBO(startX+1, startY));
			}
		}
		if(exists(startX-1, startY, initialData.getGridSize())== 1){
			if(block(startX-1, startY) == 0){
				blockedNodesList.add(new AStarBO(startX-1, startY));
			}
		}
		if(exists(startX, startY+1, initialData.getGridSize())== 1){
			if(block(startX, startY+1) == 0){
				blockedNodesList.add(new AStarBO(startX, startY+1));
			}
		}
		if(exists(startX, startY-1, initialData.getGridSize())== 1){
			if(block(startX, startY-1) == 0){
				blockedNodesList.add(new AStarBO(startX, startY-1));
			}
		}
	}
	
	public void reSetInitialData(MetaDataBO initialData){
		this.initialData = initialData;
	}
	
	//code to calculate actual cost
	public void modifyClosedList(AStarBO currentObj){
		if(!currentObj.getParent().equals(closedNodesList.get(closedNodesList.size() - 1))){
			AStarBO modifiableObj = currentObj;
			int index = closedNodesList.indexOf(modifiableObj.getParent());
			AStarBO parentOfCurrentChild;
			 
			while(index == -1){
				parentOfCurrentChild = modifiableObj.getParent();
				index = closedNodesList.indexOf(parentOfCurrentChild.getParent());
				modifiableObj = parentOfCurrentChild;
				nodesToAdd.add(parentOfCurrentChild);
			}
			nodesExpanded.addAll(closedNodesList.subList((index + 1), closedNodesList.size()));
			closedNodesList.retainAll(closedNodesList.subList(0, (index + 1)));
			if(!nodesToAdd.isEmpty()){
				for(int i=(nodesToAdd.size() - 1); i>=0; i--){
					closedNodesList.add(nodesToAdd.get(i));
					if(heapObj.containsNode(nodesToAdd.get(i))){
						heapObj.removeFromHeap(nodesToAdd.get(i));
					}
				}
				nodesToAdd.clear();
			}
		}
		closedNodesList.add(currentObj);
	}
	
	public void removeAllChildrenOfAgent(int index){
		agentsPath.retainAll(agentsPath.subList(0, index + 1));
	}
	
	//code to check if a node is to be generated
		public boolean isNodeToBeGenerated(int x, int y){
			if((!isGoalFound) && (!nodesExpanded.contains(new AStarBO(x, y))) && (!nodesObserved.contains(new AStarBO(x, y)))){
				return true;
			}
			return false;
		}
		
		//code to keep a count of expanded nodes
		public void addExpandedNodes(List<AStarBO> closedNodesList){
			for(AStarBO currentNode : closedNodesList){
				if(!actualExpandedList.contains(currentNode)){
					actualExpandedList.add(currentNode);
				}
			}
		}
		
		//code to get count of nodes expanded
		public int countOfExpandedNodes(){
			if(!actualExpandedList.isEmpty()){
				return actualExpandedList.size();
			}
			else{
				return 0;
			}
		}
}
