package com.example.doctruyenapplication.api;

import android.util.JsonReader;
import android.util.JsonWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://10.0.2.2:7115/api/"; // Replace with your base URL
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new CustomDateTypeAdapter())
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class CustomDateTypeAdapter extends TypeAdapter<Date> {
        private final SimpleDateFormat[] dateFormats = new SimpleDateFormat[] {
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault()),  // with milliseconds
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())           // without milliseconds
        };

        @Override
        public void write(com.google.gson.stream.JsonWriter out, Date value) throws IOException {
            out.value(dateFormats[0].format(value));
        }

        @Override
        public Date read(com.google.gson.stream.JsonReader in) throws IOException {
            String dateStr = in.nextString();
            for (SimpleDateFormat dateFormat : dateFormats) {
                try {
                    return dateFormat.parse(dateStr);
                } catch (ParseException ignored) {
                    // Try the next format
                }
            }
            throw new IOException("Unparseable date: " + dateStr);
        }
    }

}
