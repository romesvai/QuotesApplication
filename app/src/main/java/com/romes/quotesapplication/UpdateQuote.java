package com.romes.quotesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.net.Inet4Address;

public class UpdateQuote extends AppCompatActivity {
        private EditText mQuote;
        private EditText mAuthor;
        private EditText mDescription;
        public static final String upQuote="com.romes.android.EXTRA_QUOTE1";
        public static final String upAuthor ="com.romes.android.EXTRA_AUTHOR1";
        public static final String upDescription="com.romes.android.EXTRA_DESC1";
        public static final String upID ="com.romes.android.EXTRA_id1";
        public int selectedQuoteID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.update_quote);
            mQuote = (EditText)findViewById(R.id.quote_EditText);
            mAuthor = (EditText)findViewById(R.id.author_editText);
            mDescription =(EditText)findViewById(R.id.description_editText);
            Intent intent = getIntent();
            String popQuote = intent.getStringExtra(MainActivity.popQuote);
            String popAuthor = intent.getStringExtra(MainActivity.popAuthor);
            String popDescription = intent.getStringExtra(MainActivity.popDescription);
            selectedQuoteID = intent.getIntExtra(MainActivity.id,0);
            mQuote.setText(popQuote);
            mAuthor.setText(popAuthor);
            mDescription.setText(popDescription);
        }

        public void updateQuote(View view) {
            String quote = mQuote.getText().toString();
            String author = mAuthor.getText().toString();
            String description = mDescription.getText().toString();
            Intent replyIntent1 = new Intent();
            replyIntent1.putExtra(upQuote,quote);
            replyIntent1.putExtra(upAuthor,author);
            replyIntent1.putExtra(upDescription,description);
            replyIntent1.putExtra(upID,selectedQuoteID);
            setResult(RESULT_OK,replyIntent1);
            finish();
        }


}

