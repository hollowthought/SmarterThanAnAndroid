package illek.general.smart;

import android.app.Application;
import android.content.res.Configuration;

public class SmarterThanApplication extends Application {

		private static SmarterThanApplication singleton;
		
		//Returns the application instance
		public static SmarterThanApplication getInstance() {
			return singleton;
		}
		private  Challenge activeChallenge;
		private Challenge[] Round;
		private int index = 0;
		
		@Override
		public final void onCreate() {
			super.onCreate();
			singleton = this;	
		}
		@Override
		public final void onTerminate(){
			super.onTerminate();
		}
		public void setRound(Challenge[] round)
		{
			Round = round;
		}
		public void setActiveChallenge(Challenge chal)
		{
			activeChallenge = chal;
		}
		public  Challenge getActiveChallenge()
		{
			return Round[index];
		}
		public boolean winner()
		{
			if (Round.length > index+1) 
			{
				index++;
				return true;
			}
			return false;
			
		}
	}