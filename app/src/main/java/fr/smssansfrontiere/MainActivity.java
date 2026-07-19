package fr.smssansfrontiere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView numeroAffiche;
    private TextView messageAffiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER);
        page.setPadding(40, 40, 40, 40);

        TextView titre = new TextView(this);
        titre.setText("SMS Sans Frontière");
        titre.setTextSize(28);
        titre.setGravity(Gravity.CENTER);

        numeroAffiche = new TextView(this);
        numeroAffiche.setText("\nAucun numéro reçu");
        numeroAffiche.setTextSize(18);
        numeroAffiche.setGravity(Gravity.CENTER);

        messageAffiche = new TextView(this);
        messageAffiche.setText("\nAucun message reçu");
        messageAffiche.setTextSize(18);
        messageAffiche.setGravity(Gravity.CENTER);

        page.addView(titre);
        page.addView(numeroAffiche);
        page.addView(messageAffiche);

        setContentView(page);

        lireCommande(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        lireCommande(intent);
    }

    private void lireCommande(Intent intent) {
        String numero = intent.getStringExtra("numero");
        String message = intent.getStringExtra("message");

        if (numero != null) {
            numeroAffiche.setText("\nNuméro reçu :\n" + numero);
        }

        if (message != null) {
            messageAffiche.setText("\nMessage reçu :\n" + message);
        }
    }
}
