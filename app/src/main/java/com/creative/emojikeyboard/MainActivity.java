package com.creative.emojikeyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class MainActivity extends FragmentActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    EmojiconEditText mEditEmojicon;
    EmojiconTextView mTxtEmojicon;
    CheckBox mCheckBox;
    Button touch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touch = (Button) findViewById(R.id.keyboard11111);
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);
        mEditEmojicon.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTxtEmojicon.setText(s);
            }
        });
        mCheckBox = (CheckBox) findViewById(R.id.use_system_default);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mEditEmojicon.setUseSystemDefault(b);
                mTxtEmojicon.setUseSystemDefault(b);
                setEmojiconFragment(b);
            }
        });
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) mEditEmojicon.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(mEditEmojicon, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        setEmojiconFragment(false);
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }
}