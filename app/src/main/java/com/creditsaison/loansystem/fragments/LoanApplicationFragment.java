package com.creditsaison.loansystem.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentLoanApplicationBinding;
import com.creditsaison.loansystem.viewmodel.LoanApplicationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanApplicationFragment extends Fragment implements View.OnClickListener {

    private FragmentLoanApplicationBinding binding;
    private LoanApplicationViewModel viewModel;

    SharedPreferences sharedpreferences;

    Button btn_repayment, btn_next;
    TextView submissionDate, disbursementDate, customer;
    DatePickerDialog datePickerDialog;
    EditText principalAmount;
    Spinner loanProduct, loanPurpose;

    List<String> loanProductArray;
    List<Integer> loanProductIds;

    public LoanApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoanApplicationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoanApplicationBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        btn_repayment = (Button) binding.getRoot().findViewById(R.id.btn_repayment);
        btn_repayment.setOnClickListener(this);

        btn_next = (Button) binding.getRoot().findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        customer = (TextView) binding.getRoot().findViewById(R.id.tv_customer);
        customer.setText(sharedpreferences.getString("clientfirstName", "") + " " + sharedpreferences.getString("clientlastName", ""));

        submissionDate = (TextView) binding.getRoot().findViewById(R.id.tv_submission_date);
        String date = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(new Date());
        submissionDate.setText(date);
        submissionDate.setOnClickListener(this);

        disbursementDate = (TextView) binding.getRoot().findViewById(R.id.tv_disbursementon_date);
        disbursementDate.setOnClickListener(this);

        principalAmount = (EditText) binding.getRoot().findViewById(R.id.et_principal);
        loanProduct = (Spinner) binding.getRoot().findViewById(R.id.sp_lproduct);
        loanPurpose = (Spinner) binding.getRoot().findViewById(R.id.sp_loan_purpose);

        loanProductArray = new ArrayList<>();
        loanProductIds = new ArrayList<>();

        // this is the part where you assign the saved data in sharedpreference to array
        try {

            JSONArray arr_loan_products = new JSONArray(sharedpreferences.getString("arr_loan_products", " "));

            //populate arrays for loan product values(String) and ids(Int)
            for(int i = 0; i < arr_loan_products.length(); i++) {
                JSONObject jsonObject1 = arr_loan_products.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                loanProductArray.add(name);
                loanProductIds.add(id);
            }
            //setting spinner for residence ownership
            ArrayAdapter<String> adapter_products = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    loanProductArray
            );
            loanProduct.setAdapter(adapter_products);


            Log.i("LOAN PRODUCTS SIZE", String.valueOf(loanProductArray.size()));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_next) {

            String str_loanProduct = loanProduct.getSelectedItem().toString();
            int product_index = loanProductArray.indexOf(str_loanProduct);
            int loan_product_id = loanProductIds.get(product_index);
            String str_loanPurpose = loanPurpose.getSelectedItem().toString();
            String str_submissionDate = submissionDate.getText().toString();
            String str_disbursement = disbursementDate.getText().toString();
            String str_principal = principalAmount.getText().toString();
            int int_principal = 0;
            if (!TextUtils.isEmpty(str_principal)) {
                int_principal = Integer.parseInt(str_principal);
            }


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("createWhat", "coMaker");

            editor.putInt("LoanProduct", loan_product_id);
            editor.putString("LoanPurpose", str_loanPurpose);
            editor.putString("LoanSubmissionDate", str_submissionDate);
            editor.putString("LoanDisbursement", str_disbursement);
            editor.putString("LoanPrincipal", str_principal);

            editor.commit();


            if (int_principal >= 50000) {
                Navigation.findNavController(v).navigate(R.id.action_loanApplicationFragment_to_accountNewFragment_as_coMaker);
            } else {
                Navigation.findNavController(v).navigate(R.id.action_loanApplicationFragment_to_termsConditionFragment);
            }
        } else if (v == btn_repayment) {
            Navigation.findNavController(v).navigate(R.id.action_loanApplicationFragment_to_loanRepaymentDetailsFragment);
        } else {
            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            // date picker dialog
            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // set day of month , month and year value in the textview
                    ((TextView) v).setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                }

            }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }
    }

}


//https://192.168.227.159/fineract-provider/api/v1/loans/template?activeOnly=true&productId=1&templateType=individual
