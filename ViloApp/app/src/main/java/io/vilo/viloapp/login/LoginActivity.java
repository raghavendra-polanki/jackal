package io.vilo.viloapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import io.vilo.viloapp.BaseActivity;
import io.vilo.viloapp.R;
import io.vilo.viloapp.ViloApplication;
import io.vilo.viloapp.api.BaseCallback;
import io.vilo.viloapp.api.RestError;
import io.vilo.viloapp.model.apiRequest.RegisterAppRequest;
import io.vilo.viloapp.model.apiResponse.RegisterAppResponse;
import io.vilo.viloapp.utils.Constants;
import io.vilo.viloapp.utils.LocalStorage;
import io.vilo.viloapp.utils.Utilities;
import io.vilo.viloapp.widget.InfoDialog;

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
            if(registerAppResponse.getStatus() == "OK") {
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
            ViloApplication.getInstance().getApiHandler()
                    .registerApp(new RegisterAppRequest(phoneNumber),
                            new RequestOtpCallback());
        } else {
            mPhoneNumberEt.setError(getString(R.string.invalid_phonenumber));
        }
    }
}
