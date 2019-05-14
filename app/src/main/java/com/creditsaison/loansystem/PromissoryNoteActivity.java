package com.creditsaison.loansystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class PromissoryNoteActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedpreferences;

    Button signatureButton, agreeButton, disagreeButton;
    ImageView signImage;
    CheckBox checkBox;

    public PromissoryNoteActivity() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("log_tag", "onCreate");


        setContentView(R.layout.activity_promissory_note);
        signImage = (ImageView) findViewById(R.id.imageView1);

        signatureButton = (Button) findViewById(R.id.getSign);
        signatureButton.setOnClickListener(this);
        agreeButton = (Button) findViewById(R.id.btn_agree);
        agreeButton.setOnClickListener(this);
        disagreeButton = (Button) findViewById(R.id.btn_disagree);
        disagreeButton.setOnClickListener(this);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        //disable button if checkbox is not checked else enable button
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Log.v("log_tag", "MarkIF");
                    signatureButton.setEnabled(true);
                }
                else{
                    Log.v("log_tag", "MarkELSE");
                    signatureButton.setEnabled(false);
                }
            }
        });


    }





    @Override
    public void onResume(){
        super.onResume();
        Log.v("log_tag", "uyNagResume");

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();


        editor.putString("currentSign", "promNote");
        editor.commit();


        String restoredText = sharedpreferences.getString("promNoteImg", " ");

        String image_path = restoredText;
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
        signImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.getSign:
                // do your code
                if (checkBox.isChecked()){
                    Intent i = new Intent(PromissoryNoteActivity.this, SignatureActivity.class);
                    startActivity(i);
                }
                else {
                    signatureButton.setEnabled(false);
                }

                break;

            case R.id.btn_agree:
                // do your code
                Intent i = new Intent(PromissoryNoteActivity.this, SignatureActivity.class);
                startActivity(i);

                break;

            case R.id.btn_disagree:
                // do your code
                Navigation.findNavController(v).navigate(R.id.action_termsConditionFragment_to_loanAgreementFragment);

                break;

            default:
                break;
        }
    }

}
