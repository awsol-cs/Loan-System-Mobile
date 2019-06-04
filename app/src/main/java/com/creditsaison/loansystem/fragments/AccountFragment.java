package com.creditsaison.loansystem.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.creditsaison.loansystem.MainActivity;
import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountBinding;
import com.creditsaison.loansystem.viewmodel.AccountViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

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
public class AccountFragment extends Fragment {

    SharedPreferences sharedpreferences;
    Button btn_no;

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String MyPREFERENCES = "MyPrefs" ;

        MainActivity main_act = (MainActivity)getActivity();
        String ip_url = main_act.final_url;

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();

        String signImgDef = "default";

        editor.putString("currentSign", "termsCondition");
        editor.putString("termsConditionImg", signImgDef);
        editor.putString("loanAgreementImg", signImgDef);
        editor.putString("promNoteImg", signImgDef);
        editor.putString("createWhat", "client");
        editor.putString("buttonEnable1", "disable");
        editor.putString("buttonEnable2", "disable");


        editor.commit();

        getData(ip_url);

        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        btn_no = (Button) binding.getRoot().findViewById(R.id.btn_no);

        // start of click event
        btn_no.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String buttonEnable1 = sharedpreferences.getString("buttonEnable1", "");
                  String buttonEnable2 = sharedpreferences.getString("buttonEnable2", "");

                  if (buttonEnable1 == "enable" && buttonEnable2 == "enable"){
                      Navigation.findNavController(v).navigate(R.id.action_accountFragment_to_accountNewFragment);
                  } else {
                      Toast.makeText(getActivity().getApplicationContext(), "Cannot Proceed Because of Database Connection Error", Toast.LENGTH_SHORT).show();
                  }
              }
        });

        return binding.getRoot();
    }


    // always verify the host - dont check for certificate
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

    public void getData(String ip_url) {
        Thread databaseValue = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uNameandPword = "mifos:password";
                    String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);

                    String f_url = ip_url + "fineract-provider/api/v1/cs_clients/template?staffInSelectedOfficeOnly=true";

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
                    Log.i("OUTPUT", readStream(conn.getInputStream(), "databaseValue"));

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        databaseValue.start();

        Thread loanData = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    databaseValue.join();

                    try {
                        String uNameandPword = "mifos:password";
                        String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);

                        String f_url = ip_url + "fineract-provider/api/v1/loans/template?activeOnly=true&templateType=individual";

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
                        Log.i("OUTPUT", readStream(conn.getInputStream(), "loanData"));

                        conn.disconnect();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        loanData.start();
    }

    public String readStream(InputStream inputStream, String process) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while((length = inputStream.read(buffer)) != -1){
            result.write(buffer, 0, length);
        }

        String data = result.toString(UTF_8.name());

        if (process == "databaseValue") {

            try {

                JSONObject jsonObject = new JSONObject(data);

                Iterator<String> keys = jsonObject.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    Log.v("**********************", "\"**********************");
                    Log.v("keys", key);
                }

                // this is the part where you save data in sharedpreference
                SharedPreferences.Editor editor = sharedpreferences.edit();

                //getting arrays from json object
                //for client basic info
                JSONArray arr_gender = jsonObject.getJSONArray("genderOptions");
                JSONArray arr_marital_status = jsonObject.getJSONArray("clientMaritalStatusOptions");
                JSONArray arr_educ_attain = jsonObject.getJSONArray("clientEducationalAttainmentOptions");

                //for residence info
                JSONArray arr_residence_ownership = jsonObject.getJSONArray("clientResidenceOwnershipOptions");
                JSONArray arr_address_type = jsonObject.getJSONArray("address").getJSONObject(0).getJSONArray("addressTypeIdOptions");
                JSONArray arr_state = jsonObject.getJSONArray("address").getJSONObject(0).getJSONArray("stateProvinceIdOptions");
                JSONArray arr_country = jsonObject.getJSONArray("address").getJSONObject(0).getJSONArray("countryIdOptions");

                //for employment info
                JSONArray arr_employment_type = jsonObject.getJSONArray("clientEmploymentOptions");
                JSONArray arr_self_employment = jsonObject.getJSONArray("clientSelfEmployedOptions");

                //for personal reference
                JSONArray arr_relationship = jsonObject.getJSONArray("clientRelationshipOfOfficerOptions");
                JSONArray arr_isRelated = jsonObject.getJSONArray("clientRelatedToOfficerOptions");


                //saving arrays to sharedpreferences
                //for client basic info
                editor.putString("arr_gender", arr_gender.toString());
                editor.putString("arr_marital_status", arr_marital_status.toString());
                editor.putString("arr_educ_attain", arr_educ_attain.toString());

                //for residence info
                editor.putString("arr_residence_ownership", arr_residence_ownership.toString());
                editor.putString("arr_address_type", arr_address_type.toString());
                editor.putString("arr_state", arr_state.toString());
                editor.putString("arr_country", arr_country.toString());

                //for employment info
                editor.putString("arr_employment_type", arr_employment_type.toString());
                editor.putString("arr_self_employment", arr_self_employment.toString());

                //for personal reference
                editor.putString("arr_relationship", arr_relationship.toString());
                editor.putString("arr_isRelated", arr_isRelated.toString());

                editor.putString("buttonEnable1", "enable");

                editor.commit();

            } catch (JSONException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "Database Connectivity Error", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }


        } else {

            try {

                JSONObject jsonObject = new JSONObject(data);

                Iterator<String> keys = jsonObject.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    Log.v("**********************", "\"**********************");
                    Log.v("keys", key);
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();

                //getting loan products
                JSONArray arr_loan_products = jsonObject.getJSONArray("productOptions");

                //add to sharedpreferences
                editor.putString("arr_loan_products", arr_loan_products.toString());

                editor.putString("buttonEnable2", "enable");

                editor.commit();

            } catch (JSONException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(), "Database Connectivity Error", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

        }


        return data;
    }

}
