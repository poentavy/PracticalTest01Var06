package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private EditText number1EditText;
    private EditText number2EditText;
    private EditText number3EditText;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private Button playButton;
    private Button computeButton;
    private Random random;
    private int totalScore = 0; // Variabila pentru scorul total

    private static final String TAG = "PracticalTest01Var06";
    private static final String[] POSSIBLE_VALUES = {"1", "2", "3", "*"};

    private static final String SCORE_KEY = "score_key";

    private static final int REQUEST_CODE_SECONDARY_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);

        // Inițializarea elementelor UI
        number1EditText = findViewById(R.id.number1);
        number2EditText = findViewById(R.id.number2);
        number3EditText = findViewById(R.id.number3);
        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        playButton = findViewById(R.id.play_button);
        computeButton = findViewById(R.id.compute);
        random = new Random();

        // Setarea listener-ului pentru butonul "Play"
        playButton.setOnClickListener(v -> {
            // Generare numere aleatoare din {1, 2, 3, *} și suprascriere doar dacă checkbox-ul NU este bifat
            if (!checkbox1.isChecked()) {
                number1EditText.setText(getRandomValue());
            }
            if (!checkbox2.isChecked()) {
                number2EditText.setText(getRandomValue());
            }
            if (!checkbox3.isChecked()) {
                number3EditText.setText(getRandomValue());
            }

            // Afișarea valorilor în Toast și Logcat
            String result = "Numere generate: " +
                    number1EditText.getText().toString() + ", " +
                    number2EditText.getText().toString() + ", " +
                    number3EditText.getText().toString();
            Toast.makeText(PracticalTest01Var06MainActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, result);
        });

        computeButton.setOnClickListener(v -> {
            // Crearea intenției pentru a lansa activitatea secundară
            Intent intent = new Intent(PracticalTest01Var06MainActivity.this, PracticalTest01Var06SecondaryActivity.class);
            intent.putExtra("numbers", new String[]{
                    number1EditText.getText().toString(),
                    number2EditText.getText().toString(),
                    number3EditText.getText().toString()
            });

            // Calcularea numărului de checkbox-uri bifate
            int checkboxCount = 0;
            if (checkbox1.isChecked()) checkboxCount++;
            if (checkbox2.isChecked()) checkboxCount++;
            if (checkbox3.isChecked()) checkboxCount++;
            intent.putExtra("checkboxCount", checkboxCount);

            // Lansarea activității cu REQUEST_CODE_SECONDARY_ACTIVITY
            startActivityForResult(intent, REQUEST_CODE_SECONDARY_ACTIVITY);
        });
    }

    // Metodă pentru a returna un număr aleatoriu din setul {1, 2, 3, *}
    private String getRandomValue() {
        return POSSIBLE_VALUES[random.nextInt(POSSIBLE_VALUES.length)];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SECONDARY_ACTIVITY && resultCode == RESULT_OK && data != null) {
            int score = data.getIntExtra("score", 0);
            totalScore += score; // Actualizare scor total

            // Afișare scor total în Toast
            Toast.makeText(this, "Total Score: " + totalScore, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Total Score: " + totalScore);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salvarea scorului total
        outState.putInt(SCORE_KEY, totalScore);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restaurarea scorului total
        totalScore = savedInstanceState.getInt(SCORE_KEY, 0);
        Log.d(TAG, "Scorul restaurat: " + totalScore);
    }

}
