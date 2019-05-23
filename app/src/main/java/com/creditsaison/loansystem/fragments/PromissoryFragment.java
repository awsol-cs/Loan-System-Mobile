package com.creditsaison.loansystem.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentPromiNoteBinding;
import com.creditsaison.loansystem.viewmodel.PromissoryViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PromissoryFragment extends Fragment implements View.OnClickListener {


    SharedPreferences sp;
    ImageView signImage;
    Button btn_agree;

    private FragmentPromiNoteBinding binding;
    private PromissoryViewModel viewModel;

    public PromissoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PromissoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPromiNoteBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        signImage = (ImageView) binding.getRoot().findViewById(R.id.signature);


        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();

        btn_agree = (Button) getActivity().findViewById(R.id.btn_agree3);
        btn_agree.setOnClickListener(this);

        sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currentSign", "promissoryNote");
        editor.commit();

        String restoredText = sp.getString("promNoteImg", " ");

        if (restoredText != "default"){
            String image_path = restoredText;
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            signImage.setImageBitmap(bitmap);
            btn_agree.setEnabled(true);
        } else {
            signImage.setImageResource(R.drawable.signature_logo);
            btn_agree.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {
        sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String restoredText = sp.getString("termsConditionImg", " ");

        JSONObject client = new JSONObject();
        JSONObject kyc = new JSONObject();
        JSONObject loan = new JSONObject();
        JSONObject coMaker = new JSONObject();
        JSONObject coMakerInfo = new JSONObject();
        JSONObject coMakerKyc = new JSONObject();
        JSONObject image = new JSONObject();
        JSONObject documents = new JSONObject();
        JSONObject signatures = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            client.put("id", sp.getString("clientfirstName", " "));
            client.put("id", sp.getString("clientmiddleName", " "));
            client.put("id", sp.getString("clientlastName", " "));
            client.put("id", sp.getString("clientMobileNo", " "));
            client.put("id", sp.getString("clientNationality", " "));
            client.put("id", sp.getString("clientbirthPlace", " "));
            client.put("id", sp.getString("clientDependents", " "));
            client.put("id", sp.getString("clientGovIdNo", " "));
            client.put("id", sp.getString("clientDocSource", " "));
            client.put("id", sp.getString("clientGender", " "));
            client.put("id", sp.getString("clientMaritalStatus", " "));
            client.put("id", sp.getString("clientEducStat", " "));
            client.put("id", sp.getString("clientGovSpinner", " "));
            client.put("id", sp.getString("clientDocSpinner", " "));

            // client residence
            kyc.put("id", sp.getString("clientAddressType", " "));
            kyc.put("id", sp.getString("clientStreet", " "));
            kyc.put("id", sp.getString("clientAddressLine1", " "));
            kyc.put("id", sp.getString("clientAddressLine2", " "));
            kyc.put("id", sp.getString("clientAddressLine3", " "));
            kyc.put("id", sp.getString("clientCity", " "));
            kyc.put("id", sp.getString("clientStateProvince", " "));
            kyc.put("id", sp.getString("clientPostalCode", " "));
            kyc.put("id", sp.getString("clientResidenceOwner", " "));
            // client employment
            kyc.put("id", sp.getString("clientEmploymentOthers", " "));
            kyc.put("id", sp.getString("clientOperationYears", " "));
            kyc.put("id", sp.getString("clientPresentEmployer", " "));
            kyc.put("id", sp.getString("clientBusinessNature", " "));
            kyc.put("id", sp.getString("clientOfficeAddress", " "));
            kyc.put("id", sp.getString("clientOfficePhone", " "));
            kyc.put("id", sp.getString("clientOfficeMobile", " "));
            kyc.put("id", sp.getString("clientLocalNo", " "));
            kyc.put("id", sp.getString("clientFaxNo", " "));
            kyc.put("id", sp.getString("clientEmailAddress", " "));
            kyc.put("id", sp.getString("clientPosition", " "));
            kyc.put("id", sp.getString("clientGrossIncome", " "));
            kyc.put("id", sp.getString("clientOtherIncome", " "));
            kyc.put("id", sp.getString("clientPreviousEmployer", " "));
            kyc.put("id", sp.getString("clientPreviousEmployerOffice", " "));
            kyc.put("id", sp.getString("clientPresentEmployerYears", " "));
            kyc.put("id", sp.getString("clientPreviousEmployerYears", " "));
            kyc.put("id", sp.getString("clientEmploymentType", " "));
            kyc.put("id", sp.getString("clientSelfEmployed", " "));
            // client reference
            kyc.put("id", sp.getString("clientRefName", " "));
            kyc.put("id", sp.getString("clientRefRelationship", " "));
            kyc.put("id", sp.getString("clientRefEmployer", " "));
            kyc.put("id", sp.getString("clientRefContactNo", " "));
            kyc.put("id", sp.getString("clientRefMobile", " "));
            kyc.put("id", sp.getString("clientRelatedOfficerName", " "));
            kyc.put("id", sp.getString("clientOfficerContactNo", " "));
            kyc.put("id", sp.getString("clientRelationshipToStaff", " "));
            kyc.put("id", sp.getString("clientIsRelated", " "));

            loan.put("id", sp.getString("LoanProduct", " "));
            loan.put("id", sp.getString("LoanPurpose", " "));
            loan.put("id", sp.getString("LoanSubmissionDate", " "));
            loan.put("id", sp.getString("LoanDisbursement", " "));
            loan.put("id", sp.getString("LoanPrincipal", " "));

            coMakerInfo.put("id", sp.getString("coMakerfirstName", " "));
            coMakerInfo.put("id", sp.getString("coMakermiddleName", " "));
            coMakerInfo.put("id", sp.getString("coMakerlastName", " "));
            coMakerInfo.put("id", sp.getString("coMakerMobileNo", " "));
            coMakerInfo.put("id", sp.getString("coMakerNationality", " "));
            coMakerInfo.put("id", sp.getString("coMakerbirthPlace", " "));
            coMakerInfo.put("id", sp.getString("coMakerDependents", " "));
            coMakerInfo.put("id", sp.getString("coMakerGovIdNo", " "));
            coMakerInfo.put("id", sp.getString("coMakerDocSource", " "));
            coMakerInfo.put("id", sp.getString("coMakerGender", " "));
            coMakerInfo.put("id", sp.getString("coMakerMaritalStatus", " "));
            coMakerInfo.put("id", sp.getString("coMakerEducStat", " "));
            coMakerInfo.put("id", sp.getString("coMakerGovSpinner", " "));
            coMakerInfo.put("id", sp.getString("coMakerDocSpinner", " "));





            signatures.put("id", sp.getString("termsConditionImg", " "));
            signatures.put("id", sp.getString("loanAgreementImg", " "));
            signatures.put("id", sp.getString("promNoteImg", " "));




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
