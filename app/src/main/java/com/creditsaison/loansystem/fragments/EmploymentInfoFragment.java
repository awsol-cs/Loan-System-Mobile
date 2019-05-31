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
import com.creditsaison.loansystem.databinding.FragmentEmploymentInfoBinding;
import com.creditsaison.loansystem.viewmodel.EmploymentInfoViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmploymentInfoFragment extends Fragment {

    SharedPreferences sharedpreferences;
    EditText employment_others, operation_years, present_employer, business_nature, office_address,
            office_phone, office_mobile, local_number, fax_number, email_add, position,
            gross_income, other_income, previous_employer, previous_employer_office,
            present_employer_years, previous_employer_years;
    Spinner employment_type, self_employed;
    Button btn_next;

    //arrays for dropdown values and their corresponding ids
    List<String> employmentTypeArray, selfEmployedTypeArray;
    List<Integer> employmentTypeIds, selfEmployedTypeIds;

    private FragmentEmploymentInfoBinding binding;
    private EmploymentInfoViewModel viewModel;

    public EmploymentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(EmploymentInfoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEmploymentInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);


        btn_next = (Button) binding.getRoot().findViewById(R.id.btn_next);

        // bind elements
        employment_others = (EditText) binding.getRoot().findViewById(R.id.et_employment_others);
        operation_years = (EditText) binding.getRoot().findViewById(R.id.et_operation_years);
        present_employer = (EditText) binding.getRoot().findViewById(R.id.et_present_employer);
        business_nature = (EditText) binding.getRoot().findViewById(R.id.et_business_nature);
        office_address = (EditText) binding.getRoot().findViewById(R.id.et_office_address);
        office_phone = (EditText) binding.getRoot().findViewById(R.id.et_office_phone);
        office_mobile = (EditText) binding.getRoot().findViewById(R.id.et_office_mobile);
        local_number = (EditText) binding.getRoot().findViewById(R.id.et_local_number);
        fax_number = (EditText) binding.getRoot().findViewById(R.id.et_fax_number);
        email_add = (EditText) binding.getRoot().findViewById(R.id.et_email_add);
        position = (EditText) binding.getRoot().findViewById(R.id.et_position);
        gross_income = (EditText) binding.getRoot().findViewById(R.id.et_gross_income);
        other_income = (EditText) binding.getRoot().findViewById(R.id.et_other_income);
        previous_employer = (EditText) binding.getRoot().findViewById(R.id.et_previous_employer);
        previous_employer_office = (EditText) binding.getRoot().findViewById(R.id.et_previous_employer_office);
        present_employer_years = (EditText) binding.getRoot().findViewById(R.id.et_present_employer_years);
        previous_employer_years = (EditText) binding.getRoot().findViewById(R.id.et_previous_employer_years);
        employment_type = (Spinner) binding.getRoot().findViewById(R.id.sp_employment_type);
        self_employed = (Spinner) binding.getRoot().findViewById(R.id.sp_self_employed);

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        employmentTypeArray = new ArrayList<>();
        selfEmployedTypeArray = new ArrayList<>();
        employmentTypeIds = new ArrayList<>();
        selfEmployedTypeIds = new ArrayList<>();

        // this is the part where you assign the saved data in sharedpreference to array
        try {

            JSONArray arr_employment_type = new JSONArray(sharedpreferences.getString("arr_employment_type", " "));
            JSONArray arr_self_employment = new JSONArray(sharedpreferences.getString("arr_self_employment", " "));

            //populate arrays for employment type values(String) and ids(Int)
            for(int i = 0; i < arr_employment_type.length(); i++) {
                JSONObject jsonObject1 = arr_employment_type.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                employmentTypeArray.add(name);
                employmentTypeIds.add(id);
            }
            //setting spinner for employment type
            ArrayAdapter<String> adapter_employment_type = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    employmentTypeArray
            );
            employment_type.setAdapter(adapter_employment_type);

            //for address type
            for(int i = 0; i < arr_self_employment.length(); i++) {
                JSONObject jsonObject1 = arr_self_employment.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                selfEmployedTypeArray.add(name);
                selfEmployedTypeIds.add(id);
            }
            ArrayAdapter<String> adapter_self_employment = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    selfEmployedTypeArray
            );
            self_employed.setAdapter(adapter_self_employment);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        // start of click event
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                // get inputs
                String str_employmentOthers = employment_others.getText().toString();
                String str_operationYears = operation_years.getText().toString();
                String str_presentEmployer = present_employer.getText().toString();
                String str_businessNature = business_nature.getText().toString();
                String str_officeAddress = office_address.getText().toString();
                String str_officePhone = office_phone.getText().toString();
                String str_officeMobile = office_mobile.getText().toString();
                String str_localNumber = local_number.getText().toString();
                String str_faxNumber = fax_number.getText().toString();
                String str_emailAddress = email_add.getText().toString();
                String str_position = position.getText().toString();
                String str_grossIncome = gross_income.getText().toString();
                String str_otherIncome = other_income.getText().toString();
                String str_previousEmployer = previous_employer.getText().toString();
                String str_previousEmployerOffice = previous_employer_office.getText().toString();
                String str_presentEmployerYears = present_employer_years.getText().toString();
                String str_previousEmployerYears = previous_employer_years.getText().toString();

                //getting selected values from dropdowns and its corresponding ids
                //for employment type
                String str_employmentType = employment_type.getSelectedItem().toString();
                int type_index = employmentTypeArray.indexOf(str_employmentType);
                int employment_type_id = employmentTypeIds.get(type_index);

                //for self-employed type
                String str_selfEmployed = self_employed.getSelectedItem().toString();
                int self_employed_index = selfEmployedTypeArray.indexOf(str_selfEmployed);
                int self_employment_id = selfEmployedTypeIds.get(self_employed_index);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                String restoredText = sharedpreferences.getString("createWhat", " ");

                if (restoredText == "client") {
                    editor.putString("clientEmploymentOthers", str_employmentOthers);
                    editor.putString("clientOperationYears", str_operationYears);
                    editor.putString("clientPresentEmployer", str_presentEmployer);
                    editor.putString("clientBusinessNature", str_businessNature);
                    editor.putString("clientOfficeAddress", str_officeAddress);
                    editor.putString("clientOfficePhone", str_officePhone);
                    editor.putString("clientOfficeMobile", str_officeMobile);
                    editor.putString("clientLocalNo", str_localNumber);
                    editor.putString("clientFaxNo", str_faxNumber);
                    editor.putString("clientEmailAddress", str_emailAddress);
                    editor.putString("clientPosition", str_position);
                    editor.putString("clientGrossIncome", str_grossIncome);
                    editor.putString("clientOtherIncome", str_otherIncome);
                    editor.putString("clientPreviousEmployer", str_previousEmployer);
                    editor.putString("clientPreviousEmployerOffice", str_previousEmployerOffice);
                    editor.putString("clientPresentEmployerYears", str_presentEmployerYears);
                    editor.putString("clientPreviousEmployerYears", str_previousEmployerYears);
                    editor.putInt("clientEmploymentType", employment_type_id);
                    editor.putInt("clientSelfEmployed", self_employment_id);
                } else {
                    editor.putString("coMakerEmploymentOthers", str_employmentOthers);
                    editor.putString("coMakerOperationYears", str_operationYears);
                    editor.putString("coMakerPresentEmployer", str_presentEmployer);
                    editor.putString("coMakerBusinessNature", str_businessNature);
                    editor.putString("coMakerOfficeAddress", str_officeAddress);
                    editor.putString("coMakerOfficePhone", str_officePhone);
                    editor.putString("coMakerOfficeMobile", str_officeMobile);
                    editor.putString("coMakerLocalNo", str_localNumber);
                    editor.putString("coMakerFaxNo", str_faxNumber);
                    editor.putString("coMakerEmailAddress", str_emailAddress);
                    editor.putString("coMakerPosition", str_position);
                    editor.putString("coMakerGrossIncome", str_grossIncome);
                    editor.putString("coMakerOtherIncome", str_otherIncome);
                    editor.putString("coMakerPreviousEmployer", str_previousEmployer);
                    editor.putString("coMakerPreviousEmployerOffice", str_previousEmployerOffice);
                    editor.putString("coMakerPresentEmployerYears", str_presentEmployerYears);
                    editor.putString("coMakerPreviousEmployerYears", str_previousEmployerYears);
                    editor.putInt("coMakerEmploymentType", employment_type_id);
                    editor.putInt("coMakerSelfEmployed", self_employment_id);
                }

                editor.commit();

                // turn to next page
                Navigation.findNavController(v).navigate(R.id.action_employmentInformationFragment_to_personalReferenceFragment);

            }
        });
        // end of click event

        return binding.getRoot();
    }
}
