import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MazeGenerator {
	private MetaDataBO initialData;
	private int noOfGrids;
	Map<Integer, Boolean[][]> gridMaps;
	
	public MazeGenerator(MetaDataBO initialData, int noOfGrids) {
		this.initialData = initialData;
		this.noOfGrids = noOfGrids;
		gridMaps = new TreeMap<Integer, Boolean[][]>();
	}

	public void generateGrid(){
		Boolean[][] maze = null;
		int gridSize=initialData.getGridSize();	//take input from user
		
		int blockedProbability = (int) Math.round(.3*gridSize*gridSize);	//the no. of blocked cells 
		
		for(int l=1;l<=noOfGrids;l++){
			maze = new Boolean[gridSize][gridSize];
			//initializing the maze to ublocked state
			for(int i=0; i<gridSize;i++){
				for(int j=0;j<gridSize;j++){
					maze[i][j] = true;
				}
			}
			
			// generating the Blocked cells
			Random randomNumber = new Random();
			int countVisited = 0;
			while(countVisited <blockedProbability){
				int k = randomNumber.nextInt(gridSize);
				int j = randomNumber.nextInt(gridSize);
				if(maze[k][j] != false){
					maze[k][j] = false;
					countVisited++;
				}
			}
			
			gridMaps.put((l), maze);
			displayGrid(maze);
		}
	}
	
	//code to display the grids
	public void displayGrid(Boolean[][] maze){
		for(int i=0; i<initialData.getGridSize();i++){
			for(int j=0;j<initialData.getGridSize();j++){
				System.out.print("[");
				if(maze[i][j] == true)
					System.out.print(1);
				else{
					System.out.print(0);
				}
				System.out.print("]");
			}
			System.out.println();
		}
	}
	
	public Boolean[][] selectGrid(int gridIndex){
		return gridMaps.get(gridIndex);
	}
}
