package de.tum.in.vfit.ticketaid.activities;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.touchpad.Gesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import de.tum.in.vfit.ticketaid.R;
import de.tum.in.vfit.ticketaid.adapters.CardAdapter;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class PurchaseActivity extends Activity {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private CardScrollView mCardScroller;

    private CardScrollAdapter mAdapter;

    private GestureDetector mGestureDetector;

    /**
     * "Hello World!" {@link View} generated by {@link #buildView()}.
     */
    private View mView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //mView = buildView();

        mAdapter = new CardAdapter(createCards(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        mGestureDetector = createGestureDetector(this);

        setContentView(mCardScroller);

//        // Handle the TAP event.
//        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Plays disallowed sound to indicate that TAP actions are not supported.
//                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                am.playSoundEffect(Sounds.DISALLOWED);
//            }
//        });
//
    }

    /**
     * Create list of API demo cards.
     */
    private List<CardBuilder> createCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        cards.add(0, new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.step_1));
        cards.add(1, new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.step_2));
        cards.add(2, new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.step_3));
        cards.add(3, new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.step_4));
        cards.add(4, new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.get_ticket));
        return cards;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);

        //Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.SWIPE_DOWN && mCardScroller.getSelectedItemPosition() == 4) {
                    Intent intent = new Intent(PurchaseActivity.this, WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        return gestureDetector;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
}
