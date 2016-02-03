import java.util.Comparator;

public class FunctionComparator implements Comparator<AStarBO>{

	@Override
	public int compare(AStarBO as1, AStarBO as2) {
		int result = as1.getFunctionValue() - as2.getFunctionValue();
		if(result == 0){
			return as2.getActualCost()-as1.getActualCost();
		}
		return result;	
	}
	
}
