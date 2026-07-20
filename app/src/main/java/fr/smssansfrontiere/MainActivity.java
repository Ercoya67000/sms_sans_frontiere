package fr.smssansfrontiere;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int DEMANDE_PERMISSION_SMS = 100;

    private TextView numeroAffiche;
    private TextView messageAffiche;

    private String numeroEnAttente;
    private String messageEnAttente;

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
            numeroAffiche.setText(
                "\nNuméro reçu :\n" + numero
            );
        }

        if (message != null) {
            messageAffiche.setText(
                "\nMessage reçu :\n" + message
            );
        }

        if (numero != null && message != null) {
            numeroEnAttente = numero;
            messageEnAttente = message;

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {

                envoyerSms();

            } else {
                requestPermissions(
                    new String[]{
                        Manifest.permission.SEND_SMS
                    },
                    DEMANDE_PERMISSION_SMS
                );
            }
        }
    }

    private void envoyerSms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(
                numeroEnAttente,
                null,
                messageEnAttente,
                null,
                null
            );

            messageAffiche.setText(
                "\nSMS envoyé :\n" + messageEnAttente
            );

            Toast.makeText(
                this,
                "SMS envoyé",
                Toast.LENGTH_LONG
            ).show();

        } catch (Exception erreur) {
            messageAffiche.setText(
                "\nErreur d'envoi :\n"
                    + erreur.getMessage()
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        );

        if (requestCode == DEMANDE_PERMISSION_SMS
                && grantResults.length > 0
                && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

            envoyerSms();

        } else {
            messageAffiche.setText(
                "\nAutorisation d'envoi refusée"
            );
        }
    }
}
