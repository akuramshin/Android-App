package com.example.ourgame.Menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ourgame.R;
import com.example.ourgame.Utilities.DataWriter;
import com.example.ourgame.Themes.Theme;
import com.example.ourgame.Themes.ThemeBuilder;
import com.example.ourgame.Utilities.ScreenLoader;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private String user;
    private DataWriter data;
    private ScreenLoader screenLoader;
    private ImageView character;

    private RadioGroup languageRadioGroup;
    private RadioButton englishButton, frenchButton;
    private ImageButton autumn, winter, summer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageRadioGroup = findViewById(R.id.languageRadioGroup);
        englishButton = findViewById(R.id.englishButton);
        frenchButton = findViewById(R.id.frenchButton);
        character = findViewById(R.id.characterImage);

        data = new DataWriter(this);
        user = data.getUser();
        screenLoader = new ScreenLoader(this);

        ConstraintLayout constraintLayout = findViewById(R.id.settingsActivityLayout);
        ThemeBuilder themeBuilder = new ThemeBuilder(data.getThemeData(user));
        Theme theme = themeBuilder.getTheme();
        constraintLayout.setBackgroundResource(theme.SettingsActivityLayout());

        autumn = findViewById(R.id.autumnButton);
        winter = findViewById(R.id.winterButton);
        summer = findViewById(R.id.summerButton);

        autumn.setOnClickListener(this);
        winter.setOnClickListener(this);
        summer.setOnClickListener(this);

        character.setImageResource(data.getCharacterData(user));


        if (data.getLanguage(user).equals("french")) {
            frenchButton.setChecked(true);
        } else if (data.getLanguage(user).equals("english")) {
            englishButton.setChecked(true);
        }

        if (data.getThemeData(user).equals("autumn")) {
            winter.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
            summer.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (data.getThemeData(user).equals("winter")) {
            autumn.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
            summer.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            winter.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
            autumn.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    // go to choose character page, then return to settings
    public void onChooseCharacterPressed(View view) {
        switch (data.getCharacterData(user)){
            case R.drawable.boy:
                character.setImageResource(R.drawable.girl);
                data.setCharacterData(user, R.drawable.girl);
                break;
            case R.drawable.girl:
                character.setImageResource(R.drawable.male);
                data.setCharacterData(user, R.drawable.male);
                break;
            case R.drawable.male:
                character.setImageResource(R.drawable.female);
                data.setCharacterData(user, R.drawable.female);
                break;
            case R.drawable.female:
                character.setImageResource(R.drawable.kid);
                data.setCharacterData(user, R.drawable.kid);
                break;
            case R.drawable.kid:
                character.setImageResource(R.drawable.boy);
                data.setCharacterData(user, R.drawable.boy);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.autumnButton:
                autumn.clearColorFilter();
                winter.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                summer.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                data.setThemeData(user, "autumn");
                break;
            case R.id.winterButton:
                winter.clearColorFilter();
                autumn.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                summer.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                data.setThemeData(user, "winter");
                break;
            case R.id.summerButton:
                summer.clearColorFilter();
                winter.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                autumn.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                data.setThemeData(user, "summer");
                break;

        }
    }

    // save selected theme and language with data saver, and
    // go to main menu
    public void onSaveButtonPressed(View view) {
        // use this to save selected language
        int checkedLanguageId = languageRadioGroup.getCheckedRadioButtonId();
        if (checkedLanguageId == R.id.englishButton) {
            data.setLanguage(user, "english");
        } else if (checkedLanguageId == R.id.frenchButton) {
            data.setLanguage(user, "french");
        }

        screenLoader.loadMainMenu();
    }
}
