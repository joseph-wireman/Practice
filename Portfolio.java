

import java.util.ArrayList;
import java.util.Arrays;

public class Portfolio extends ArrayList<MutualFund> {
	
	public Portfolio() {
		addAll(Arrays.asList(FundsDef.INITIAL_FUNDS));
	}
	
	public void refresh() {
		clear();
		addAll(Arrays.asList(FundsDef.INITIAL_FUNDS));
	}

}
