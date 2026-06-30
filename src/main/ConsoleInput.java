package main;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.InputMismatchException;

public class ConsoleInput {
	private Scanner keyboard;

	public ConsoleInput(Scanner keyboard) {
		this.keyboard = keyboard;
	}

	public void cleanInput() {
		keyboard.nextLine();
	}

	// Byte
	public byte readByte() {
		byte byteWritten = 0;
		boolean valid = false;
		do {
			try {
				byteWritten = keyboard.nextByte();
				valid = true;
			} catch (InputMismatchException e) {
				System.out.println("Error, ingrese un valor válido para byte:");
				cleanInput();
			}
		} while (!valid);

		return byteWritten;
	}

	public byte readByteLessThan(byte upperBound) {
		byte input;

		do {
			input = readByte();
			if (input >= upperBound) {
				System.out.println("Error, el valor debe ser menor que " + upperBound);
			}
		} while (input >= upperBound);

		return input;
	}

	public byte readByteLessOrEqualThan(byte upperBound) {
		byte input;

		do {
			input = readByte();
			if (input > upperBound) {
				System.out.println("Error, el valor debe ser menor o igual que " + upperBound);
			}
		} while (input > upperBound);

		return input;
	}

	public byte readByteGreaterThan(byte lowerBound) {
		byte input;

		do {
			input = readByte();
			if (input <= lowerBound) {
				System.out.println("Error, el valor debe ser mayor que " + lowerBound);
			}
		} while (input <= lowerBound);

		return input;
	}

	public byte readByteGreaterOrEqualThan(byte lowerBound) {
		byte input;

		do {
			input = readByte();
			if (input < lowerBound) {
				System.out.println("Error, el valor debe ser mayor o igual que " + lowerBound);
			}
		} while (input < lowerBound);

		return input;
	}

	public byte readByteInRange(byte lowerBound, byte upperBound) {
		byte input;

		do {
			input = readByte();
			if (input < lowerBound || input > upperBound) {
				System.out.println("Error, el valor debe estar en el rango [" + lowerBound + ", " + upperBound + "]");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// Short
	public short readShort() {
		short shortWritten = 0;
		boolean valid = false;

		do {
			try {
				shortWritten = keyboard.nextShort();
				valid = true;
			} catch (InputMismatchException e) {
				System.out.println("Error, ingrese un valor válido para short:");
				cleanInput();
			}
		} while (!valid);

		return shortWritten;
	}

	public short readShortLessThan(short upperBound) {
		short input;

		do {
			input = readShort();
			if (input >= upperBound) {
				System.out.println("El valor debe ser menor que " + upperBound + ".");
			}
		} while (input >= upperBound);

		return input;
	}

	public short readShortLessOrEqualThan(short upperBound) {
		short input;

		do {
			input = readShort();
			if (input > upperBound) {
				System.out.println("El valor debe ser menor o igual a " + upperBound + ".");
			}
		} while (input > upperBound);

		return input;
	}

	public short readShortGreaterThan(short lowerBound) {
		short input;

		do {
			input = readShort();
			if (input <= lowerBound) {
				System.out.println("El valor debe ser mayor que " + lowerBound + ".");
			}
		} while (input <= lowerBound);

		return input;
	}

	public short readShortGreaterOrEqualThan(short lowerBound) {
		short input;

		do {
			input = readShort();
			if (input < lowerBound) {
				System.out.println("El valor debe ser mayor o igual a " + lowerBound + ".");
			}
		} while (input < lowerBound);

		return input;
	}

	public short readShortInRange(short lowerBound, short upperBound) {
		short input;

		do {
			input = readShort();
			if (input < lowerBound || input > upperBound) {
				System.out
						.println("El valor debe estar entre " + lowerBound + " y " + upperBound + ", ambos incluidos.");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// Int
	public int readInt() {
		int intWritten = 0;
		boolean valid = false;
		do {
			try {
				intWritten = keyboard.nextInt();
				valid = true;
			} catch (InputMismatchException e) {
				System.out.println("Error, ingrese un valor válido para entero:");
				cleanInput();
			}
		} while (!valid);

		return intWritten;
	}

	public int readIntLessThan(int upperBound) {
		int input;

		do {
			input = readInt();
			if (input >= upperBound) {
				System.out.println("El valor debe ser menor que " + upperBound + ".");
			}
		} while (input >= upperBound);

		return input;
	}

	public int readIntLessOrEqualThan(int upperBound) {
		int input;

		do {
			input = readInt();
			if (input > upperBound) {
				System.out.println("El valor debe ser menor o igual a " + upperBound + ".");
			}
		} while (input > upperBound);

		return input;
	}

	public int readIntGreaterThan(int lowerBound) {
		int input;

		do {
			input = readInt();
			if (input <= lowerBound) {
				System.out.println("El valor debe ser mayor que " + lowerBound + ".");
			}
		} while (input <= lowerBound);

		return input;
	}

	public int readIntGreaterOrEqualThan(int lowerBound) {
		int input;

		do {
			input = readInt();
			if (input < lowerBound) {
				System.out.println("El valor debe ser mayor o igual a " + lowerBound + ".");
			}
		} while (input < lowerBound);

		return input;
	}

	public int readIntInRange(int lowerBound, int upperBound) {
		int input;

		do {
			input = readInt();
			if (input < lowerBound || input > upperBound) {
				System.out
						.println("El valor debe estar entre " + lowerBound + " y " + upperBound + ", ambos incluidos.");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// Long
	public long readLong() {
		long longWritten = 0;
		boolean valid = false;

		do {
			try {
				longWritten = keyboard.nextLong();
				valid = true;
			} catch (InputMismatchException e) {
				System.out.println("Error, ingrese un valor válido para número entero largo:");
				cleanInput();
			}
		} while (!valid);

		return longWritten;
	}

	public long readLongLessThan(long upperBound) {
		long input;

		do {
			input = readLong();
			if (input >= upperBound) {
				System.out.println("El valor debe ser menor que " + upperBound + ".");
			}
		} while (input >= upperBound);

		return input;
	}

	public long readLongLessOrEqualThan(long upperBound) {
		long input;

		do {
			input = readLong();
			if (input > upperBound) {
				System.out.println("El valor debe ser menor o igual a " + upperBound + ".");
			}
		} while (input > upperBound);

		return input;
	}

	public long readLongGreaterThan(long lowerBound) {
		long input;

		do {
			input = readLong();
			if (input <= lowerBound) {
				System.out.println("El valor debe ser mayor que " + lowerBound + ".");
			}
		} while (input <= lowerBound);

		return input;
	}

	public long readLongGreaterOrEqualThan(long lowerBound) {
		long input;

		do {
			input = readLong();
			if (input < lowerBound) {
				System.out.println("El valor debe ser mayor o igual a " + lowerBound + ".");
			}
		} while (input < lowerBound);

		return input;
	}

	public long readLongInRange(long lowerBound, long upperBound) {
		long input;

		do {
			input = readLong();
			if (input < lowerBound || input > upperBound) {
				System.out
						.println("El valor debe estar entre " + lowerBound + " y " + upperBound + ", ambos incluidos.");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// Float
	public float readFloat() {
		float floatWritten = 0;
		boolean valid = false;

		do {
			try {
				floatWritten = Float.parseFloat(keyboard.nextLine()); // Lee directamente y convierte.
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Error, ingrese un valor válido para número decimal:");
			}
		} while (!valid);

		return floatWritten;
	}

	public float readFloatLessThan(float upperBound) {
		float input;

		do {
			input = readFloat();
			if (input >= upperBound) {
				System.out.println("El valor debe ser menor que " + upperBound + ".");
			}
		} while (input >= upperBound);

		return input;
	}

	public float readFloatLessOrEqualThan(float upperBound) {
		float input;

		do {
			input = readFloat();
			if (input > upperBound) {
				System.out.println("El valor debe ser menor o igual a " + upperBound + ".");
			}
		} while (input > upperBound);

		return input;
	}

	public float readFloatGreaterThan(float lowerBound) {
		float input;

		do {
			input = readFloat();
			if (input <= lowerBound) {
				System.out.println("El valor debe ser mayor que " + lowerBound + ".");
			}
		} while (input <= lowerBound);

		return input;
	}

	public float readFloatGreaterOrEqualThan(float lowerBound) {
		float input;

		do {
			input = readFloat();
			if (input < lowerBound) {
				System.out.println("El valor debe ser mayor o igual a " + lowerBound + ".");
			}
		} while (input < lowerBound);

		return input;
	}

	public float readFloatInRange(float lowerBound, float upperBound) {
		float input;

		do {
			input = readFloat();
			if (input < lowerBound || input > upperBound) {
				System.out
						.println("El valor debe estar entre " + lowerBound + " y " + upperBound + ", ambos incluidos.");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// Double
	public double readDouble() {
		double doubleWritten = 0;
		boolean valid = false;

		do {
			try {
				doubleWritten = Double.parseDouble(keyboard.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Error, ingrese un valor válido para número decimal:");
			}
		} while (!valid);

		return doubleWritten;
	}

	public double readDoubleLessThan(double upperBound) {
		double input;

		do {
			input = readDouble();
			if (input >= upperBound) {
				System.out.println("El valor debe ser menor que " + upperBound + ".");
			}
		} while (input >= upperBound);

		return input;
	}

	public double readDoubleLessOrEqualThan(double upperBound) {
		double input;

		do {
			input = readDouble();
			if (input > upperBound) {
				System.out.println("El valor debe ser menor o igual a " + upperBound + ".");
			}
		} while (input > upperBound);

		return input;
	}

	public double readDoubleGreaterThan(double lowerBound) {
		double input;

		do {
			input = readDouble();
			if (input <= lowerBound) {
				System.out.println("El valor debe ser mayor que " + lowerBound + ".");
			}
		} while (input <= lowerBound);

		return input;
	}

	public double readDoubleGreaterOrEqualThan(double lowerBound) {
		double input;

		do {
			input = readDouble();
			if (input < lowerBound) {
				System.out.println("El valor debe ser mayor o igual a " + lowerBound + ".");
			}
		} while (input < lowerBound);

		return input;
	}

	public double readDoubleInRange(double lowerBound, double upperBound) {
		double input;

		do {
			input = readDouble();
			if (input < lowerBound || input > upperBound) {
				System.out
						.println("El valor debe estar entre " + lowerBound + " y " + upperBound + ", ambos incluidos.");
			}
		} while (input < lowerBound || input > upperBound);

		return input;
	}

	// readChar
	public char readChar() {
		String stringWritten;
		char charWritten;

		do {
			stringWritten = keyboard.nextLine();
			if (stringWritten.length() > 1) {
				System.out.println("Error, solo se puede introducir un caracter, introduzcalo de nuevo: ");
			} else if (stringWritten.length() < 1) {
				System.out.println("Error, ha introducido una cadena vacia, introduzcala de nuevo: ");
			}
		} while (stringWritten.length() != 1);
		charWritten = stringWritten.charAt(0);

		return charWritten;
	}

	public char readChar(String validCharacters) {
		String stringWritten;
		char charWritten = 0;

		do {
			stringWritten = keyboard.nextLine();
			if (stringWritten.length() != 1) {
				System.out.println("Error, solo se puede introducir un caracter. Inténtelo de nuevo: ");
			} else {
				charWritten = stringWritten.charAt(0);
				if (!Pattern.matches(validCharacters, String.valueOf(charWritten))) {
					System.out.println(
							"Error, el carácter introducido no es válido. Introduzca uno de los caracteres permitidos: ");
					charWritten = 0;
				}
			}
		} while (charWritten == 0);

		return charWritten;
	}

	public char readVowel() {
		return readChar("[aeiouAEIOUÁÉÍÓÚáéíóú]");
	}

	public char readDigit() {
		return readChar("[0-9]");
	}

	public char readLowerCase() {
		return readChar("[a-zñáéíóú]");
	}

	public char readUpperCase() {
		return readChar("[A-ZÑÁÉÍÓÚ]");
	}

	public String readString() {
		String stringWritten;

		stringWritten = keyboard.nextLine();
		return stringWritten;
	}

	public String readString(int maxLength) {
		String stringWritten;
		boolean valid;

		do {
			stringWritten = readString();
			valid = stringWritten.length() <= maxLength;
			if (!valid) {
				System.out.println("Error, la longitud de la cadena es mayor");
			}
		} while (!valid);

		return stringWritten;
	}

	// Boolean
	public boolean readBooleanUsingChar(char affirmativeValue) {
		char charWritten;
		boolean valid;

		do {
			charWritten = readChar();
			valid = (Character.toUpperCase(charWritten) == Character.toUpperCase(affirmativeValue));
			if (!valid) {
				System.out.println("Error, el carácter no coincide con el valor afirmativo. Introduzca de nuevo:");
			}
		} while (!valid);

		return valid;
	}

	public boolean readBooleanUsingChar() {
		return readBooleanUsingChar('s');
	}

	public boolean readBooleanUsingChar(char affirmativeValue, char negativeValue) {
		char charWritten;
		boolean valid;

		do {
			charWritten = readChar();
			charWritten = Character.toUpperCase(charWritten);
			valid = (charWritten == Character.toUpperCase(affirmativeValue))
					|| (charWritten == Character.toUpperCase(negativeValue));
			if (!valid) {
				System.out.println("Error, el carácter no coincide con ningún valor esperado. Introduzca de nuevo:");
			}
		} while (!valid);

		return charWritten == Character.toUpperCase(affirmativeValue);
	}

	public boolean readBooleanUsingInt(int affirmativeValue) {
		int intWritten;
		boolean valid;

		do {
			intWritten = readInt();
			valid = (intWritten == affirmativeValue);
			if (!valid) {
				System.out.println("Error, el entero no coincide con el valor afirmativo. Introduzca de nuevo:");
			}
		} while (!valid);

		return valid;
	}

	public boolean readBooleanUsingInt() {
		return readBooleanUsingInt(1);
	}

	public boolean readBooleanUsingInt(int affirmativeValue, int negativeValue) {
		int intWritten;
		boolean valid;

		do {
			intWritten = readInt();
			valid = (intWritten == affirmativeValue) || (intWritten == negativeValue);
			if (!valid) {
				System.out.println("Error, el entero no coincide con ningún valor esperado. Introduzca de nuevo:");
			}
		} while (!valid);

		return intWritten == affirmativeValue;
	}
}
