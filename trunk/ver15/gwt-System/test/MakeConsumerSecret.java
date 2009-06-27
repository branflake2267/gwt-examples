import com.gawkat.gwt.system.client.oauth.Sha1;


public class MakeConsumerSecret {

	public static void main(String[] args) {
		
		String data = "password";
		
		Sha1 sha = new Sha1();
		String hash = sha.hex_sha1(data);
		
		System.out.println("hash: " + hash);
	}
}
