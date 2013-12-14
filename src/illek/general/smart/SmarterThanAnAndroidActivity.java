package illek.general.smart;

import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.app.ListActivity;
import android.widget.AdapterView;


public class SmarterThanAnAndroidActivity extends Activity {
    private ListView myListView;
    private TextView myTextView;
    private String evaluationDisplay;
    private static final String TAG = "iSpeech Will Quiz You";
    SpeechSynthesis synthesis;
    SmarterThanApplication sta;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sta = new SmarterThanApplication(); 
        //get references to UI widgets
        myListView = (ListView)findViewById(R.id.myListView);
        myTextView = (TextView)findViewById(R.id.myTextView);
        
        prepareTTSEngine();
        
        
        // Create some challenges
        Challenge cha1 = new Challenge( "Who is Cersi in relation to Tyrion?", new String[] {"sister","cousin","secret incestious lover"}, 0);
        Challenge cha2 = new Challenge( "What is the name of the imp?", new String[] {"Bastard","Tyrion","Bloody Bastard"}, 1);
        Challenge cha3 = new Challenge( "What mythical animal did the house of Targarions boast?", new String[] {"Ligars","Unicorns","Dragons"}, 2);
        
        Challenge[] Round = new Challenge[]{cha1,cha2,cha3};
        sta.setRound(Round);
        try {
			takeChallenge(sta.getActiveChallenge());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
        myListView.setOnItemClickListener(new  android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                 
            	//((TextView) view).getText()
            	
            	//See if they were right
            	if (sta.getActiveChallenge().answer == position)
					try {
						correct();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else wrong();
            	
            	talk(evaluationDisplay);
            	
            	// When clicked, show a toast with the Evaluation Text
                  Toast.makeText(getApplicationContext(), evaluationDisplay ,
                      Toast.LENGTH_SHORT).show();
                }
        });
       
    }
    ArrayAdapter<String> aa;
    private void takeChallenge(Challenge cha) throws InterruptedException
    {
    	myTextView.setText(cha.Question);
    	aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cha.Choices);
     	myListView.setAdapter(aa);
     	talk(cha.getTalking());

    }
    private void correct() throws InterruptedException
    {
    	evaluationDisplay = "Correct";
    	if (sta.winner())
    	{
    		 takeChallenge(sta.getActiveChallenge());
    	}
    	else {
            Toast.makeText(getApplicationContext(), "You won everything" ,
              Toast.LENGTH_SHORT).show();
    	}
    }
    private void wrong()
    {
    	evaluationDisplay = "Wrong!";
    }
	private void prepareTTSEngine() {

		try {
			synthesis = SpeechSynthesis.getInstance(this);
			synthesis.setSpeechSynthesisEvent(new SpeechSynthesisEvent() {

				public void onPlaySuccessful() {
					Log.i(TAG, "onPlaySuccessful");
				}

				public void onPlayStopped() {
					Log.i(TAG, "onPlayStopped");
				}

				public void onPlayFailed(Exception e) {
					Log.e(TAG, "onPlayFailed");
					e.printStackTrace();
				}

				public void onPlayStart() {
					Log.i(TAG, "onPlayStart");
				}

				@Override
				public void onPlayCanceled() {
					Log.i(TAG, "onPlayCanceled");
				}
				
				
			});

			//synthesis.setVoiceType("usenglishfemale1"); // All the values available to you can be found in the developer portal under your account

		} catch (InvalidApiKeyException e) {
			Log.e(TAG, "Invalid API key\n" + e.getStackTrace());
			Toast.makeText(this.getApplicationContext(), "ERROR: Invalid API key", Toast.LENGTH_LONG).show();
		}

	}
	private void talk(String sayThis)
	{
		try {
			synthesis.speak(sayThis);

		} catch (BusyException e) {
			Log.e(TAG, "SDK is busy");
			e.printStackTrace();
			Toast.makeText(this.getApplicationContext(), "ERROR: SDK is busy", Toast.LENGTH_LONG).show();
		} catch (NoNetworkException e) {
			Log.e(TAG, "Network is not available\n" + e.getStackTrace());
			Toast.makeText(this.getApplicationContext(), "ERROR: Network is not available", Toast.LENGTH_LONG).show();
		}
	}
	
    
}