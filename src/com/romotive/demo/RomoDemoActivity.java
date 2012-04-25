package com.romotive.demo;

import java.util.Locale;

import com.romotive.library.RomoCommandInterface;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

/**
 * This Activity provides an example of how to use the RomoSDK in your own Android projects.
 * Simply drag and drop the com.romotive.library package and the libs folder into your project.
 * 
 * @author James Knight
 * @version 1.0, 4-15-2012
 * 
 * Copyright (c) 2012 Romotive, Inc. All rights reserved.
 */
public class RomoDemoActivity extends Activity implements TextToSpeech.OnInitListener {
	// Add an instance of RomoCommandInterface to your activity.
	RomoCommandInterface mCommandInterface;
    private TextToSpeech mTts;
	
    @Override
    /** 
     * Called when the activity is first created.
     * Initialize your RomoCommandInterface here.
     */
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Initialize your RomoCommandInterface:
        mCommandInterface = new RomoCommandInterface();
        mTts = new TextToSpeech(this, this);  // TextToSpeech.OnInitListener
    }

    @Override
    /**
     * Called when the activity is destroyed.
     * Be sure to shutdown the RomoCommandInterface here!
     */
    public void onDestroy()
    {
    	super.onDestroy();
    	
    	// Shutdown the RomoCommandInterface:
    	mCommandInterface.shutdown();
    	mCommandInterface = null;
    	
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }

    }
    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // Note that a language may not be available, and the result will indicate this.
            int result = mTts.setLanguage(Locale.US);
            // Try this someday for some interesting results.
            // int result mTts.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Lanuage data is missing or the language is not supported.
            } else {
                // Check the documentation for other possible result codes.
                // For example, the language may be available for the locale,
                // but not for the specified country and variant.

                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
                // Greet the user.
            }
        } else {
            // Initialization failed.
        }
    }
   
    /**
     * Called when one of the directional buttons is pressed.
     * @param view	The Android view which received the onClick event.
     */
    public void buttonPressed(View view)
    {
		
    	// Switch on the view's ID:
    	switch (view.getId())
    	{
    	case R.id.l_f:
    		mCommandInterface.playMotorCommand(0xC0, 0xFF);
    		break;
    		
    	case R.id.f:
			mCommandInterface.playMotorCommand(0xFF, 0xFF);
    		break;
    		
    	case R.id.r_f:
    		mCommandInterface.playMotorCommand(0xFF, 0xC0);
    		break;

    	case R.id.l:
    		mCommandInterface.playMotorCommand(0x00, 0xFF);
    		break;
    		
    	case R.id.s:
    		mCommandInterface.playMotorCommand(0x80, 0x80);
    		AudioManager aM = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    		aM.setMode(AudioManager.MODE_NORMAL);
    		//aM.setBluetoothScoOn(false);
    		//aM.setBluetoothA2dpOn(false);
    		aM.setWiredHeadsetOn(false);
    		aM.setSpeakerphoneOn(true);
    		mTts.speak("Stopped here", TextToSpeech.QUEUE_FLUSH, null);
    		break;
    		
    	case R.id.r:
    		mCommandInterface.playMotorCommand(0xFF, 0x00);
    		break;

    	case R.id.l_b:
    		mCommandInterface.playMotorCommand(0x40, 0x00);
    		break;
    		
    	case R.id.b:
    		mCommandInterface.playMotorCommand(0x00, 0x00);
    		break;
    		
    	case R.id.r_b:
    		mCommandInterface.playMotorCommand(0x00, 0x40);
    		break;
    	}
    }
}