package com.creditsaison.loansystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;


public class LoanAgreementActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedpreferences;

    Button signatureButton, agreeButton, disagreeButton;
    ImageView signImage;
    CheckBox checkBox;

    public LoanAgreementActivity() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("log_tag", "onCreate");


        setContentView(R.layout.activity_loan_agreement);
        signImage = (ImageView) findViewById(R.id.imageView1);

        signatureButton = (Button) findViewById(R.id.getSign);
        signatureButton.setOnClickListener(this);
        agreeButton = (Button) findViewById(R.id.btn_agree);
        agreeButton.setOnClickListener(this);
        disagreeButton = (Button) findViewById(R.id.btn_disagree);
        disagreeButton.setOnClickListener(this);


        Log.v("log_tag", "kuha image");

        //to get imagepath from SignatureActivity and set it on ImageView


//        String image_path = getActivity().getIntent().getStringExtra("imagePath");//getIntent is an activity class
        //String image_path = args.getString("imagePath", "");//String text




//        viewModel = ViewModelProviders.of(this).get(TermsConditionsViewModel.class);
    }



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        Log.v("log_tag", "onCreateView andito ba?");
//
////        binding = FragmentTermsConditionsBinding.inflate(inflater, container, false);
////        binding.setViewModel(viewModel);
//
//        Log.v("log_tag", "onCreateView");
//
////        return binding.getRoot();
//
//        return inflater.inflate(R.layout.fragment_terms_conditions, container, false);
//    }


    @Override
    public void onResume(){
        super.onResume();
        Log.v("log_tag", "uyNagResume");

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();


        editor.putString("currentSign", "loanAgreement");
        editor.commit();


        String restoredText = sharedpreferences.getString("loanAgreementImg", " ");

        String image_path = restoredText;
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
        signImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.getSign:
                // do your code
                Intent i = new Intent(LoanAgreementActivity.this, SignatureActivity.class);
                startActivity(i);

                break;

            case R.id.btn_agree:
                // do your code
                Intent i2 = new Intent(LoanAgreementActivity.this, PromissoryNoteActivity.class);
                startActivity(i2);

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
