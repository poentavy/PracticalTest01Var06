package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button okButton;
    private static final String TAG = "PracticalTest01Var06Secondary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_second_activity);

        resultTextView = findViewById(R.id.resultTextView);
        okButton = findViewById(R.id.okButton);

        // Preluarea datelor din intenție
        Intent intent = getIntent();
        int gain = 0; // Variabila pentru scorul calculat

        if (intent != null && intent.hasExtra("numbers") && intent.hasExtra("checkboxCount")) {
            String[] numbers = intent.getStringArrayExtra("numbers");
            int checkboxCount = intent.getIntExtra("checkboxCount", 0);

            // Verificarea numerelor și afișarea rezultatului
            if (areNumbersEqual(numbers)) {
                gain = calculateGain(checkboxCount);
                resultTextView.setText("Gained " + gain);
//                Toast.makeText(this, "Gained " + gain, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Gained " + gain);
            } else {
                resultTextView.setText("No gain");
                Log.d(TAG, "No gain");
            }
        }

        // Setarea rezultatului și închiderea activității la apăsarea butonului OK
        int finalGain = gain;
        okButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("score", finalGain);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private boolean areNumbersEqual(String[] numbers) {
        String first = numbers[0].equals("*") ? numbers[1] : numbers[0];
        for (String number : numbers) {
            if (!number.equals(first) && !number.equals("*")) {
                Log.d(TAG, "Mismatch found: " + number + " != " + first);
                return false;
            }
        }
        return true;
    }

    private int calculateGain(int checkboxCount) {
        switch (checkboxCount) {
            case 0:
                return 100;
            case 1:
                return 50;
            case 2:
                return 10;
            default:
                return 0;
        }
    }
}
