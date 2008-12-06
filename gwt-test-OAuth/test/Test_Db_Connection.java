import sun.security.action.GetBooleanAction;

import com.tribling.gwt.test.oauth.server.db.Db_Conn;


public class Test_Db_Connection {

	public static void main(String[] args) {
		
		
		Db_Conn db = new Db_Conn();
		
		String sql = "SELECT ApplicationId FROM Application WHERE ApplicationId='1';";
		int i = db.getQueryInt(sql);
		
		System.out.println("appId: " + i);
	}
}
