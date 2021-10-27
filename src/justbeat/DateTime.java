package justbeat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	public Date date() throws InterruptedException {
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss.SS");
		
		Date today = new Date ();
		Date tomorrow = new Date ( today.getTime ( ) + (long) ( 1000 * 60 * 60 * 24 ) );
		
		long reqTime = System.currentTimeMillis(); 
		String reqTimeStr = dayTime.format(new Date(reqTime));
		
		long resTime = System.currentTimeMillis(); 
		String resTimeStr = dayTime.format(resTime);
		
		return today;
	}

}