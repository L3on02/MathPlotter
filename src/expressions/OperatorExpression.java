package expressions;

import tokens.Token;


/**
 * OperatorExpression stellt die Verknüpfung zweier Expressions mit einem
 * binären Operator (+ - * / ^) als Expression dar. Dies kann genutzt werden, um
 * einen binären Knoten in einem Syntaxbaum darzustellen. Die eval()-Methode
 * ruft die eval()-Methoden der beiden Kind-Expressions auf und verknüpft die
 * Ergebnisse.
 */
public class OperatorExpression extends Expression {
	private TokenType tp;
	private Expression left;
	private Expression right;

	/**
	 * Erstellt eine OperatorExpression.
	 *
	 * Ergänze einen default case um eine IllegalArgumentException mit
	 * aussagekräftiger Fehlermeldung!
	 *
	 * @param tp    Art der Verknüpfung (+ - * / ^).
	 * @param left  Expression auf der linken Seite des Operators.
	 * @param right Expression auf der linken Seite des Operators.
	 * @throws IllegalArgumentException wird im Fehlerfall geworfen.
	 */
	public OperatorExpression(Token token, Expression left, Expression right) {
		switch(token.getType()){
		case PLUS:
		case MINUS:
		case TIMES:
		case DIVIDED:
		case POWER:
			this.tp = token.getType();
			break;
		default:
			throw new IllegalArgumentException("Ungültiger Token");
		}
		this.left = left;
		this.right = right;
	}
	
	/**
	 * eval wertet die OperatorExpression aus. Hierzu werden erst die
	 * eval()-Methoden von left und right aufgerufen, und dann mit der passenden
	 * Rechenoperation verknüpft.
	 *
	 * @param x Der für x einzusetzende Wert. Der Parameter x muss rekursiv an die
	 *          eval-Methode der Unter-Expressions left und right übergeben werden.
	 * @return Ergebnis der Verknüpfung "left (op) right".
	 */
	@Override
	public double eval(double x) {

		double lValue = left.eval(x);
		double rValue = right.eval(x);

		double result = 0;

		switch (this.tp) {
		case PLUS:
			result = lValue + rValue;
			break;

		case MINUS:
			result = lValue - rValue;
			break;

		case TIMES:
			result = lValue * rValue;
			break;

		case DIVIDED:
			result = lValue / rValue;
			break;

		case POWER:
			result = Math.pow(lValue, rValue);
			break;
			
		default:
			throw new IllegalStateException("Ungueltiger Operator!");
		}

		return result;
	}
}
