package fr.jlc.polytech.scheduler.core;

/**
 * Capacity is a class which represents a unit in byte. It contains a value (long) and a scale (kilo = 10^3,
 * mega = 10^6, giga = 10^9, tera^12, or nothing for scale = 1).
 */
public class Capacity {
	
	/**
	 * The value in Byte
	 */
	private long value;
	
	/**
	 * The scale. It can be ' ', 'K', 'M', 'G' or 'T'.
	 */
	private char scale;
	
	public Capacity(long value, char scale) {
		setValue(value);
		setScale(scale);
	}
	public Capacity(long value) {
		this(value, ' ');
	}
	
	public long convertIntoTrueValue() {
		long trueValue;
		
		switch (getScale())
		{
			case 'K':
				trueValue = (long) (getValue() * Math.pow(10, 3));
				break;
			case 'M':
				trueValue = (long) (getValue() * Math.pow(10, 6));
				break;
			case 'G':
				trueValue = (long) (getValue() * Math.pow(10, 9));
				break;
			case 'T':
				trueValue = (long) (getValue() * Math.pow(10, 12));
				break;
			default:
				trueValue = getValue();
				break;
		}
		
		return trueValue;
	}

	public static Capacity convertIntoCapacity(long value){

		double nb0 = Math.log10(value);
		Capacity c;
		int x =(int)nb0;
		if(x<6) {
			c = new Capacity(value/1000, 'K');
			return c;
		}
		else if (x>=6 && x<9) {
			c = new Capacity(value/1000000, 'M');
			return c;
		}
		else if (x>=9 && x<12){
			c = new Capacity(value/1000000000, 'G');
			return c;
		}
		else if (x>=12){
			c = new Capacity(value/1000000000000L, 'T');
			return c;
		}

		else {
			c = new Capacity (value);
			return c;
		}
	}
	
	/**
	 * Parse a string to convert into a capacity
	 * @param value Value must be a capacity like "40G", "9", "50T", "400 ", ...
	 * @return Return the instance of the capacity
	 * @throws UnsupportedOperationException Thrown when the string is not valid
	 */
	public static Capacity fromString(String value) {
		UnsupportedOperationException exception = new UnsupportedOperationException("Unsupported format");
		
		boolean parseValue = true;
		StringBuilder s_value = new StringBuilder();
		char scale = ' ';
		
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			
			if (Character.isDigit(c) && parseValue)
				s_value.append(c);
				// If c is ' ' or a scale character...
			else if (isScaleValid(c) && parseValue) {
				parseValue = false;
				scale = c;
			}
			else
				throw exception;
		}
		
		if (s_value.toString().length() == 0)
			throw exception;
		
		long l_value;
		
		try {
			l_value = Long.valueOf(s_value.toString());
		} catch (NumberFormatException ex) {
			throw exception;
		}
		
		return new Capacity(l_value, scale);
	}
	
	public static char[] getAllScales() {
		return new char[] { ' ', 'K', 'M', 'G', 'T' };
	}
	
	public static boolean isScaleValid(char c) {
		for (char scale : getAllScales())
			if (scale == c)
				return true;
		
		return false;
	}
	
	/* GETTERS & SETTERS */
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		if (value <= 0){
			System.out.println(value);
			throw new IllegalArgumentException("Capacity value must be greater than 0");
		}

		this.value = value;
	}
	
	public char getScale() {
		return scale;
	}
	
	public void setScale(char scale) {
		scale = Character.toUpperCase(scale);
		
		if (scale == ' ' ||
			scale == 'K' ||
			scale == 'M' ||
			scale == 'G' ||
			scale == 'T')
			this.scale = scale;
		else
			throw new IllegalArgumentException("Scale can be ' ' (unit), 'K', 'M', 'G' or 'T'");
	}
	
	/* OVERRIDES */

	@Override
	public String toString() {
		return Long.toString(getValue()) + Character.toString(Character.toUpperCase(getScale()));
	}
}
