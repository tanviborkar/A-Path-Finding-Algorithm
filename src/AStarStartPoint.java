import java.util.Date;
import java.util.Scanner;

public class AStarStartPoint {

	public static void main(String[] args) {
		
		//code to take inputs from the user
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to A* implementation of maze puzzle");
		System.out.println("Enter the dimension of the grid");
		int gridSize = input.nextInt();
		
		System.out.println("Enter the number of grids");
		int noOfGrids = input.nextInt();
		
		//code to set the initial data in the metadata object
		MetaDataBO initialData = new MetaDataBO();
		initialData.setGridSize(gridSize);
		
		//code to generate mazes for user to choose		
		MazeGenerator mazeGen = new MazeGenerator(initialData, noOfGrids);
		mazeGen.generateGrid();
		
		int noOfBlockedCells = (int) Math.round(.3*gridSize*gridSize);
		int noOfUnblockedCells = (gridSize*gridSize) - noOfBlockedCells;
		
		System.out.println("Enter the number of the grid you want to work with");
		int gridIndex = input.nextInt();
		if(!checkGridRelatedInputs(gridIndex, noOfGrids)){
			System.out.println("The index selected is incorrect. Please try again");
		}
		else{
			System.out.println("Enter the start X position of the game");
			int startX = input.nextInt();
			System.out.println("Enter the start Y position of the game");
			int startY = input.nextInt();
			
			System.out.println("Enter the target X position of the game");
			int targetX = input.nextInt();
			System.out.println("Enter the target Y position of the game");
			int targetY = input.nextInt();
			
			input.close();
			if((checkStartEndPointsInputs(startX, startY, gridSize)) && (checkStartEndPointsInputs(targetX, targetY, gridSize))){
				//code to save the initial data
				resetInitialData(initialData, startX, startY, targetX, targetY);
						
				//code to call the A*
				Boolean[][] generatedMaze = mazeGen.selectGrid(gridIndex);
				Date startTime = null;
				Date endTime = null;
				
				//code to call Repeated forward A* with larger g-values for tie break
				AStarForward forward1 = new AStarForward(generatedMaze, initialData, noOfUnblockedCells, 1);
				startTime = new Date();
				if(forward1.startRepeatedForwardAStar()){
					endTime = new Date();
					calculateTimeTaken(startTime, endTime);
				}
				System.out.println("Repeated forward A* with larger g-values result");
				System.out.println();
				resetInitialData(initialData, startX, startY, targetX, targetY);
		
				//code to call Repeated forward A* with smaller g-values for tie break
				AStarForward forward2 = new AStarForward(generatedMaze, initialData, noOfUnblockedCells, 2);
				startTime = new Date();
				if(forward2.startRepeatedForwardAStar()){
					endTime = new Date();
					calculateTimeTaken(startTime, endTime);
				}
				System.out.println("Repeated forward A* with smaller g-values result");
				System.out.println();
				resetInitialData(initialData, startX, startY, targetX, targetY);
				
				//code to call Repeated backward A* with larger g-values for tie break
				AStarBackward backward1 = new AStarBackward(generatedMaze, initialData, noOfUnblockedCells, 3);
				startTime = new Date();
				if(backward1.startRepeatedBackwardAStar()){
					endTime = new Date();
					calculateTimeTaken(startTime, endTime);
				}
				System.out.println("Repeated backward A* with larger g-values result");
				System.out.println();
				resetInitialData(initialData, startX, startY, targetX, targetY);
				
				//code to call Repeated backward A* with smaller g-values for tie break
				/*AStarBackward backward2 = new AStarBackward(generatedMaze, initialData, noOfUnblockedCells, 4);
				startTime = new Date();
				if(backward2.startRepeatedBackwardAStar()){
					endTime = new Date();
					calculateTimeTaken(startTime, endTime);
				}
				System.out.println("Repeated backward A* with smaller g-values result");
				System.out.println();
				resetInitialData(initialData, startX, startY, targetX, targetY);*/
		
				AdaptiveForwardAStar adaptive = new AdaptiveForwardAStar(generatedMaze, initialData, noOfUnblockedCells, 5);
				startTime = new Date();
				if(adaptive.startAdaptiveForwardAStar()){
					endTime = new Date();
					calculateTimeTaken(startTime, endTime);
				}
				System.out.println("Adaptive Forward A* with larger g-values result");
				memoryUsage();
			}
		}
	}
	
	// code to reset the initial data
	public static void resetInitialData(MetaDataBO initialData, int startX, int startY, int targetX, int targetY){
		initialData.setStartXPoint(startX);
		initialData.setStartYPoint(startY);
		initialData.setEndXPoint(targetX);
		initialData.setEndYPoint(targetY);
		initialData.setActualEndXPoint(targetX);
		initialData.setActualEndYPoint(targetY);
	}
	
	public static void calculateTimeTaken(Date startTime, Date endTime){
		long timeDiff = endTime.getTime() - startTime.getTime(); 
		System.out.println("Time taken in milliseconds " + timeDiff);
	}
	
	public static void memoryUsage(){
		int mb = 1024*1024;
        
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println();
        System.out.println("##### Heap utilization statistics [MB] #####");
        System.out.println("Total Memory : "+runtime.totalMemory());
        //Print used memory
        System.out.println("Used Memory:"
            + (runtime.totalMemory() - runtime.freeMemory()) / mb);
	}
	
	public static boolean checkGridRelatedInputs(int gridIndex, int noOfGrids){
		if((gridIndex <= 0) || (gridIndex>noOfGrids)){
			//System.out.println("The index selected is incorrect. Please try again");
			return false;
		}
		return true;
	}
	
	public static boolean checkStartEndPointsInputs(int startEndX, int startEndY, int gridDimension){
		if((startEndX >= gridDimension) || (startEndX < 0) || (startEndY >= gridDimension) || (startEndY < 0)){
			System.out.println("The selected start or end points are incorrect. Please try again");
			return false;
		}
		return true;
	}
}
