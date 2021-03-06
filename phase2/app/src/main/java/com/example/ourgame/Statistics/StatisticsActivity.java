package com.example.ourgame.Statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourgame.Languages.LanguageFactory;
import com.example.ourgame.Languages.Language;
import com.example.ourgame.R;
import com.example.ourgame.Utilities.ScreenLoader;
import com.example.ourgame.Utilities.DataWriter;
import com.example.ourgame.Utilities.WriteData;

/**
 * An activity class for the Statistics screen, which shows the user their statistics for the games
 * they have played
 */
public class StatisticsActivity extends AppCompatActivity {

    WriteData dataWriter;
    ScreenLoader screenLoader;

    TextView pointsText;
    TextView playtimeText;
    TextView rankingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        pointsText = findViewById(R.id.pointsValue);
        playtimeText = findViewById(R.id.playtimeValue);
        rankingText = findViewById(R.id.rankingValue);

        dataWriter = new DataWriter(this);
        screenLoader = new ScreenLoader(this);

        String user = dataWriter.getCurrentUser();
        TextView usernameText = findViewById(R.id.usernameText);
        usernameText.setText(user);

        setLanguage();

        ConstraintLayout constraintLayout = findViewById(R.id.statisticsLayout);
        constraintLayout.setBackgroundResource(dataWriter.getThemeData());

        setUpNavigationButtons();
        displayStatistics();
    }

    private void setLanguage() {
        TextView title = findViewById(R.id.titleText);
        TextView points = findViewById(R.id.pointsText);
        TextView playtime = findViewById(R.id.playtimeText);
        TextView rank = findViewById(R.id.rankingText);
        Button mainMenu = findViewById(R.id.mainMenuButton);
        Button continueButton = findViewById(R.id.continueButton);
        Button backButton = findViewById(R.id.backButton);
        ImageView characterImage = findViewById(R.id.characterImage);

        LanguageFactory text = new LanguageFactory(dataWriter.getLanguage(), this);
        Language language = text.getTextSetter();

        continueButton.setText(language.getContinue());
        title.setText(language.statistics());
        points.setText(language.statPoints());
        playtime.setText(language.statPlaytime());
        rank.setText(language.statRank());
        mainMenu.setText(language.mainMenu());
        backButton.setText(language.back());
        characterImage.setImageResource(dataWriter.getCharacterData());
    }

    private void displayStatistics() {
        String str = Integer.toString(dataWriter.getPoints());
        pointsText.setText(str);
        String str2 = dataWriter.getPlayTime() + " secs.";
        playtimeText.setText(str2);
        rankingText.setText(dataWriter.getRanking());
    }

    private void setUpNavigationButtons() {
        // if the statistics were shown after a game, show the continue and main menu buttons,
        // otherwise, the statistics were shown after the main menu, so we only need
        // the back button that takes us back there
        boolean afterGame = getIntent().getBooleanExtra("after game", false);

        if (!afterGame) {
            Button continueButton = findViewById(R.id.continueButton);
            Button mainMenuButton = findViewById(R.id.mainMenuButton);
            mainMenuButton.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
        } else {
            Button backButton = findViewById(R.id.backButton);
            backButton.setVisibility(View.GONE);
        }
    }


    /**
     * Method to bring the user to the next game after they've viewed their stats
     *
     * @param view the button object that was tapped
     */
    public void onContinuePressed(View view) {
        screenLoader.loadNextGame();
    }

    public void onMainMenuPressed(View view) {
        screenLoader.loadMainMenu();
    }

    public void onBackPressed(View view) {
        screenLoader.loadMainMenu();
    }
}
