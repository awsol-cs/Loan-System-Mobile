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
import com.creditsaison.loansystem.databinding.FragmentEmploymentInfoBinding;
import com.creditsaison.loansystem.viewmodel.EmploymentInfoViewModel;

public class EmploymentInfoFragment extends Fragment {

    SharedPreferences sharedpreferences;
    EditText employment_others, operation_years, present_employer, business_nature, office_address,
            office_phone, office_mobile, local_number, fax_number, email_add, position,
            gross_income, other_income, previous_employer, previous_employer_office,
            present_employer_years, previous_employer_years;
    Spinner employmennt_type, self_employed;
    Button btn_next;

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

        // start of click event
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("currentSign", "termsCondition");
                editor.commit();

                String restoredText = sharedpreferences.getString("termsConditionImg", " ");

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
                employmennt_type = (Spinner) binding.getRoot().findViewById(R.id.sp_employment_type);
                self_employed = (Spinner) binding.getRoot().findViewById(R.id.sp_self_employed);

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
                String str_employmenntType = employmennt_type.getSelectedItem().toString();
                String str_selfEmployed = self_employed.getSelectedItem().toString();

                // turn to next page
                Navigation.findNavController(v).navigate(R.id.action_employmentInformationFragment_to_personalReferenceFragment);

            }
        });
        // end of click event

        return binding.getRoot();
    }
}
