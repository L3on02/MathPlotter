package expressions;

import tokens.FunctionToken;

/**
 * UnaryFunctionExpression stellt eine unäre Funktion dar. Die Funktion wird mit ihrem Namen identifiziert.
 * Siehe switch-case in eval() für eine Liste der darstellbaren Funktionen. Die Funktion enthält die Unter-Expression
 * argument als Funktionsargument und stellt somit die Funktion name(argument) dar.
 */
public class UnaryFunctionExpression extends Expression {
    private final String name;
    private final Expression argument;

    /**
     * Initialisiert die UnaryFunctionExpression.
     *
     * @param name     Name der darzustellenden Funktion (z. B. sin, cos, sqrt), siehe eval() für unterstützte Funktionen.
     * @param argument Ausdruck, der als Funktionsargument genutzt wird.
     */
    public UnaryFunctionExpression(FunctionToken function, Expression arg) {
		this.name = function.getName();
		this.argument = arg;
    	
    };

    /**
     * Wertet die FunctionExpression aus. Hierbei wird zunächst die eval-Methode auf das argument aufgerufen
     * und dann in die angegeben Funktion eingesetzt.
     *
     * Nicht vergessen: default case mit einer IllegalArgumentException
     * mit aussagekräftiger Fehlermeldung!
     *
     * @param x Der für x einzusetzende Wert. Der Parameter x muss rekursiv an eval-Methode der Unter-Expression argument
     *          weitergegeben werden.
     * @return Der Wert der Funktion, ausgewertet an der Stelle, die dem ausgewerteten Wert von argument entspricht.
     * @throws IllegalArgumentException wird im Fehlerfall geworfen.
     */
    @Override
    public double eval(double x) {
    	
    	double value = argument.eval(x);
    	
    	double result = 0;
    	
    	switch (this.name) {
    		case "sin":
    			result = Math.sin(value);
    			break;
    			
    		case "cos":
    			result = Math.cos(value);
    			break;
    			
    		case "tan":
    			result = Math.tan(value);
    			break;
    			
    		case "log":
    			result = Math.log(value);
    			break;
    			
    		case "sqrt":
    			result = Math.sqrt(value);
    			break;
    		
    		default:
    			throw new IllegalArgumentException("\"" + this.name + "\" ist ein ungueltiges Argument!");    		
    	}
		return result;

    }
}
