package com.creditsaison.loansystem.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountBinding;
import com.creditsaison.loansystem.viewmodel.AccountViewModel;

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

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String MyPREFERENCES = "MyPrefs" ;

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();

        String signImgDef = "default";

        editor.putString("currentSign", "termsCondition");
        editor.putString("termsConditionImg", signImgDef);
        editor.putString("loanAgreementImg", signImgDef);
        editor.putString("promNoteImg", signImgDef);
        editor.putString("createWhat", "client");
        editor.commit();

        getData();

        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

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

    public void getData() {
        Thread databaseValue = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uNameandPword = "mifos:password";
                    String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);

                    URL url = new URL("https://192.168.227.159/fineract-provider/api/v1/cs_clients/template?staffInSelectedOfficeOnly=true");

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

                        URL url = new URL("https://192.168.227.159/fineract-provider/api/v1/loans/template?activeOnly=true&templateType=individual");

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

            } catch (JSONException e) {
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return data;
    }

}
