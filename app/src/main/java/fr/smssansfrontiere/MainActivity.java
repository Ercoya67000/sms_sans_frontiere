package fr.smssansfrontiere;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

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

        TextView statut = new TextView(this);
        statut.setText("\nVersion 0.2");
        statut.setTextSize(18);
        statut.setGravity(Gravity.CENTER);

        Button boutonTester = new Button(this);
        boutonTester.setText("Tester");

        boutonTester.setOnClickListener(v ->
            statut.setText("\nBonjour Erwan !")
        );

        page.addView(titre);
        page.addView(statut);
        page.addView(boutonTester);

        setContentView(page);
    }
}
