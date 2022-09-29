package tokens;

import java.util.ArrayList;

import expressions.TokenType;

/**
 * Ein Token ist ein zusammengehöriges Symbol, das Teil eines mathematischen Ausdrucks ist. Es entspricht ungefähr
 * einem "Wort"; hier kann es entweder Operatoren oder Operanden von Rechenoperationen darstellen. Eine einzelne Zahl
 * (nicht Ziffer) ist bspw. ein Token, die Variable x ist ein Token, ein Operator ist ein Token, der Name einer Funktion
 * ist ein Token.
 */
public class Token {

    protected TokenType type;

    /**
     * Es soll kein Token ohne Type konstruiert werden, außer von Unterklassen.
     */
    protected Token() {	
    }

    /**
     * Erzeugt einen Token mit dem angegebenen Typ.
     * @param type Tokentyp.
     */
    public Token(TokenType type) {
    	
    	if (type == TokenType.NUMBER) {
            throw new IllegalArgumentException("NumberToken muss mit NumberToken(number) erzeugt werden");
        } else if (type == TokenType.FUNCTION) {
            throw new IllegalArgumentException("FunctionToken muss mit FunctionToken(name) erzeugt werden");
        }
    	
    	this.type = type;
    }

    /**
     * getType gibt den Typ dieses Tokens zurück.
     * @return Tokentyp.
     */
     public TokenType getType() {
    	 return this.type;
     }
     
     /**
      * Gibt zurück welchen Stellenwert ein Operator hat,
      * also wie früh er ausgewertet werden soll
      * @return Wertigkeit des Operators
      */
     public int ranking() {
         switch (this.type) {
             case PLUS:
             case MINUS:
                 return 1;
             case TIMES:
             case DIVIDED:
                 return 2;
             case POWER:
                 return 3;
             default:
                 return -1;
         }
     }

     /**
      * isLeftAssociate überprüft ob ein Token linksbünig ist oder nicht
      * @return Token linksbündig true oder false
      */
     public boolean isLeftAssociative() {
         return this.type != TokenType.POWER;
     }


    /**
     * toString gibt den Token in lesbarer Form aus.
     * @return Token als String.
     */
     public String toString() {
         switch (this.type) {
             case X:
                 return "x";
             case PLUS:
                 return "+";
             case MINUS:
                 return "-";
             case TIMES:
                 return "*";
             case DIVIDED:
                 return "/";
             case POWER:
                 return "^";
             case OPENING_PARENTHESIS:
                 return "(";
             case CLOSING_PARENTHESIS:
                 return ")";
             default:
                 throw new IllegalArgumentException("Kein toString für tokenType " + this.type);
         }
     }
     
     
     private static String longNums(char[] Chars, int pos) {
    	String result = "";
		if (pos < Chars.length) {
			if(Character.isDigit(Chars[pos]) || Chars[pos] == '.') {
				result = Chars[pos] + longNums(Chars, pos + 1);
			}
		}
    	return result;
     }
     
     public static Token[] tokenize(String s) {
    	ArrayList<Token> result = new ArrayList<>();
    	
    	char[] charArray = s.toCharArray();

    	for(int i = 0; i < charArray.length; i++) {
    		
    		 switch (charArray[i]) {
    		 case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
    			 String num = longNums(charArray, i);
    			 result.add(new NumberToken(Double.parseDouble(num)));
    			 i += (num.length() - 1);
    			 break;
    			 
    		 case '(':
    			 result.add(new Token(TokenType.OPENING_PARENTHESIS));
    			 break;
    			 
    		 case ')':
    			 result.add(new Token(TokenType.CLOSING_PARENTHESIS));
    			 break;
    			 
    		 case '+':
    			 result.add(new Token(TokenType.PLUS));
    			 break;
    			 
    		 case '-':
    			 if(i >=1 && charArray[i - 1] != '(') {
    				 result.add(new Token(TokenType.MINUS));
    			 }
    			 else {
    				 result.add(new Token(TokenType.OPENING_PARENTHESIS));
    				 result.add(new NumberToken(-1));
    				 result.add(new Token(TokenType.CLOSING_PARENTHESIS));
    				 result.add(new Token(TokenType.TIMES));
    			 }
    			 break;
    			 
    		 case '*':
    			 result.add(new Token(TokenType.TIMES));
    			 break;
    			 
    		 case '/':
    			 result.add(new Token(TokenType.DIVIDED));
    			 break;
    			 
    		 case '^':
    			 result.add(new Token(TokenType.POWER));
    			 break;
    			 
    		 case 'x':
    			 result.add(new Token(TokenType.X));
    			 break;
    			 
    		 case 'i': // i ist ausschlieslich in sin enthalten, und kann daher dafür genutzt werden
    			 result.add(new FunctionToken("sin"));
    			 break;
    			 
    		 case 'q': // q ist ausschlieslich in sqrt enthalten, und kann daher dafür genutzt werden
    			 result.add(new FunctionToken("sqrt"));
    			 break;
    			 
    		 case 'c':
    			 result.add(new FunctionToken("cos"));
    			 break;
    			 
    		 case 'a': // a ist ausschlieslich in tan enthalten, und kann daher dafür genutzt werden
    			 result.add(new FunctionToken("tan"));
    			 break;
    			 
    		 case 'l':
    			 result.add(new FunctionToken("log"));
    			 break;
    			
    		 default:
    			 // macht nichts, das bedeuted unnötige Zeichen aus zb l(og) oder c(os) werden einfach ignoriert
    		 }
    	 }
    	 
    	 return result.toArray(new Token[result.size()]);
     }
}
