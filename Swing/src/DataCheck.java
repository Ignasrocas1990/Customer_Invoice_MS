package src;
	/**
	 * Class used for data checking
	 * @author ignas rocas
	 *
	 */
public class DataCheck {
	public DataCheck() {
	}

	/**
	 * allow only letters
	 * @param letters char letter entered by user
	 * @return boolean,true if its a char
	 */
	public boolean onlyLetters(char letters) {
		if ((letters >= 'a' && letters <= 'z') || (letters >= 'A' && letters <= 'Z')) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * (allows backspace (8) and Shift/CapsLock(65535))
	 * @param special ascii value of character entered 
	 * @return boolean,true if its a backspace,shift,capslock or '.'
	 */
	public boolean specialkeys(int special) {
		if (special == 8 || special == 65535 || special== '.') {
			return true;
		}
		return false;
	}
	/**
	 * allows only number's
	 * @param number - int number entered by a user
	 * @return boolean,true if its a int 0-9
	 */
	public boolean onlyNumbers(int number) {
		if (number >= '0' && number <= '9') {
			return true;
		}
		return false;
	}

}
