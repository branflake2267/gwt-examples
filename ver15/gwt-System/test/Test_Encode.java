

public class Test_Encode {

	public static void main(String[] args) {
		String s = "~!@#$%^&*(){g}[g]=:/,;?+\'\"\\abcdef1234567890 ";
		//String s = "[";
		
		String encoded = urlencode(s);
		
		System.out.println("escaped: " + encoded);
		
	}
	
	private static String urlencode(String s) {
		
		String r = "";
		for (int i=0; i < s.length(); i++) {
			r += encodeChar(s.charAt(i));
		}
		
		return r;
	}
	
	private static String encodeChar(char c) {
		String cS = Character.toString(c);
		
		String s = "";
		if (cS.matches("[a-zA-Z0-9_.\\-~&=]") == false) {
			s = "%" + Byte.toString((byte) c);
		} else {
			s = cS;
		}
		
		return s;
	}
	
}
