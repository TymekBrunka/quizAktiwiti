package tymek.app.quiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import android.util.Log;

class pytanie{
    private static String textA = "Czy jesteś";
    public static Boolean odpa = true;
    public TextView pytanieE;

    public Boolean zadaj(){
        pytanieE.setText(textA);
        return odpa;
    }

    public pytanie(String txt, Boolean o, TextView tw){
        textA = txt;
        odpa = o;
        pytanieE = tw;
    }
}

public class MainActivity extends AppCompatActivity {

    int pop = 0;
    int niepop = 0;
    int iq = 0;
    Boolean odp = true;

    public final int REQUEST_CODE_CHEAT = 0;

    TextView pytanielement;
    pytanie[] pytania;

    Random r = new Random();

    private boolean mCzyPodpowiedzWyswietlona; //wartość zwrócona z widoku podpowiedzi

    int pyt = 0;
    int podpo = 3;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATS = "index";
    private static final String EXTRA_CHEATS = "com.tymek.app.quiz2.amogus";

//    private boolean mPytanieJestPrawda;

//    protected void onActivityResult(int requestCode, int resultCode, Intent data){};

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "Wywołanie metody: onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, pyt);
        savedInstanceState.putInt(KEY_CHEATS, podpo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if(data==null) {
                return;
            }
            mCzyPodpowiedzWyswietlona = MainActivity2.jakaBylaOdpowiedz(data);
            podpo = MainActivity2.ilePodpo(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"Wróciłem (ma onActivityResoult)");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"Witam (ma onCreate)");

        if (savedInstanceState!=null){
            pyt = savedInstanceState.getInt(KEY_INDEX, 0);
            podpo = savedInstanceState.getInt(KEY_CHEATS, 3);
        }

        pytanielement = findViewById(R.id.pytanie);

        pytania = new pytanie[]{
                new pytanie("Czy istniejesz?", true, pytanielement),
                new pytanie("Czy można kichać z zamkniętymi oczyma?", true, pytanielement),
                new pytanie("2 + 2 = 5?", false, pytanielement),
                new pytanie("Czy zadałem ci pytanie?", true, pytanielement),
                new pytanie("x^2 - a = (x+a)(x-a)+1?", false, pytanielement),
                new pytanie("Czy ziemia jest płaska?", false, pytanielement)
        };

        findViewById(R.id.podpowiedzmact).setOnClickListener(v -> {
            boolean pytanieJestPrawda = pytania[pyt].odpa;
            Intent intent = MainActivity2.newIntent(MainActivity.this, pytanieJestPrawda, podpo);
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });

        findViewById(R.id.tak).setOnClickListener(v -> {
            Update(true);
        });

        findViewById(R.id.nie).setOnClickListener(v -> {
            Update(false);
        });

        findViewById(R.id.nast).setOnClickListener(v -> {
            pyt++;
            if (pyt > pytania.length - 1){
                pyt = 0;
            }
            odp = pytania[pyt].zadaj();
            mCzyPodpowiedzWyswietlona = false;
        });
    }

    public void Update(Boolean id){
        if(mCzyPodpowiedzWyswietlona) {
            String kom = String.valueOf(R.string.oceniajacy_toast);
            Toast.makeText(MainActivity.this, kom, Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Kiepskie zakończenie (odblokowano 1 z 3 zakończeń) (*dźwięki mikrofali*CzyPodpowiedzWyświetlona");
        } else {
            if (id == odp){
                pop++;
                iq = (pop * 15) - (niepop * 5);
                String kom = "Poprawna odpowiedź. Masz " + iq + " IQ.";
                Toast.makeText(MainActivity.this, kom, Toast.LENGTH_SHORT).show();
            } else {
                niepop++;
                iq = (pop * 15) - (niepop * 5);
                String kom = "Niepoprawna odpowiedź. Masz " + iq + " IQ.";
                Toast.makeText(MainActivity.this, kom, Toast.LENGTH_SHORT).show();
            }
            TextView iqelement = findViewById(R.id.iq);
            String text = "IQ: " + iq;
            iqelement.setText(text);
            TextView popodp = findViewById(R.id.popodp);
            text = "Poprawne odpowiedzi: " + pop;
            popodp.setText(text);
            TextView niepopodp = findViewById(R.id.niepopodp);
            text = "Niepoprawne odpowiedzi: " + niepop;
            niepopodp.setText(text);

            if (iq < 0){
                findViewById(R.id.cl).setBackgroundResource(R.drawable.smooth_brain);
            } else if (iq > 100){
                findViewById(R.id.cl).setBackgroundResource(R.drawable.big_brain);
            } else {
                findViewById(R.id.cl).setBackgroundResource(R.drawable.ic_launcher_background);
            }
        }
    }
}