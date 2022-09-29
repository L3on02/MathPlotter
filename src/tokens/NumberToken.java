package tokens;

import expressions.TokenType;

/**
 * NumberToken beschreibt eine konstante Zahl.
 */
public class NumberToken extends Token {
    private double number;
    
    /**
     * Erzeugt einen Token, der eine konstante Zahl beschreibt.
     * @param number Zahl, die beschrieben wird.
     */
    public NumberToken(double num) {
    	this.type = TokenType.NUMBER;
    	this.number = num;
    }
    
    /**
     * getNumber gibt die Zahl zurück, die dieser Token darstellt.
     * @return Dargestellte Zahl.
     */
	 public double getNumber() {
    	return this.number;
    }

    /**
     * toString gibt die Zahl als String zurück.
     * @return Dargestellt Zahl als String.
     */
    @Override
    public String toString() {
        return Double.valueOf(number).toString();
    }
}
