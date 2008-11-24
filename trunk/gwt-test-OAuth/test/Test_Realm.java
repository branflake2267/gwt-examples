import com.tribling.gwt.test.oauth.client.Global_Domain;


public class Test_Realm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://host.domain.tld:8180/home/#test?test=1&b=2";
		
		String r = Global_Domain.getRealm(url);
		
		System.out.println(r);
	}


}
