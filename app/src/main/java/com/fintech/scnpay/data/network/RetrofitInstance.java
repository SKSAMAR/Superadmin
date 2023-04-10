package com.fintech.scnpay.data.network;

public class RetrofitInstance {
//    public static String BASE_URL = "https://paydeer.app/mobile_phone/";
//
//    @SuppressLint("StaticFieldLeak")
//    public static NetworkConnectionInterceptor networkConnectionInterceptor;
//
//    public static void addNetworkConnectionInterceptor(NetworkConnectionInterceptor networkConnectionInterceptor){
//        RetrofitInstance.networkConnectionInterceptor = networkConnectionInterceptor;
//    }
//
//    public static Retrofit retrofit;
//
//
//    public static Retrofit getRetrofitClient(){
//        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
//                .connectTimeout(240, TimeUnit.SECONDS)
//                .readTimeout(240, TimeUnit.SECONDS)
//                .writeTimeout(240, TimeUnit.SECONDS)
//                .addInterceptor(networkConnectionInterceptor)
//                .build();
//
//        if(retrofit == null){
//            final Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
//                    .create();
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(BASE_URL)
//                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }

}
