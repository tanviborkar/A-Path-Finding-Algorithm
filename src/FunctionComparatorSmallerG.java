import java.util.Comparator;

public class FunctionComparatorSmallerG implements Comparator<AStarBO>{

	@Override
	public int compare(AStarBO as1, AStarBO as2) {
		int result = as1.getFunctionValue() - as2.getFunctionValue();
		if(result == 0){
			return as1.getActualCost()-as2.getActualCost();
		}
		return result;	
	}
	
}
