package tymek.app.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    private void setAnswerShowResult(boolean czyOdpowiedzPokazana, int podpo) {
        if (podpo > 0) {
            podpo--;
            Log.d(TAG, "Ogień w dziurze!1! (ma2 setAnswerShowResult)");
            Intent data = new Intent();
            data.putExtra(EXTRA_POKAZ_ODPOWIEDZ, czyOdpowiedzPokazana);
            data.putExtra(EXTRA_CHEATS, podpo);
            setResult(RESULT_OK, data);
        }
    }

    private static final String EXTRA_POKAZ_ODPOWIEDZ = "com.tymek.app.quiz2.pokaz_odpowiedz";
    private static final String EXTRA_CHEATS = "com.tymek.app.quiz2.amogus";
    private static final String TAG = "QuizActivity";

    private boolean mPytanieJestPrawda;

    int podpo = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG,"Właśnie zdradzasz MainActivity z MainActivity2 (ma2 onCreate)");
        mPytanieJestPrawda = getIntent().getBooleanExtra("com.tymek.app.quiz2.answer_is_true", false);
        podpo = getIntent().getIntExtra(EXTRA_CHEATS, 3);

        findViewById(R.id.button).setOnClickListener(v -> {
            TextView e = findViewById(R.id.textView2);
            setAnswerShowResult(true, podpo);
            if (podpo > 0) {
                if (mPytanieJestPrawda) {
                    e.setText("Odpowiedź jest tak prawdziwa jak 1==1.");
                } else {
                    e.setText("Odpowiedź jest tak prawdziwa jak teoria płaskiej Ziemii.");
                }
            } else {
                e.setText("Mam cię w dupie stary trupie. (nie masz podpowiedzi)");
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean pytanieJestPrawda, int podpo) {
        Intent intent = new Intent(packageContext, MainActivity2.class);
        intent.putExtra("com.tymek.app.quiz2.answer_is_true", pytanieJestPrawda);
        intent.putExtra(EXTRA_CHEATS, podpo);
        Log.d(TAG,"Wskszeszanie MainActivity2 (ma2 newIntent)");
        return intent;
    }

    public static boolean jakaBylaOdpowiedz(Intent result){
        return result.getBooleanExtra(EXTRA_POKAZ_ODPOWIEDZ, false);
    }

    public static int ilePodpo(Intent result){
        return result.getIntExtra(EXTRA_CHEATS, 3);
    }
}