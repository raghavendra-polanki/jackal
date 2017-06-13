package com.example.raghavendra.vidnet.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.example.raghavendra.vidnet.BaseActivity;
import com.example.raghavendra.vidnet.R;
import com.example.raghavendra.vidnet.VidNetApplication;
import com.example.raghavendra.vidnet.api.BaseCallback;
import com.example.raghavendra.vidnet.api.RestError;
import com.example.raghavendra.vidnet.model.apiRequest.RegisterAppRequest;
import com.example.raghavendra.vidnet.model.apiResponse.RegisterAppResponse;
import com.example.raghavendra.vidnet.utils.Constants;
import com.example.raghavendra.vidnet.utils.LocalStorage;
import com.example.raghavendra.vidnet.utils.Utilities;
import com.example.raghavendra.vidnet.widget.InfoDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity {

    public static final String KEY_IS_LOGOUT = "is_logout";

    static final int LOGIN_ACTIVITY_FINISH_STATUS = 1;  // The request code

    @Bind(R.id.et_otp)
    EditText mPhoneNumberEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPhoneNumberEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                requestOtp();
                return false;
            }
        });

        if (getIntent().getBooleanExtra(KEY_IS_LOGOUT, false)) {
            InfoDialog d = InfoDialog.newInstance(null, getString(R.string.you_have_been_logged_out));
            d.show(getSupportFragmentManager(), "logout_message");
        }
    }

    @OnClick(R.id.tv_request_otp)
    public void clickRequestOtp() {
        requestOtp();
    }

    private class RequestOtpCallback extends BaseCallback<RegisterAppResponse> {
        @Override
        public void failure(RestError restError) {
            hideProgress();
            Snackbar.make(mPhoneNumberEt, R.string.otp_sending_failed, Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void success(RegisterAppResponse registerAppResponse, Response response) {
            if (isLoggedOut(registerAppResponse)) {
                return;
            }
            hideProgress();
            if(registerAppResponse.getStatus() == 1) {
                LocalStorage.getInstance(LoginActivity.this).setIsNewUser(registerAppResponse.isNew());

                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                intent.putExtra(Constants.PHONE_NUMBER, mPhoneNumberEt.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent, LOGIN_ACTIVITY_FINISH_STATUS);
//                startActivity(intent);
            } else {
                Snackbar.make(mPhoneNumberEt, R.string.otp_sending_failed, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGIN_ACTIVITY_FINISH_STATUS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                finish();
                // Do something with the contact here (bigger example below)
            }
        }
    }

    private void requestOtp() {
        if(Utilities.isValidPhone(mPhoneNumberEt.getText())) {
            showProgress(getString(R.string.requesting_otp));
            String phoneNumber = "+91" +  mPhoneNumberEt.getText().toString();
            VidNetApplication.getInstance().getApiHandler()
                    .registerApp(new RegisterAppRequest(phoneNumber),
                            new RequestOtpCallback());
        } else {
            mPhoneNumberEt.setError(getString(R.string.invalid_phonenumber));
        }
    }
}
