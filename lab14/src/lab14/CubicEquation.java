package lab14;

import java.math.BigDecimal;
import java.util.ArrayList;

class CubicEquation {
	private BigDecimal a;
	private BigDecimal b;
	private BigDecimal c;
	private ArrayList<BigDecimal> roots;

	CubicEquation(BigDecimal aParam, BigDecimal bParam, BigDecimal cParam)
			throws NumberFormatException {
		if (aParam.compareTo(BigDecimal.ZERO) == 0)
			throw new NumberFormatException("It is not Cubic Equation!");
		a = aParam;
		b = bParam;
		c = cParam;
	}
	
	public ArrayList<BigDecimal> getRoots() {
		ArrayList<BigDecimal> roots = new ArrayList<>();

		BigDecimal bSquare = b.pow(2);
		BigDecimal subtrahend = BigDecimal.valueOf(4).multiply(a).multiply(c);
		BigDecimal delta = bSquare.subtract(subtrahend);

		BigDecimal nominator = b.negate();
		BigDecimal denominator = BigDecimal.valueOf(2).multiply(a);

		switch (delta.compareTo(BigDecimal.ZERO)) {
		case 1:
			BigDecimal deltaSqRoot = BigDecimal.valueOf(Math.sqrt(delta.doubleValue()));

			BigDecimal root1 = nominator.subtract(deltaSqRoot);
			root1 = root1.divide(denominator);
			roots.add(root1);

			BigDecimal root2 = nominator.add(deltaSqRoot);
			root2 = root2.divide(denominator);
			roots.add(root2);
			break;
		case 0:
			BigDecimal root = nominator.divide(denominator);
			roots.add(root);
		}

		return roots;
	}
}