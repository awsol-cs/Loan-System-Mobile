package com.creditsaison.loansystem.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentResidenceInformationBinding;
import com.creditsaison.loansystem.viewmodel.ResidenceInformationViewModel;

public class ResidenceInformationFragment extends Fragment {

    SharedPreferences sharedpreferences;
    EditText street, address_line_1, address_line_2, address_line_3, city, postal_code;
    Spinner state_province, country, address_type, residence_ownership;
    Button btn_next;

    private FragmentResidenceInformationBinding binding;
    private ResidenceInformationViewModel viewModel;


    public ResidenceInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ResidenceInformationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResidenceInformationBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);


        btn_next = (Button) binding.getRoot().findViewById(R.id.btn_next);

        // bind elements
        address_type = (Spinner) binding.getRoot().findViewById(R.id.sp_address_type);
        street = (EditText) binding.getRoot().findViewById(R.id.et_street);
        address_line_1 = (EditText) binding.getRoot().findViewById(R.id.et_address_line_1);
        address_line_2 = (EditText) binding.getRoot().findViewById(R.id.et_address_line_2);
        address_line_3 = (EditText) binding.getRoot().findViewById(R.id.et_address_line_3);
        city = (EditText) binding.getRoot().findViewById(R.id.et_city);
        country = (Spinner) binding.getRoot().findViewById(R.id.sp_country);
        state_province = (Spinner) binding.getRoot().findViewById(R.id.sp_state);
        postal_code = (EditText) binding.getRoot().findViewById(R.id.et_postal_code);
        residence_ownership = (Spinner) binding.getRoot().findViewById(R.id.sp_residence_ownership);

        // start of click event
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                // get inputs
                String str_addressType = address_type.getSelectedItem().toString();
                String str_street = street.getText().toString();
                String str_addLine1 = address_line_1.getText().toString();
                String str_addLine2 = address_line_2.getText().toString();
                String str_addLine3 = address_line_3.getText().toString();
                String str_city = city.getText().toString();
                String str_country = country.getSelectedItem().toString();
                String str_stateProvice = state_province.getSelectedItem().toString();
                String str_postalCode = postal_code.getText().toString();
                String str_residenceOwner = residence_ownership.getSelectedItem().toString();

                sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                String restoredText = sharedpreferences.getString("createWhat", " ");

                if (restoredText == "client") {
                    editor.putString("clientAddressType", str_addressType);
                    editor.putString("clientStreet", str_street);
                    editor.putString("clientAddressLine1", str_addLine1);
                    editor.putString("clientAddressLine2", str_addLine2);
                    editor.putString("clientAddressLine3", str_addLine3);
                    editor.putString("clientCity", str_city);
                    editor.putString("clientCountry", str_country);
                    editor.putString("clientStateProvince", str_stateProvice);
                    editor.putString("clientPostalCode", str_postalCode);
                    editor.putString("clientResidenceOwner", str_residenceOwner);
                } else {
                    editor.putString("coMakerAddressType", str_addressType);
                    editor.putString("coMakerStreet", str_street);
                    editor.putString("coMakerAddressLine1", str_addLine1);
                    editor.putString("coMakerAddressLine2", str_addLine2);
                    editor.putString("coMakerAddressLine3", str_addLine3);
                    editor.putString("coMakerCity", str_city);
                    editor.putString("coMakerCountry", str_country);
                    editor.putString("coMakerStateProvince", str_stateProvice);
                    editor.putString("coMakerPostalCode", str_postalCode);
                    editor.putString("coMakerResidenceOwner", str_residenceOwner);
                }

                editor.commit();


                // turn to next page
                Navigation.findNavController(v).navigate(R.id.action_residenceInformationFragment_to_employmentInformationFragment);
            }
        });
        // end of click event

        return binding.getRoot();
    }

    public void storeData() {
        Log.v("log_tag", "ResidenceInformation storing data");

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("currentSign", "termsCondition");
        editor.commit();

        String restoredText = sharedpreferences.getString("termsConditionImg", " ");

        street = (EditText) binding.getRoot().findViewById(R.id.et_street);

        String str_street = street.getText().toString();



        Log.v("log_tag", str_street);


    }

}
