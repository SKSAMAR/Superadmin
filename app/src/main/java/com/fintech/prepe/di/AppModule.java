package com.fintech.prepe.di;


import android.content.Context;

import com.fintech.prepe.auth.data.AuthApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fintech.prepe.BaseApplication;
import com.fintech.prepe.Provider;
import com.fintech.prepe.R;
import com.fintech.prepe.clean.data.remote.YesApi;
import com.fintech.prepe.clean.data.repository.YesRepositoryImp;
import com.fintech.prepe.clean.domain.repository.YesRepository;
import com.fintech.prepe.constructor.Construct;
import com.fintech.prepe.data.db.AppDatabase;
import com.fintech.prepe.data.db.dao.AuthDao;
import com.fintech.prepe.data.db.dao.ProfileDao;
import com.fintech.prepe.data.db.dao.UserDao;
import com.fintech.prepe.data.db.entities.AuthData;
import com.fintech.prepe.data.network.APIServices;
import com.fintech.prepe.data.network.NetworkConnectionInterceptor;
import com.fintech.prepe.data.repositories.UserRepository;
import com.fintech.prepe.util.Constant;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public static BaseApplication provideApplication(@ApplicationContext Context context) {
        return (BaseApplication) context;
    }

    @Singleton
    @Provides
    public static NetworkConnectionInterceptor getNetworkInterceptor(@ApplicationContext Context context) {
        return new NetworkConnectionInterceptor(context);
    }


    @Singleton
    @Provides
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


    @Singleton
    @Provides
    public static OkHttpClient getOkHttpClient(
            @ApplicationContext Context context,
            NetworkConnectionInterceptor networkConnectionInterceptor,
            HttpLoggingInterceptor httpLoggingInterceptor,
            SSLSocketFactory sslSocketFactory
    ) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                /**
                .sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                })
                **/
                .hostnameVerifier((hostname, session) -> {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify(context.getString(R.string.base_url_data), session);
                });

        httpClient.addInterceptor(chain -> {
            AuthData data = AppDatabase.getAppDatabase(context).getAuthDao().getAuthData();
            String token;
            try {
                token = data.getToken();
            } catch (NullPointerException e) {
                token = Constant.getRegistrationToken();
            }
            Request request = chain.request().newBuilder()
                    .addHeader("token", token)
                    .addHeader("Device", Provider.getDeviceModel())
                    .addHeader("Ip", Provider.getLocalIpAddress())
                    .build();
            Response response = chain.proceed(request);
            if (response.code() == 401) {
                Construct.logoutExecute(context);
            } else if (response.code() == 401) {
                Construct.logoutExecute(context);
            }
            return response;
        });
        httpClient.addInterceptor(networkConnectionInterceptor);
        httpClient.addInterceptor(httpLoggingInterceptor);
        return httpClient.build();
    }


    @Singleton
    @Provides
    public static APIServices getApiServices(@ApplicationContext Context context , OkHttpClient httpClient) {

        final Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://"+context.getString(R.string.base_url_data)+"/")
                .client(httpClient)
                .build();
        return retrofit.create(APIServices.class);
    }


    @Singleton
    @Provides
    public static AuthApi getAuthApi(@ApplicationContext Context context ,OkHttpClient httpClient) {

        final Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://"+context.getString(R.string.base_url_data)+"/")
                .client(httpClient)
                .build();
        return retrofit.create(AuthApi.class);
    }

    @Singleton
    @Provides
    public static YesApi getYesAPI(@ApplicationContext Context context ,OkHttpClient httpClient) {
        final Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://"+context.getString(R.string.base_url_data)+"/")
                .client(httpClient)
                .build();
        return retrofit.create(YesApi.class);
    }

    @Singleton
    @Provides
    public static UserRepository userRepository(@ApplicationContext Context context, APIServices apiServices) {
        return new UserRepository(context, apiServices);
    }

    @Singleton
    @Provides
    public static YesRepository yesRepository(YesApi api, UserDao userDao, AuthDao authDao, ProfileDao profileDao) {
        return new YesRepositoryImp(api, authDao, userDao, profileDao);
    }


    @Singleton
    @Provides
    public SSLSocketFactory getGlobalSSlFactory(@ApplicationContext Context context) {
        try {
            InputStream inputStream= context.getResources().openRawResource(R.raw.certificate);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca = cf.generateCertificate(inputStream);
            inputStream.close();
            KeyStore keyStore = KeyStore.getInstance("BKS");
            keyStore.load(null, null);

            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "xxxxxxx".toCharArray());

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Singleton
    @Provides
    public static AppDatabase getDatabase(@ApplicationContext Context context) {
        return AppDatabase.getAppDatabase(context);
    }

    @Singleton
    @Provides
    public static AuthDao getAuthDao(AppDatabase appDatabase) {
        return appDatabase.getAuthDao();
    }

    @Singleton
    @Provides
    public static UserDao getUserDao(AppDatabase appDatabase) {
        return appDatabase.getUserDao();
    }

    @Singleton
    @Provides
    public static ProfileDao getUserProfileDao(AppDatabase appDatabase) {
        return appDatabase.getUserProfileDao();
    }

}
