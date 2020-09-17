

import java.util.Scanner;
import java.util.function.Predicate;


public class FundScreen {

	public static final String LIST = "List";
	public static final String EXCLUDE = "Exclude";
	public static final String KEEP = "Retain";
	public static final String RESET_PORTFOLIO = "Reset Portfolio";
	public static final String EXIT = "Exit";

	public static final String TICKER = "Ticker";
	public static final String AVG_HOLDING_SIZE = "Average Holding Size";
	public static final String MIN_INVESTMENT = "Minimum Investment";
	public static final String VALUE_MEASURE = "Value Measure";
	public static final String DOMICILE = "Domicile";

	public static final String[] BIG_MENU = { LIST, EXCLUDE, KEEP, RESET_PORTFOLIO, EXIT, };

	public static final String[] FUND_FIELDS = { TICKER, AVG_HOLDING_SIZE, MIN_INVESTMENT, VALUE_MEASURE, DOMICILE, };

	public static void main(String[] args) {
		Scanner kybd = new Scanner(System.in);
		Portfolio portfolio = new Portfolio();
		String userChoice = "";
		while (!EXIT.equals(userChoice)) {
			userChoice = Utils.userChoose(kybd, BIG_MENU);
			if (LIST.equals(userChoice)) {
				System.out.println("Current Portfolio");
				Utils.userDisplay(kybd, portfolio);
			} else if (EXCLUDE.equals(userChoice)) {
				exclude(kybd, portfolio);
			} else if (KEEP.equals(userChoice)) {
				keep(kybd, portfolio);
			} else if (RESET_PORTFOLIO.equals(userChoice)) {
				portfolio.refresh();
			}
		}
	}

	private static void keep(Scanner kybd, Portfolio portfolio) {
		System.out.println("What field and values identify funds you wish to keep?");
		String keeping = Utils.userChoose(kybd, FUND_FIELDS);
		if (TICKER.equals(keeping)) {
			System.out.print("Which ticker? ");
			String ticker = kybd.next().toUpperCase();
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return !(ticker.equals(t));
				}
			});

		} else if (AVG_HOLDING_SIZE.equals(keeping)) {
			System.out.print("Lower limit for holding size: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for holding size: ");
			double ceiling = kybd.nextDouble();
			double avg = (floor + ceiling) / 2;
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return !(avg == t.getAvgHoldingSize());
				}
			});

		} else if (MIN_INVESTMENT.equals(keeping)) {
			System.out.print("Lower limit for minimum investment: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for minimum investment: ");
			double ceiling = kybd.nextDouble();
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return !(t.getMinimumInvestment() >= floor && t.getMinimumInvestment() <= ceiling );
				}
			});


		} else if (VALUE_MEASURE.equals(keeping)) {
			System.out.print("Lower limit for value: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for value: ");
			double ceiling = kybd.nextDouble();
			double min = Math.min(floor,ceiling);
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return !(min == t.getMinimumInvestment());
				}
			});



		} else if (DOMICILE.equals(keeping)) {
			keeping = Utils.userChoose(kybd, MutualFund.MARKET.values());
			MutualFund.MARKET mkt = MutualFund.MARKET.valueOf(keeping);
			// this one is supplied for you
			portfolio.removeIf(new Predicate<MutualFund>() {

				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return !(mkt == t.getDomicile());
				}
			});

		}
	}

	private static void exclude(Scanner kybd, Portfolio portfolio) {
		System.out.println("What field and values identify funds you wish to remove?");
		String removing = Utils.userChoose(kybd, FUND_FIELDS);
		if (TICKER.equals(removing)) {
			System.out.print("Which ticker? ");
			String ticker = kybd.next().toUpperCase();
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return (ticker.equals(t));
				}
			});


		} else if (AVG_HOLDING_SIZE.equals(removing)) {
			System.out.print("Lower limit for holding size: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for holding size: ");
			double ceiling = kybd.nextDouble();
			double avg = (floor + ceiling)/2;
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund mutualFund) {
					System.out.println(mutualFund);
					return (avg == mutualFund.getAvgHoldingSize());
				}
			});

		} else if (MIN_INVESTMENT.equals(removing)) {
			System.out.print("Lower limit for minimum investment: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for minimum investment: ");
			double ceiling = kybd.nextDouble();
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return (t.getMinimumInvestment() >= floor && t.getMinimumInvestment() <= ceiling );
				}
			});
			
		} else if (VALUE_MEASURE.equals(removing)) {
			System.out.print("Lower limit for value: ");
			double floor = kybd.nextDouble();
			System.out.print("Upper limit for value: ");
			double ceiling = kybd.nextDouble();
			double min = Math.min(floor,ceiling);
			portfolio.removeIf(new Predicate<MutualFund>() {
				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return (min == t.getMinimumInvestment());
				}
			});
		} else if (DOMICILE.equals(removing)) {
			removing = Utils.userChoose(kybd, MutualFund.MARKET.values());
			MutualFund.MARKET mkt = MutualFund.MARKET.valueOf(removing);
			portfolio.removeIf(new Predicate<MutualFund>() {

				@Override
				public boolean test(MutualFund t) {
					System.out.println(t);
					return (mkt == t.getDomicile());
				}
			});
		}
	}
}
