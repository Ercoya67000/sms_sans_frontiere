package fr.smssansfrontiere;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout page = new LinearLayout(this);

        page.setOrientation(LinearLayout.VERTICAL);

        page.setGravity(Gravity.CENTER);

        TextView titre = new TextView(this);

        titre.setText("SMS Sans Frontière");

        titre.setTextSize(28);

        TextView version = new TextView(this);

        version.setText("\nVersion 0.1");

        version.setTextSize(18);

        page.addView(titre);

        page.addView(version);

        setContentView(page);

    }

}
