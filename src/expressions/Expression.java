package expressions;

import tokens.FunctionToken;
import tokens.NumberToken;
import tokens.Token;

import java.util.Stack;

import shuntingyard.ShuntingYard;

/**
 * Expression stellt einen Ausdruck dar, der mit der eval()-Methode ausgewertet
 * werden kann. Der Ausdruck kann eine Variable x enthalten, die als Argument an
 * eval übergeben wird.
 */
public abstract class Expression {
	protected Expression() {
	}

	/**
	 * eval wertet den Ausdruck aus. Dabei wird der übergebene Wert für die Variable
	 * x eingesetzt.
	 *
	 * @param x Der für x einzusetzende Wert. Der Parameter x sollte stets übergeben
	 *          werden, muss aber von Tochterklassen nicht benutzt werden, falls der
	 *          dargestellte Ausdruck kein x enthält.
	 * @return Das Ergebnis des Ausdrucks mit dem ggf. eingesetzten x.
	 */
	public abstract double eval(double x);
	
	/**
	 * parseInfixString liest ein String mit einem mathematischen Ausdruck in infix Notation ein, und
	 * zerteilt und "verpackt" diesen in ein Array aus Tokens.
	 * 
	 * Zuerst wird mit der Methode 'tokenize' aus dem Eingabestring ein Array aus Tokens erzeugt. 
	 * Anschließend werden dieses Tokens genutzt um mit 'convertToRPN' einen Syntaxbaum in umgekehrt polnischer Notation
	 * zu erzeugen. Dieser Syntaxbaum wird dann von 'parseRPN' in eine Expression umgewandelt.
	 *
	 * @param String, der einen mathematischen Ausdruck infix Notation darstellt.
	 * @return Eine Expression, die rekursiv den kompletten Ausdruck darstellt.
	 */
	public static Expression parseInfixString(String s) {
		return parseRPN(ShuntingYard.convertToRPN(Token.tokenize(s)));
	}
	
	/**
	 * parseRPN liest ein Array aus Tokens, die in umgekehrter polnischer Notation
	 * sortiert sein müssen, und konstruiert daraus einen Syntaxbaum aus
	 * Expression-Tochterklassen.
	 *
	 * Die Konstruktion des Syntaxbaumes funktioniert so: Gehe das Array von vorne
	 * nach hinten durch und führe die folgenden Aktionen aus: * Wenn in tokens eine
	 * Zahl oder ein x angetroffen wird, wird eine NumberExpression/XExpression
	 * erzeugt und auf den Stack gelegt. * Wenn in tokens ein FunctionToken
	 * angetroffen wird, wird eine Expression vom Stack genommen, als
	 * Funktionsargument genutzt, und eine UnaryFunctionExpression mit diesem
	 * Argument erzeugt und auf den Stack gelegt. * Wenn in tokens ein
	 * Rechen-Operator angetroffen wird, werden zwei Expressions vom Stack genommen,
	 * als linke und rechte Operanden genutzt und eine OperatorExpression mit diesen
	 * Operanden erzeugt und auf den Stack gelegt. * Wirf an einer passenden Stelle
	 * eine IllegalArgumentException und fange an einer anderen Stelle eine
	 * EmptyStackException ab Durch die Wiederverwendung der Expressions auf dem
	 * Stack entsteht auf diese Weise nach und nach eine Baumstruktur.
	 *
	 * @param tokens Tokens, die einen mathematischen Ausdruck in umgekehrter
	 *               polnischer Notation darstellen.
	 * @return Eine Expression, die rekursiv den kompletten Ausdruck darstellt.
	 * @throws IllegalArgumentException wird im Fehlerfall geworfen.
	 */
	public static Expression parseRPN(Token[] tokens) {
		
		Stack<Expression> stack = new Stack<>();
		
		try {
			for (Token token : tokens) {
				
				switch (token.getType()) {
				case NUMBER:
					stack.push(new NumberExpression(((NumberToken)token)));
					break;

				case X:
					stack.push(new XExpression());
					break;

				case FUNCTION:
					stack.push(new UnaryFunctionExpression((FunctionToken)token,stack.pop()));
					break;

				case MINUS:
				case PLUS:
				case POWER:
				case TIMES:
				case DIVIDED:
					Expression right = stack.pop();
					Expression left = stack.pop();
					stack.push(new OperatorExpression(token, left, right)); // Seiten müssen vertauscht werden
					break;
					
				default:
					throw new IllegalArgumentException("\"" + token.toString() + "\" ist ein ungueltiger Token");

				}
			}
		}catch(Exception EmptyStackException) {
			throw new IllegalArgumentException("Ungueltiger Token Array");
		};
		
		if(stack.size()!= 1)
			throw new IllegalArgumentException("Mehr als 1 Element auf Stack");
		
		return stack.pop();
	}
}