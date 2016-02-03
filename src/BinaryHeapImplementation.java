import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinaryHeapImplementation {
	
	private Queue<AStarBO> openNodesHeap;

	
	public BinaryHeapImplementation(int functionType) {
		super();
		if((functionType == 1) || (functionType == 3) || (functionType == 5)){
			openNodesHeap = new PriorityQueue<AStarBO>(100, new FunctionComparator());
		}
		else{
			openNodesHeap = new PriorityQueue<AStarBO>(100, new FunctionComparatorSmallerG());
		}
	}


	public BinaryHeapImplementation(Queue<AStarBO> openNodesHeap) {
		super();
		this.openNodesHeap = openNodesHeap;
	}
	
	public void insertIntoHeap(AStarBO insertObj){
		if(openNodesHeap == null){
			openNodesHeap = new PriorityQueue<AStarBO>();
		}
		openNodesHeap.add(insertObj);
	}
	
	public void removeFromHeap(AStarBO removeObj){
		if((openNodesHeap != null) && (openNodesHeap.contains(removeObj))){
			openNodesHeap.remove(removeObj);
			//sortHeap();
		}
		else{
			System.out.println("object not found in list");
		}
	}
	
	public AStarBO fetchAndRemoveMinFromHeap(){
		if((openNodesHeap != null) && (!openNodesHeap.isEmpty())){
			return openNodesHeap.poll();
			//sortHeap();
		}
		else{
			System.out.println("object not found in list");
			return null;
		}
	}
	
	public boolean isOpenQueueEmpty(){
		if(openNodesHeap.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean containsNode(AStarBO currentObj){
		if(openNodesHeap.contains(currentObj)){
			return true;
		}
		else{
			return false;
		}
	}
	
	//code to clear open nodes list
	public void clearList(){
		openNodesHeap.clear();
	}
	
	public void displayList(){
		System.out.println(openNodesHeap);
	}
	
	//code to get a particular element from queue
	public AStarBO retrieveElementFromQueue(AStarBO currentObj){
		AStarBO nextElement;
		Iterator<AStarBO> iterate = openNodesHeap.iterator();
		while(iterate.hasNext()){
			nextElement = iterate.next();
			if((currentObj.getX() == nextElement.getX()) && (currentObj.getY() == nextElement.getY())){
				return nextElement;
			}
		}
		return null;
	}
}
