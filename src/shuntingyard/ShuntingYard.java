package shuntingyard;

import tokens.Token;
import expressions.TokenType;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ShuntingYard stellt die Methode convertToRPN() bereit, die ein Array von
 * Tokens in Infix-Notation mithilfe des Shunting-Yard-Algorithmus von Dijkstra
 * in ein Array von Tokens in umgekehrter polnischer Notation konvertiert.
 */
public class ShuntingYard {

	/**
	 * convertToRPN wandelt das Array tokens, das einen mathematischen Ausdruck in
	 * Infix-Schreibweise darstellt, in ein Array von Tokens um, das denselben
	 * Ausdruck in umgekehrter polnischer Notation darstellt.
	 *
	 * Hierfür wird der Shunting-Yard-Algorithmus genutzt, siehe
	 * https://en.m.wikipedia.org/wiki/Shunting-yard_algorithm
	 *
	 * @param tokens Tokens in Infix-Schreibweise.
	 * @return Tokens in umgekehrter polnischer Notation.
	 */
	public static Token[] convertToRPN(Token[] tokens) {
		// In result kann das neue Ergebnis Stück für Stück geschrieben werden
		ArrayList<Token> result = new ArrayList<>();
	
		Stack<Token> stack = new Stack<>();


		for (Token token : tokens) {

			switch (token.getType()) {
			case NUMBER:
			case X:
				result.add(token);
				break;

			case FUNCTION:
			case OPENING_PARENTHESIS:
				stack.push(token);
				break;

			case DIVIDED:
			case TIMES:
			case PLUS:
			case MINUS:
			case POWER:

			
				
				while (!stack.isEmpty() && stack.peek().getType() != TokenType.OPENING_PARENTHESIS
						&& (stack.peek().ranking() > token.ranking()
								|| (stack.peek().ranking() == token.ranking() && token.isLeftAssociative()))) {
					result.add(stack.pop());
				}

				stack.push(token);
				break;

			case CLOSING_PARENTHESIS:
				// pop everthing until the next "(" into result array
				while (stack.peek().getType() != TokenType.OPENING_PARENTHESIS) {
					result.add(stack.pop());
				}

				// throw "(" out
				stack.pop();

				// add Functions to result array
				if (!stack.isEmpty() && stack.peek().getType() == TokenType.FUNCTION) {
					result.add(stack.pop());
				}
				break;

			default:
				break;
			}
		}
		// pop rest of stack into result array
		while (stack.size() > 0) {
			if ((stack.peek().getType() != TokenType.OPENING_PARENTHESIS))
				result.add(stack.pop());
		}

		// result list ist converted back to Token array
		return result.toArray(new Token[result.size()]);
	}
}