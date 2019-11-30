package com.example.ourgame.Menus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageButton [] themeButtons;

    private int [] characterImageIds = {R.drawable.boy, R.drawable.female, R.drawable.girl,
            R.drawable.male, R.drawable.kid};

    private String selectedTheme;
    private int selectedCharacterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageRadioGroup = findViewById(R.id.languageRadioGroup);
        character = findViewById(R.id.characterImage);

        data = new DataWriter(this);
        user = data.getUser();
        screenLoader = new ScreenLoader(this);


        // TODO: find way to improve this
        // (get rid of ThemeBuilder, easily access theme in one line)
        // theme
        ThemeBuilder themeBuilder = new ThemeBuilder(data.getThemeData(user).toUpperCase());
        Theme theme = themeBuilder.getTheme();
        ConstraintLayout constraintLayout = findViewById(R.id.settingsActivityLayout);
        constraintLayout.setBackgroundResource(theme.SettingsActivityLayout());

        // initialize theme buttons and set on click listeners for each of them
        LinearLayout layout = findViewById(R.id.themeButtons);
        int count = layout.getChildCount();
        themeButtons = new ImageButton[count];
        for(int i=0; i<count; i++) {
            themeButtons[i] = (ImageButton)layout.getChildAt(i);
            themeButtons[i].setOnClickListener(this);
        }

        displayCurrentSettings();
    }



    private void displayCurrentSettings() {

        //character
        character.setImageResource(data.getCharacterData(user));


        // language

        // Language language = data.getLanguage(user);
        // RadioButton selectedLanguageButton = findViewById(language.getLanguageButtonId());
        // selectedLanguageButton.setChecked(true);


        // TODO: find way to remove this if/else block
        // loop through radio group and compare tags with getLanguage
        RadioButton selectedLanguageButton;

        if (data.getLanguage(user).equals(getString(R.string.language_french))) {
            selectedLanguageButton = findViewById(R.id.french);
        } else { // if (data.getLanguage(user).equals("english")) {
            selectedLanguageButton = findViewById(R.id.english);
        }
        selectedLanguageButton.setChecked(true);

        // theme
        selectedTheme = data.getThemeData(user);
        displaySelectedTheme();
    }


    // when change character pressed, change the selected character and display it
    public void onChangeCharacterPressed(View view) {

        int nextCharacterId = characterImageIds[0];
        for(int i = 0; i < characterImageIds.length -1; i ++){
            if(selectedCharacterId == characterImageIds[i]){
                nextCharacterId = characterImageIds[i+1];
            }
        }
        selectedCharacterId = nextCharacterId;
        character.setImageResource(selectedCharacterId);
    }

    // only called when a theme button is pressed, display the selected theme
    @Override
    public void onClick(View view) {
        selectedTheme = view.getTag().toString();
        displaySelectedTheme();
    }

    // highlight the selected theme button
    private void displaySelectedTheme(){
        // add grey tint to all theme buttons, and remove any filters from
        // the selected one
        for(ImageButton themeButton : themeButtons){
            String theme = themeButton.getTag().toString();
            if (selectedTheme.equals(theme)) {
                themeButton.clearColorFilter();
            } else {
                addGreyTintFilter(themeButton);
            }
        }
    }

    // add grey tint filter to given image button
    private void addGreyTintFilter(ImageButton button){
        button.setColorFilter(Color.rgb(123, 123, 123),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    // save selected theme, language and character with data saver, and
    // go to main menu
    public void onSaveButtonPressed(View view) {
        // save selected language
        RadioButton checkedLanguageButton =
                findViewById(languageRadioGroup.getCheckedRadioButtonId());
        String selectedLanguage = checkedLanguageButton.getTag().toString();
        data.setLanguage(user, selectedLanguage);

        // save theme
        data.setThemeData(user, selectedTheme);

        // save character
        data.setCharacterData(user, selectedCharacterId);

        // load main menu
        screenLoader.loadMainMenu();
    }
}