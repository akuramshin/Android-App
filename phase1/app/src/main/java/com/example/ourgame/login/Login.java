/*
 *
 *  * Copyright (C) 2018 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.example.ourgame.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.ourgame.MainActivity;
import com.example.ourgame.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Login extends AppCompatActivity implements LoginView {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;
    public final static String EXTRA_MESSAGE = "com.example.ourgame.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        presenter = new LoginPresenter(this, new LoginInteractor(), new RegistrationInteractor());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setLoginError() {
        username.setError(getString(R.string.login_error));
    }

    @Override
    public void setUsernameEmpty() {
        username.setError(getString(R.string.username_empty));
    }

    @Override
    public void setRegisterError() {
        username.setError(getString(R.string.register_failed));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_empty));
    }

    @Override
    public void navigateToHome(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
        finish();
    }

    public void validateCredentials(View view) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }

    public void validateRegistration(View view) {
        presenter.validateRegistration(username.getText().toString(), password.getText().toString());
    }
}