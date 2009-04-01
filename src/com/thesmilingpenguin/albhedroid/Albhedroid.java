package com.thesmilingpenguin.albhedroid;

import com.thesmilingpenguin.albhedroid.Cipherer.Mode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Albhedroid extends Activity {
    
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Cipherer.setCipherMode(Cipherer.Mode.ENGLISH_TO_ALBHED);
      
    final RadioGroup cipherModes = 
      (RadioGroup) findViewById(R.id.cipher_modes);
    final Button cipherButton =
      (Button) findViewById(R.id.cipher_button);
    cipherModes.setOnCheckedChangeListener(switchCipherMode);
    cipherButton.setOnClickListener(doCipher);
  }
  
  private OnCheckedChangeListener switchCipherMode = 
    new OnCheckedChangeListener() {
    public void onCheckedChanged(RadioGroup rg, int checkedId) {
      
      switch(checkedId) {
      case R.id.english_to_albhed:
        Cipherer.setCipherMode(Cipherer.Mode.ENGLISH_TO_ALBHED);
        break;
      case R.id.albhed_to_english:
        Cipherer.setCipherMode(Cipherer.Mode.ALBHED_TO_ENGLISH);
      }
      
    }
  };
    
  private OnClickListener doCipher = new OnClickListener() {
    public void onClick(View v) {
      final EditText inputString = 
        (EditText) findViewById(R.id.input_string);
      final TextView outputString = 
        (TextView) findViewById(R.id.output_string);
      
      try {
        String result = Cipherer.cipher(inputString.getText().toString());
        outputString.setText(result);
      } catch (CipherException e) {
        outputString.setText(e.getMessage());
      }
    }
  };
}