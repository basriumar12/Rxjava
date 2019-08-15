package com.example.learnrx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.functions.Func4;

public class ValidasiActivity extends AppCompatActivity {


    EditText etPassword;
    TextView textPasswordAlert;
    EditText etPasswordConfirmation;
    TextView textPasswordConfirmationAlert;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi);

        etPassword = (EditText) findViewById(R.id.et_password);
        textPasswordAlert = (TextView) findViewById(R.id.text_password_alert);
        etPasswordConfirmation = (EditText) findViewById(R.id.et_password_confirmation);
        textPasswordConfirmationAlert = (TextView) findViewById(R.id.text_password_confirmation_alert);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        textPasswordAlert.setVisibility(View.GONE);
        textPasswordConfirmationAlert.setVisibility(View.GONE);
        btnSubmit.setEnabled(false);


        // Return true jika password yang diketik user < 6 karakter
        Observable<Boolean> passwordStream = RxTextView.textChanges(etPassword)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !TextUtils.isEmpty(charSequence)
                                && charSequence.toString().trim().length() < 6;
                    }
                });

        Observable<Boolean> passwordConfirmationStream = Observable.merge(
                RxTextView.textChanges(etPassword)
                        .map(new Func1<CharSequence, Boolean>() {
                            @Override
                            public Boolean call(CharSequence charSequence) {
                                return !charSequence.toString().trim().equals(etPasswordConfirmation.getText().toString());
                            }
                        }),
                RxTextView.textChanges(etPasswordConfirmation)
                        .map(new Func1<CharSequence, Boolean>() {
                            @Override
                            public Boolean call(CharSequence charSequence) {
                                return !charSequence.toString().trim().equals(etPassword.getText().toString());
                            }
                        })
        );



        Observer<Boolean> passwordObserver = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d("rx","Password stream completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rx",e.getMessage());
            }

            @Override
            public void onNext(Boolean passwordLessThanLimit) {
                Log.d("passwordObserver",String.valueOf(passwordLessThanLimit.booleanValue()));
                showPasswordMinimalAlert(passwordLessThanLimit.booleanValue());
            }
        };

        Observer<Boolean> passwordConfirmationObserver = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d("rx","Password confirmation stream completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rx",e.getMessage());
            }

            @Override
            public void onNext(Boolean passwordConfirmationDontMatch) {
                Log.d("passwordConfirmation",String.valueOf(passwordConfirmationDontMatch.booleanValue()));
                showPasswordConfirmationAlert(passwordConfirmationDontMatch.booleanValue());
            }
        };



        passwordStream.subscribe(passwordObserver);

        passwordConfirmationStream.subscribe(passwordConfirmationObserver);



    }


    public void showPasswordMinimalAlert(boolean value){
        if(value) {
            textPasswordAlert.setText(getString(R.string.password_minimal_alert));
            textPasswordAlert.setVisibility(View.VISIBLE);
        } else {
            textPasswordAlert.setVisibility(View.GONE);
        }
    }

    public void showPasswordConfirmationAlert(boolean value){
        if(value){
            textPasswordConfirmationAlert.setText(R.string.password_confirmation_does_not_match_alert);
            textPasswordConfirmationAlert.setVisibility(View.VISIBLE);
        } else {
            textPasswordConfirmationAlert.setVisibility(View.GONE);
        }
    }
}
