package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Calculator {
	private BufferedReader in;
	private double first = 0;
	private double second = 0;
	private double result = 0;
	private String operation = "";
	private static final Map<String, IOperation> OPERATIONS = new HashMap<String, IOperation>();
	static
    {
		OPERATIONS.put("+", ((double x, double y) -> x + y));
		OPERATIONS.put("-", ((double x, double y) -> x - y));
		OPERATIONS.put("/", ((double x, double y) -> x / y));
		OPERATIONS.put("*", ((double x, double y) -> x * y));
    }
	
	public Calculator() {
		in  = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public double launch() {
		System.out.println("Welcome to the calculator!");
		first = verifyInputDouble("first", ""); 
		operation = verifyInputString();
		second = verifyInputDouble("second", operation);
		result = calculate(operation, first, second);
		System.out.println("The result of " + first + operation + second + " = " + result);
		return result;
	}
	
	
	private double calculate(String operation, double x, double y) {
		return OPERATIONS.get(operation).calc(x, y);
	}
	
	private String verifyInputString() {
		boolean correct = true;
		String input = "";
		do {
			System.out.println("Enter required operation (+ - * /):");
			try {
				input = in.readLine();
			} catch (IOException e) {
				System.out.println("Unexpected error has occured");
				e.printStackTrace();
				System.exit(-1);
			}
			if(!OPERATIONS.containsKey(input)) {
				correct = false;
				System.out.println("The operation you entered is not valid, try again.");
			}
			else {
				correct = true;
			}
		} while (!correct);
		return input;
	}
	
	private double verifyInputDouble(String operandDescription, String operation) {
		boolean correct = false;
		boolean negative = false;
		double inputParsed = 0;
		String input = "";
		do{
			System.out.println("Enter " + operandDescription + " operand:");
			try {
				input = in.readLine();
				if(input.substring(0, 1).equals("-")) {
					negative = true;
					input = input.substring(1);
				}
				inputParsed = Double.parseDouble(input);
				correct = true;
				if(inputParsed == 0 && operandDescription.equals("second") && operation.equals("/")) {
					correct = false;
					System.out.println("It is not allowed to divide by zero, enter another divider.");
				}				
				if(negative && inputParsed	!= 0) {
					inputParsed = -inputParsed;
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Please enter " + operandDescription + " of integer/double type.");
			} catch (IOException e) {
				System.out.println("Unexpected error has occured");
				e.printStackTrace();
				System.exit(-1);
			} 
		} while (!correct);		
		return inputParsed;
	}	
}


interface IOperation {
	double calc(double x, double y);
}


	




