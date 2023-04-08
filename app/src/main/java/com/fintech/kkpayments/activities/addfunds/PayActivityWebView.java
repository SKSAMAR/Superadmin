package com.fintech.kkpayments.activities.addfunds;

//import static com.morpho.android.usb.USBManager.context;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.databinding.ActivityPayBinding;
import com.fintech.kkpayments.listeners.WebViewPayment;
import com.fintech.kkpayments.util.DisplayMessageUtil;
import com.fintech.kkpayments.util.MyAlertUtils;
import com.fintech.kkpayments.util.ViewUtils;
import com.fintech.kkpayments.viewmodel.FundViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayActivityWebView extends AppCompatActivity implements WebViewPayment {

    ActivityPayBinding binding;
    FundViewModel viewModel;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        binding.amountbalance.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
        binding.amountbalance.setCompoundDrawablePadding(20);
        setListeners();
        makeThePayment();
        tts = new TextToSpeech(PayActivityWebView.this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.forLanguageTag("hi-IN"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    ViewUtils.showToast(PayActivityWebView.this, "Hindi Language is not supported");
                }
            }
        });
        if (savedInstanceState == null) {
            binding.payScreen.setVisibility(View.VISIBLE);
            binding.webViewScreen.setVisibility(View.GONE);
        }
        WebView.setWebContentsDebuggingEnabled(true);
        initWebView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {

        binding.one.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.one.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.two.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.two.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.three.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.three.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.four.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.four.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.five.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.five.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.six.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.six.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.seven.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.seven.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.eight.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.eight.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.nine.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.nine.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.zeo.setOnClickListener(v -> {
            if (!binding.amountbalance.getText().toString().isEmpty()) {
                String value = binding.amountbalance.getText().toString() + binding.zeo.getText().toString();
                binding.amountbalance.setText(value);
            }
        });

        binding.erase.setOnClickListener(v -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < binding.amountbalance.getText().toString().length() - 1; i++) {
                builder.append(binding.amountbalance.getText().toString().charAt(i));
            }

            binding.amountbalance.setText(String.valueOf(builder));
        });
    }


    private void makeThePayment() {
        binding.onRequestMoneyButton.setOnClickListener(view -> {
            if (binding.amountbalance.getText().toString().isEmpty()) {
                MyAlertUtils.showAlertDialog(PayActivityWebView.this, "Warning", "Provide Valid Amount", R.drawable.warning);
            } else {
                String audioAmount = binding.amountbalance.getText().toString();
                viewModel.madeValidPayment(audioAmount, PayActivityWebView.this, PayActivityWebView.this);
            }

        });
    }

    @Override
    public void homeScreen(String message, boolean result) {
        if (result) {
            DisplayMessageUtil.success(this, message);
        } else {
            DisplayMessageUtil.error(this, message);
        }
        binding.payScreen.setVisibility(View.VISIBLE);
        binding.webViewScreen.setVisibility(View.GONE);
    }

    @Override
    public void webViewPage(String message) {
        binding.webViewScreen.loadUrl(message);
        binding.payScreen.setVisibility(View.GONE);
        binding.webViewScreen.setVisibility(View.VISIBLE);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        binding.webViewScreen.getSettings().setJavaScriptEnabled(true);
        binding.webViewScreen.setWebViewClient(new MyClient());
        binding.webViewScreen.setWebChromeClient(new WebChromeClient());
        binding.webViewScreen.addJavascriptInterface(new WebviewInterface(), "Interface");
    }

    public class MyClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            DisplayMessageUtil.loading(PayActivityWebView.this);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            DisplayMessageUtil.dismissDialog();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.contains("upi://pay?")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                return true;
            }
            if (url.toLowerCase().contains(getApplicationContext().getString(R.string.base_url_data))) {
                homeScreen("Success", true);
                audio(binding.amountbalance.getText().toString());
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }
    }


    public class WebviewInterface {
        @JavascriptInterface
        public void errorResponse() {
            DisplayMessageUtil.error(PayActivityWebView.this, "Transaction Error.");
        }

        @JavascriptInterface
        public void paymentResponse(String client_txn_id, String txn_id) {
            audio(binding.amountbalance.getText().toString());
            Toast.makeText(PayActivityWebView.this, "Payment has been Done.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void audio(String text) {

        AudioManager audioManager;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = (float) currentVolume / maxVolume; // Calculate the current volume as a fraction of the maximum volume
        }
        if (!tts.isSpeaking()) {
            tts.setSpeechRate(.9f);
            tts.speak("केके पेमेंट्स बिजनेस पर अभी अभी " + text + " रूपए  प्राप्त हुए है।", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}