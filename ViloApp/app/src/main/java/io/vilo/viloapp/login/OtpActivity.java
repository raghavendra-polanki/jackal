package io.vilo.viloapp.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import io.vilo.viloapp.BaseActivity;
import io.vilo.viloapp.R;
import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.api.RestCallback;
import io.vilo.viloapp.api.RestError;
import io.vilo.viloapp.home.EngineFeedActivity;
import io.vilo.viloapp.model.User;
import io.vilo.viloapp.model.apiRequest.AuthenticateOtpRequest;
import io.vilo.viloapp.model.apiRequest.RegisterAppRequest;
import io.vilo.viloapp.model.apiResponse.RegisterAppResponse;
import io.vilo.viloapp.utils.Constants;
import io.vilo.viloapp.utils.LocalStorage;
import io.vilo.viloapp.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class OtpActivity extends BaseActivity {

    @Bind(R.id.et_otp)
    EditText mOtpEt;

    @Bind(R.id.tv_otp_sent)
    TextView mOtpSentMessageTv;

    @Bind(R.id.tv_resend_otp)
    TextView mResendOtp;

    private String mPhoneNumber;

    private AwesomeValidation mAwesomeValidation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        mPhoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        mOtpSentMessageTv.setText(String.format(getString(R.string.otp_sent), mPhoneNumber));

        setupValidations();

        mResendOtp.setPaintFlags(mResendOtp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mOtpEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendOtp();
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        // Set highest possible priority so we always get the message
        // http://stackoverflow.com/a/14396136/493321
        filter.setPriority(2147483647);
        registerReceiver(smsReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsReceiver);
    }

    @OnClick(R.id.tv_login)
    public void onCLickLogin() {
        sendOtp();
    }

    @OnClick(R.id.tv_resend_otp)
    public void onCLickResendOtp() {
        showProgress(getString(R.string.resending_otp));
        String phoneNumber = "+91" +  mPhoneNumber;

        ViloApplication.getInstance().getApiHandler()
                .registerApp(new RegisterAppRequest(phoneNumber), new ResendOtpCallback());
    }

    private  class AuthenticateOtpCallback extends RestCallback<User> {
        @Override
        public void failure(RestError restError) {
            hideProgressDialog();
            Snackbar.make(mOtpEt, R.string.login_failed, Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void success(User user, Response response) {
            hideProgressDialog();
            if(user.getSts() == 1) {
                LocalStorage.getInstance().storeUser(user);

//                ViloApplication.getInstance().setMixpanelUser();
                JSONObject props = new JSONObject();
                try {
                    props.put("distributorId", user.getDistributorId());
                    props.put("distributorName", user.getFirstName() + " " + user.getLastName());
                    if (user.getPhoneNumberList().size() > 0) {
                        props.put("mobile", user.getPhoneNumberList().get(0).getNum());
                    }
                    if (user.getEmailIdList().size() > 0) {
                        props.put("email", user.getEmailIdList().get(0).getId());
                    }
                } catch (JSONException ignored) {
                }
                if (LocalStorage.getInstance(OtpActivity.this).isNewUser()) {
//                    ViloApplication.getInstance().getMixpanel().track(Constants.LOGIN_NEW_USER,
//                            props);
                } else {
//                    ViloApplication.getInstance().getMixpanel().track(Constants.LOGIN_OLD_USER,
//                            props);
                }

                Intent homeIntent = new Intent(OtpActivity.this, EngineFeedActivity.class);
//                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setResult(RESULT_OK);
                startActivity(homeIntent);
                finish();
            } else {
                Snackbar.make(mOtpEt, R.string.login_failed, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private class ResendOtpCallback extends RestCallback<RegisterAppResponse> {
        @Override
        public void failure(RestError restError) {
            hideProgressDialog();
            Snackbar.make(mOtpEt, R.string.otp_sending_failed, Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void success(RegisterAppResponse registerAppResponse, Response response) {
            hideProgressDialog();
            if(registerAppResponse.getStatus() == 0) {
                Snackbar.make(mOtpEt, R.string.otp_sending_failed, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void hideProgressDialog() {
        hideProgress();
    }

    private void setupValidations() {
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mAwesomeValidation.addValidation(mOtpEt, Constants.REGEX_REQUIRED, getString(R.string.required));
        mAwesomeValidation.addValidation(mOtpEt, Constants.REGEX_OTP, getString(R.string.enter_valid_otp));
    }

    private void sendOtp() {
        if (NetworkUtils.isNetworkAvailable(OtpActivity.this)) {
            mAwesomeValidation.clear();
            if(mAwesomeValidation.validate()) {
                showProgress(getString(R.string.validating_otp));
                String phone = "+91" + mPhoneNumber;

                AuthenticateOtpRequest request = new AuthenticateOtpRequest(phone,
                        mOtpEt.getText().toString());
                request.setDeviceId(LocalStorage.getInstance().getGcmId());
                request.setVersionCode(ViloApplication.getInstance().getVersionCode());

                ViloApplication.getInstance().getApiHandler()
                        .authenticateOtp(request, new AuthenticateOtpCallback());
            }
        } else {
            NetworkUtils.showNoInternetConnection(mOtpEt, OtpActivity.this);
        }
    }

    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {

        private static final String SMS_BUNDLE = "pdus";

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                if (sms == null) {
                    return;
                }

                String body = "";
                String sender = "";

                for (Object sm : sms) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm);

                    body = smsMessage.getMessageBody();
                    sender = smsMessage.getOriginatingAddress();
                }

                if (sender.toUpperCase().contains(Constants.SMS_SENDER)) {
                    //this will update the UI with message
                    Pattern p = Pattern.compile(Constants.OTP_REGEX);
                    Matcher m = p.matcher(body);

                    if (m.find()) {
                        String otp = m.group(1);
                        Log.d("Debug", "OTP:: " + otp);
                        mOtpEt.setText(otp);
                        sendOtp();
                    }
                }
            }
        }
    };
}
