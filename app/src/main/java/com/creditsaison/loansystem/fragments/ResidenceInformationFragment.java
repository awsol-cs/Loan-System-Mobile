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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentResidenceInformationBinding;
import com.creditsaison.loansystem.viewmodel.ResidenceInformationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResidenceInformationFragment extends Fragment {

    SharedPreferences sharedpreferences;
    EditText street, address_line_1, address_line_2, address_line_3, city, postal_code;
    Spinner state_province, country, address_type, residence_ownership;
    Button btn_next;

    //arrays for dropdown values and their corresponding ids
    List<String> addressTypeArray, countryArray, stateArray, ownershipArray;
    List<Integer> addressTypeIds, countryIds, stateIds, ownershipIds;

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

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        addressTypeArray = new ArrayList<>();
        countryArray = new ArrayList<>();
        stateArray = new ArrayList<>();
        ownershipArray = new ArrayList<>();
        addressTypeIds = new ArrayList<>();
        countryIds = new ArrayList<>();
        stateIds = new ArrayList<>();
        ownershipIds = new ArrayList<>();

        // this is the part where you assign the saved data in sharedpreference to array
        try {

            JSONArray arr_residence_ownership = new JSONArray(sharedpreferences.getString("arr_residence_ownership", " "));
            JSONArray arr_address_type = new JSONArray(sharedpreferences.getString("arr_address_type", " "));
            JSONArray arr_state = new JSONArray(sharedpreferences.getString("arr_state", " "));
            JSONArray arr_country = new JSONArray(sharedpreferences.getString("arr_country", " "));

            //populate arrays for residence ownership values(String) and ids(Int)
            for(int i = 0; i < arr_residence_ownership.length(); i++) {
                JSONObject jsonObject1 = arr_residence_ownership.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                ownershipArray.add(name);
                ownershipIds.add(id);
            }
            //setting spinner for gender
            ArrayAdapter<String> adapter_ownership = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    ownershipArray
            );
            residence_ownership.setAdapter(adapter_ownership);

            //for address type
            for(int i = 0; i < arr_address_type.length(); i++) {
                JSONObject jsonObject1 = arr_address_type.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                addressTypeArray.add(name);
                addressTypeIds.add(id);
            }
            ArrayAdapter<String> adapter_add_type = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    addressTypeArray
            );
            address_type.setAdapter(adapter_add_type);

            //for state
            for(int i = 0; i < arr_state.length(); i++) {
                JSONObject jsonObject1 = arr_state.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                Log.v("EDUC", name + " = " + id);
                stateArray.add(name);
                stateIds.add(id);
            }
            ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    stateArray
            );
            state_province.setAdapter(adapter_state);

            //for country
            for(int i = 0; i < arr_country.length(); i++) {
                JSONObject jsonObject1 = arr_country.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                Log.v("EDUC", name + " = " + id);
                countryArray.add(name);
                countryIds.add(id);
            }
            ArrayAdapter<String> adapter_country = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    countryArray
            );
            country.setAdapter(adapter_country);

            Log.i("ADDRESSTYPE SIZE", String.valueOf(addressTypeArray.size()));
            Log.i("COUNTRY SIZE", String.valueOf(countryArray.size()));
            Log.i("STATE SIZE", String.valueOf(stateArray.size()));
            Log.i("OWNERSHIP SIZE", String.valueOf(ownershipArray.size()));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // start of click event
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                // get inputs
                String str_street = street.getText().toString();
                String str_addLine1 = address_line_1.getText().toString();
                String str_addLine2 = address_line_2.getText().toString();
                String str_addLine3 = address_line_3.getText().toString();
                String str_city = city.getText().toString();
                String str_postalCode = postal_code.getText().toString();

                //getting selected values from dropdowns and its corresponding ids
                //for address type
                String str_addressType = address_type.getSelectedItem().toString();
                int type_index = addressTypeArray.indexOf(str_addressType);
                int address_type_id = addressTypeIds.get(type_index);

                //for country
                String str_country = country.getSelectedItem().toString();
                int country_index = countryArray.indexOf(str_country);
                int country_id = countryIds.get(country_index);

                //for state
                String str_stateProvice = state_province.getSelectedItem().toString();
                int state_index = stateArray.indexOf(str_stateProvice);
                int state_id = stateIds.get(state_index);

                //for residence ownership
                String str_residenceOwnership = residence_ownership.getSelectedItem().toString();
                int ownership_index = ownershipArray.indexOf(str_residenceOwnership);
                int ownership_id = ownershipIds.get(ownership_index);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                String restoredText = sharedpreferences.getString("createWhat", " ");

                if (restoredText == "client") {
                    editor.putInt("clientAddressType", address_type_id);
                    editor.putString("clientStreet", str_street);
                    editor.putString("clientAddressLine1", str_addLine1);
                    editor.putString("clientAddressLine2", str_addLine2);
                    editor.putString("clientAddressLine3", str_addLine3);
                    editor.putString("clientCity", str_city);
                    editor.putInt("clientCountry", country_id);
                    editor.putInt("clientStateProvince", state_id);
                    editor.putString("clientPostalCode", str_postalCode);
                    editor.putInt("clientResidenceOwner", ownership_id);
                } else {
                    editor.putInt("coMakerAddressType", address_type_id);
                    editor.putString("coMakerStreet", str_street);
                    editor.putString("coMakerAddressLine1", str_addLine1);
                    editor.putString("coMakerAddressLine2", str_addLine2);
                    editor.putString("coMakerAddressLine3", str_addLine3);
                    editor.putString("coMakerCity", str_city);
                    editor.putInt("coMakerCountry", country_id);
                    editor.putInt("coMakerStateProvince", state_id);
                    editor.putString("coMakerPostalCode", str_postalCode);
                    editor.putInt("coMakerResidenceOwner", ownership_id);
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
