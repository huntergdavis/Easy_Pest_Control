package com.hunterdavis.easypestcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class EasyPestControl extends Activity {
	
	AndroidAudioDevice device;
	float currentFrequency;
	Button pauseButton = null;
	int currentDuration = 3;

	   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	device = null;

        
		// listener for frequency button
		OnClickListener frequencyListner = new OnClickListener() {
			public void onClick(View v) {
				EditText freqText = (EditText) findViewById(R.id.freqbonus);
				String frequency = freqText.getText().toString();
				if (frequency.length() > 0) {
					float localFreqValue = Float.valueOf(frequency);
					playFrequency(v.getContext(), localFreqValue);
				}

			}
		};
		
		// listener for frequency button
		OnClickListener dogOneListener = new OnClickListener() {
			public void onClick(View v) {
				playFrequency(v.getContext(), 100000);
			}
		};
		
		// listener for frequency button
		OnClickListener dogTwoListener = new OnClickListener() {
			public void onClick(View v) {
				playFrequency(v.getContext(), 75000);
			}
		};
		
		// listener for frequency button
		OnClickListener dogThreeListener = new OnClickListener() {
			public void onClick(View v) {
				playFrequency(v.getContext(), 66000);
			}
		};
		
		// listener for frequency button
		OnClickListener dogFourListener = new OnClickListener() {
			public void onClick(View v) {
				playFrequency(v.getContext(), 125000);
			}
		};
		
		// listener for puase button
		OnClickListener pauseButtonListener = new OnClickListener() {
			public void onClick(View v) {
				if (device != null) {
					device.stop();
					pauseButton.setEnabled(false);
				}
			}
		};
		
		
		// frequency button
		Button freqButton = (Button) findViewById(R.id.freqbutton);
		freqButton.setOnClickListener(frequencyListner);
		
		// Whistle Button 1-4
		Button dogOneButton = (Button) findViewById(R.id.mosq);
		dogOneButton.setOnClickListener(dogOneListener);
		Button dogTwoButton = (Button) findViewById(R.id.rats);
		dogTwoButton.setOnClickListener(dogTwoListener);
		Button dogThreeButton = (Button) findViewById(R.id.mice);
		dogThreeButton.setOnClickListener(dogThreeListener);
		Button dogFourButton = (Button) findViewById(R.id.roaches);
		dogFourButton.setOnClickListener(dogFourListener);
		
		pauseButton = (Button) findViewById(R.id.pause);
		pauseButton.setOnClickListener(pauseButtonListener);
		pauseButton.setEnabled(false);

		
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
        
    }
    
	public void playFrequency(Context context, float frequency) {


		currentFrequency = frequency;
		pauseButton.setEnabled(true);

		EditText durationText = (EditText) findViewById(R.id.duration);
		int tempDuration = 0;

		try {
			tempDuration = Integer.valueOf(durationText.getText().toString()
					.trim());
		} catch (NumberFormatException e) {
			tempDuration = 3;
		}

		currentDuration = tempDuration;

		new Thread(new Runnable() {
			public void run() {
				// final float frequency2 = 440;
				float increment = (float) (2 * Math.PI) * currentFrequency
						/ 44100; // angular
				// increment
				// for
				// each
				// sample
				float angle = 0;
				device = new AndroidAudioDevice();
				float samples[] = new float[1024];

				for (int j = 0; j < (currentDuration * 40); j++) {
					for (int i = 0; i < samples.length; i++) {
						samples[i] = (float) Math.sin(angle);
						angle += increment;
					}

					device.writeSamples(samples);

				}
				pauseButton.post(new Runnable() {
					public void run() {
						pauseButton.setEnabled(false);
					}
				});
			}
		}).start();

	}
    
    
    

}