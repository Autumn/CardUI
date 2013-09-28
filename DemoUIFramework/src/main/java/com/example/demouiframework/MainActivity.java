package com.example.demouiframework;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_background);
        LinearLayout ll = (LinearLayout) findViewById(R.id.mainLayout);
        CardBackground background = new CardBackground(ll);
        CardContainer container = new CardContainer(this, ll);
        CardElement element = new CardElement(this, container.containerView) {
            @Override
            void onLinkPress() {
                Toast.makeText(getApplicationContext(), "linkPress", Toast.LENGTH_LONG).show();
            }

            @Override
            void onImagePress() {
                Toast.makeText(getApplicationContext(), "ImagePress", Toast.LENGTH_LONG).show();
            }
        };
        CardContainer testSubcontainer = new CardContainer(this, ll);
        CardElement e2 = new CardElement(this, testSubcontainer.containerView) {
            @Override
            void onLinkPress() {}

            @Override
            void onImagePress() {}
        };
        e2.createBasicLabel("test", "text");
        testSubcontainer.addElement(e2);
        element.setDropdownContent(testSubcontainer);
        element.createSubheadingDropdown("Test");
        container.addElement(element);
        background.addContainer(container);
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

    abstract class CardElement  {
        int id;
        ViewGroup parent;
        ViewGroup element;
        LayoutInflater vi;
        CardContainer dropdownContent;
        boolean toggled;

        public CardElement(Context cxt, ViewGroup parent) {
            vi = (LayoutInflater) cxt.getSystemService(cxt.LAYOUT_INFLATER_SERVICE);
            this.parent = parent;
            toggled = false;
        }

        void createLargeImageHeading(String heading, String url) {
            RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_big_image_heading, null);
            TextView headingView = (TextView) container.findViewById(R.id.heading);
            ImageView imageView = (ImageView) container.findViewById(R.id.image);
            setImageClickAction(imageView);
            headingView.setText(heading);
            element = container;
        }

        void createSubheadingDropdown(String heading) {
            RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_subheading_dropdown, null);
            TextView headingView = (TextView) container.findViewById(R.id.heading);
            ImageView dropdown = (ImageView) container.findViewById(R.id.dropdown);
            setDropdownClickAction(dropdown);
            headingView.setText(heading);
            element = container;
        }

        void createSubsubheadingDropdown(String heading) {
            RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_subsubheading_dropdown, null);
            TextView headingView = (TextView) container.findViewById(R.id.heading);
            ImageView dropdown = (ImageView) container.findViewById(R.id.dropdown);
            setDropdownClickAction(dropdown);
            headingView.setText(heading);
            element = container;
        }

        void createBasicLabel(String heading, String text) {
            LinearLayout container = (LinearLayout) vi.inflate(R.layout.card_label_basic, null);
            TextView headingView = (TextView) container.findViewById(R.id.labelHeading);
            TextView textView = (TextView) container.findViewById(R.id.labelText);
            headingView.setText(heading);
            textView.setText(text);
            element = container;
        }

        void createLabelLink(String heading, String text) {
            RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_label_link, null);
            TextView headingView = (TextView) container.findViewById(R.id.heading);
            TextView textView = (TextView) container.findViewById(R.id.text);
            ImageView link = (ImageView) container.findViewById(R.id.link);

            headingView.setText(heading);
            textView.setText(text);
            setLinkClickAction(link);

            element = container;
        }

        void createLabelImageLink(String heading, String text) {
            RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_label_image_link, null);
            TextView headingView = (TextView) container.findViewById(R.id.heading);
            TextView textView = (TextView) container.findViewById(R.id.text);
            ImageView link = (ImageView) container.findViewById(R.id.link);
            ImageView image = (ImageView) container.findViewById(R.id.image);

            headingView.setText(heading);
            textView.setText(text);
            setLinkClickAction(link);
            setImageClickAction(image);

            element = container;
        }

        ImageView setImageClickAction(final ImageView image) {
            image.setHapticFeedbackEnabled(true);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    image.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    onImagePress();
                }
            });
            return image;
        }

        ImageView setLinkClickAction(final ImageView link) {
            link.setHapticFeedbackEnabled(true);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    link.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    onLinkPress();
                }
            });
            return link;
        }

        ImageView setDropdownClickAction(final ImageView dropdown) {
            dropdown.setHapticFeedbackEnabled(true);
            dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dropdown.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    toggleDropdown();
                }
            });
            return dropdown;
        }

        void setDropdownContent(CardContainer container) {
            dropdownContent = container;
        }

        void expandDropdown() {
            int index = parent.indexOfChild(element);
            dropdownContent.finalise();
            parent.addView(dropdownContent.containerView, index + 1);
        }

        void revertDropdown() {
            int index = parent.indexOfChild(element);
            dropdownContent.containerView.removeAllViews();
            parent.removeView(dropdownContent.containerView);
        }

        void flipToggle() {
            toggled = !toggled;
        }

        void toggleDropdown() {
            if (!toggled) {
                expandDropdown();
            } else {
                revertDropdown();
            }
            flipToggle();
        }

        abstract void onLinkPress();
        abstract void onImagePress();
    }

    class CardContainer {
        LayoutInflater vi;
        LinearLayout containerView;
        ArrayList<CardElement> cardContents;

        public CardContainer(Context cxt, LinearLayout rootView) {
            vi = (LayoutInflater) cxt.getSystemService(cxt.LAYOUT_INFLATER_SERVICE);
            containerView = (LinearLayout) vi.inflate(R.layout.card_container, rootView, false);
            cardContents = new ArrayList<CardElement>();
        }

        public void addElement(CardElement element) {
            cardContents.add(element);
        }

        public void finalise() {
            for (CardElement element : cardContents) {
                containerView.addView(element.element);
            }
        }
    }

    class CardBackground {
        ViewGroup rootView;
        ArrayList<CardContainer> cards;

        public CardBackground(LinearLayout rootView) {
            this.rootView = rootView;
            cards = new ArrayList<CardContainer>();
        }

        void addContainer(CardContainer c) {
            cards.add(c);
        }

        void finalise() {
            for (CardContainer card : cards) {
                card.finalise();
                rootView.addView(card.containerView);
            }
        }
    }
}
