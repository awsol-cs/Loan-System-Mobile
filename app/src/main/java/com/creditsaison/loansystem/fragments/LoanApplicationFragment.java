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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creditsaison.loansystem.MainActivity;
import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentLoanApplicationBinding;
import com.creditsaison.loansystem.viewmodel.LoanApplicationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanApplicationFragment extends Fragment implements View.OnClickListener {

    private FragmentLoanApplicationBinding binding;
    private LoanApplicationViewModel viewModel;

    SharedPreferences sharedpreferences;

    Button btn_repayment, btn_next;
    TextView submissionDate, disbursementDate, customer, loanTenure, interestRate, noOfRepayments;
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

        loanTenure = (TextView) binding.getRoot().findViewById(R.id.tv_loan_tenure_value);
        interestRate = (TextView) binding.getRoot().findViewById(R.id.tv_loan_interest_rate_value);
        noOfRepayments = (TextView) binding.getRoot().findViewById(R.id.tv_repayments_value);

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




        } catch (JSONException e) {
            e.printStackTrace();
        }

        loanProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int selectedProductIndex= parentView.getSelectedItemPosition();
                int selectedProductID = loanProductIds.get(selectedProductIndex);

                getLoanProductDetails(selectedProductID);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //do nothing
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_next) {
            SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy");
            Date dt_disburse, dt_submission;
            boolean valid_date = false;
            try {
                dt_disburse = df.parse(disbursementDate.getText().toString());
                dt_submission = df.parse(submissionDate.getText().toString());
                if (dt_disburse.after(dt_submission) || dt_disburse.equals(dt_submission)){
                    valid_date = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String str_loanProduct = loanProduct.getSelectedItem().toString();
            int product_index = loanProductArray.indexOf(str_loanProduct);
            int loan_product_id = loanProductIds.get(product_index);
            String str_loanPurpose = loanPurpose.getSelectedItem().toString();
            String str_submissionDate = submissionDate.getText().toString();
            String str_disbursement = disbursementDate.getText().toString();
            String str_principal = principalAmount.getText().toString();
            if (TextUtils.isEmpty(str_principal) || TextUtils.isEmpty(str_disbursement)) {

                Toast.makeText(getActivity().getApplicationContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();

            } else if (!valid_date){

                Toast.makeText(getActivity().getApplicationContext(), "Disbursement date cannot be less than the submission date", Toast.LENGTH_SHORT).show();

            } else {
                int int_principal = Integer.parseInt(str_principal);

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


    //get details of selected loan product
    public void getLoanProductDetails(int selectedLoanProductID) {
        Thread getLoanDetails = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uNameandPword = "mifos:password";
                    String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);

                    MainActivity main_act = (MainActivity)getActivity();
                    String ip_url = main_act.final_url;
                    String f_url = ip_url + "fineract-provider/api/v1/loans/template?activeOnly=true&productId="+selectedLoanProductID+"&templateType=individual";

                    URL url = new URL(f_url);

                    HttpURLConnection conn = null;

                    if (url.getProtocol().toLowerCase().equals("https")) {
                        trustAllHosts();
                        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                        https.setHostnameVerifier(DO_NOT_VERIFY);
                        conn = https;
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }

                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Fineract-Platform-TenantId", "default");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization",basicAutoPayload);
                    conn.setDoInput(true);

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    Log.i("OUTPUT", readStream(conn.getInputStream()));

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getLoanDetails.start();
    }

    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while((length = inputStream.read(buffer)) != -1){
            result.write(buffer, 0, length);
        }

        String data = result.toString(UTF_8.name());


        try {

            JSONObject detailsObject = new JSONObject(data);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            //getting loan products
            //JSONArray arr_loan_products = detailsObject.getJSONArray("productOptions");
            boolean allowPartialPeriodInterestCalcualtion =  detailsObject.optBoolean("allowPartialPeriodInterestCalcualtion");
            int amortizationType =  detailsObject.getJSONObject("amortizationType").getInt("id");
            int interestCalculationPeriodType =  detailsObject.getJSONObject("interestCalculationPeriodType").getInt("id");
            int interestRatePerPeriod =  detailsObject.optInt("interestRatePerPeriod");
            int interestType =  detailsObject.getJSONObject("interestType").getInt("id");
            boolean isEqualAmortization =  detailsObject.optBoolean("isEqualAmortization");
            int numberOfRepayments =  detailsObject.optInt("numberOfRepayments");
            int repaymentEvery =  detailsObject.optInt("repaymentEvery");
            int repaymentFrequencyTypeID =  detailsObject.getJSONObject("repaymentFrequencyType").getInt("id");
            String repaymentFrequencyTypeValue = detailsObject.getJSONObject("repaymentFrequencyType").getString("value");
            int transactionProcessingStrategyId =  detailsObject.optInt("transactionProcessingStrategyId");
            int loanTermFrequency =  detailsObject.optInt("termFrequency");
            int loanTermFrequencyTypeID =  detailsObject.getJSONObject("termPeriodFrequencyType").getInt("id");
            String loanTermFrequencyTypeValue = detailsObject.getJSONObject("termPeriodFrequencyType").getString("value");


            //add to sharedpreferences
            editor.putBoolean("allowPartialPeriodInterestCalculation", allowPartialPeriodInterestCalcualtion);
            editor.putInt("amortizationType", amortizationType);
            editor.putInt("interestCalculationPeriodType", interestCalculationPeriodType);
            editor.putInt("interestRatePerPeriod", interestRatePerPeriod);
            editor.putInt("interestType", interestType);
            editor.putBoolean("isEqualAmortization", isEqualAmortization);
            editor.putInt("numberOfRepayments", numberOfRepayments);
            editor.putInt("repaymentEvery", repaymentEvery);
            editor.putInt("repaymentFrequencyTypeID", repaymentFrequencyTypeID);
            editor.putInt("transactionProcessingStrategyId", transactionProcessingStrategyId);
            editor.putInt("loanTermFrequency", loanTermFrequency);
            editor.putInt("loanTermFrequencyTypeID", loanTermFrequencyTypeID);


            editor.commit();

            //set textviews
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    loanTenure.setText(String.valueOf(loanTermFrequency) + " " + loanTermFrequencyTypeValue);
                    interestRate.setText(String.valueOf(interestRatePerPeriod) + "%");
                    noOfRepayments.setText(String.valueOf(numberOfRepayments) + " " + repaymentFrequencyTypeValue);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return data;
    }

}

