package com.example.demouiframework;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_background);
        LinearLayout ll = (LinearLayout) findViewById(R.id.mainLayout);

        CardBackground background = new CardBackground(ll);

        CardBuilder largeHeadingBuilder = new CardBuilder(this, ll);
        largeHeadingBuilder.addBasicLabel("hello", "wagyu");

        CardBuilder dropdownBuilder = new CardBuilder(this, ll);
        dropdownBuilder.addBasicLabel("test", "Text");
        largeHeadingBuilder.addSubheadingDropdown("dropdown", dropdownBuilder.getContainer());

        CardBuilder subDropdownBuilder = new CardBuilder(this, ll);
        subDropdownBuilder.addBasicLabel("uguu", "wafu");
        dropdownBuilder.addSubsubheadingDropdown("gao", subDropdownBuilder.getContainer());

        background.addContainer(largeHeadingBuilder.getContainer());
        background.finalise();
    }

    // TODO options on dropdown
    //  - create new card
    //  - expand current card

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
