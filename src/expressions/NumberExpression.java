package expressions;

import tokens.NumberToken;


/**
 * NumberExpression stellt eine konstante Zahl als Expression dar. Diese kann in einem Syntaxbaum als Blattknoten
 * genutzt werden.
 */
public class NumberExpression extends Expression {
    private double number;

    /**
     * Initialisiert die NumberExpression.
     * @param number Darzustellende Zahl.
     */
    public NumberExpression(NumberToken token) {
    	this.number = token.getNumber();
    }

    /**
     * eval wertet die NumberExpression aus. Die darzustellende Zahl wird unverändert zurückgegeben.
     *
     * @param x Der für x einzusetzende Wert. Der Parameter x muss hier nicht benutzt werden, da eine konstante Zahl
     *          dargestellt wird.
     * @return Durch diese NumberExpression dargestellte Zahl.
     */
    @Override
    public double eval(double x) {
		return this.number;

    }
}
