import com.tribling.gwt.test.oauth.client.global.Global_String;


public class Test_RemoveBadHTML {
	
	public static void main(String[] args) {
		String html = "test removing bad tags html <sCript c='asdf'> function test </ScriPt> more stuff <EmBed a=\"2\">object</eMbed>";
		
		html = Global_String.removeBadHtmlTags(html);
		
		System.out.println("html: " + html);
	}
}
