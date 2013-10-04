package com.example.demouiframework;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aki on 28/09/13.
 */


// builds cards up with simple API
// provides access to internal container to add to a background

class CardBuilder {

    LinearLayout rootView;
    Context cxt;
    CardContainer containerView;

    final int LARGE_IMAGE_HEADING = 1;

    public CardBuilder(Context cxt, LinearLayout rootView) {
        this.cxt = cxt;
        this.rootView = rootView;

        containerView = new CardContainer(cxt, rootView);

        LayoutTransition anim = new LayoutTransition();
        anim.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        anim.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        anim.setDuration(100);

        containerView.containerView.setLayoutTransition(anim);
    }

    void addLargeImageHeading(String heading, String url) {
        CardElement element = new CardElement(cxt, rootView);
        element.createLargeImageHeading(heading, url);
        containerView.addElement(element);
    }

    void addBasicLabel(String heading, String text) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createBasicLabel(heading, text);
        containerView.addElement(element);
    }

    void addLabelLink(String heading, String text) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createLabelLink(heading, text);
        containerView.addElement(element);
    }

    void addLabelImageLink(String heading, String text) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createLabelImageLink(heading, text);
        containerView.addElement(element);
    }

    void addSubheadingDropdown(String heading, CardContainer dropdownContainer) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createSubheadingDropdown(heading);
        element.setDropdownContent(dropdownContainer);
        containerView.addElement(element);
    }

    void addSubsubheadingDropdown(String heading, CardContainer dropdownContainer) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createSubheadingDropdown(heading);
        element.setDropdownContent(dropdownContainer);
        containerView.addElement(element);
    }

    void addLabelWebView(String content) {
        CardElement element = new CardElement(cxt, containerView.containerView);
        element.createLabelWebView(content);
        containerView.addElement(element);
    }

    void addListLabelImageLink(String heading, Intent intent) {
        CardElement element = new CardElement((cxt), containerView.containerView);
        element.createListLabelImageLink(heading, intent);
        containerView.addElement(element);
    }

    CardContainer getContainer() {
        return containerView;
    }
}

class CardElement  {
    int id;
    ViewGroup parent;
    ViewGroup element;
    LayoutInflater vi;
    CardContainer dropdownContent;
    boolean toggled;
    Context cxt;


    public CardElement(Context cxt, ViewGroup parent) {
        this.cxt = cxt;
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

    void createLabelWebView(String text) {
        LinearLayout container = (LinearLayout) vi.inflate(R.layout.card_label_basic, null);
        WebView textView = (WebView) container.findViewById(R.id.labelWebView);
        textView.loadData(text, "text/html", null);
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

    void createListLabelImageLink(String text, Intent intent) {
        RelativeLayout container = (RelativeLayout) vi.inflate(R.layout.card_list_label_image_link, null);
        TextView heading = (TextView) container.findViewById(R.id.heading);
        ImageView link = (ImageView) container.findViewById(R.id.link);
        heading.setText(text);
        setLinkClickAction(link, intent);

        element = container;
    }


    ImageView setImageClickAction(final ImageView image) {
        image.setHapticFeedbackEnabled(true);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                //onImagePress();
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
                //onLinkPress();
            }
        });
        return link;
    }

    ImageView setLinkClickAction(final ImageView link, final Intent intent) {
        link.setHapticFeedbackEnabled(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                cxt.startActivity(intent);
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
        ImageView dropdownImage = (ImageView) element.findViewById(R.id.dropdown);
        dropdownImage.setImageResource(R.drawable.ic_find_previous_holo_light);

        int index = parent.indexOfChild(element);
        dropdownContent.finalise();
        parent.addView(dropdownContent.containerView, index + 1);
    }

    void revertDropdown() {
        ImageView dropdownImage = (ImageView) element.findViewById(R.id.dropdown);
        dropdownImage.setImageResource(R.drawable.ic_find_next_holo_light);

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

    void onRemoveView() {

    }

//
//    abstract void onLinkPress();
//    abstract void onImagePress();


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

