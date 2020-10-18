package com.romes.quotesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddQuoteActivity extends AppCompatActivity {
    private EditText mQuote;
    private EditText mAuthor;
    private EditText mDescription;
    public static final String Quote="com.romes.android.EXTRA_QUOTE";
    public static final String Author ="com.romes.android.EXTRA_AUTHOR";
    public static final String Description="com.romes.android.EXTRA_DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        mQuote = (EditText)findViewById(R.id.quote_EditText);
        mAuthor = (EditText)findViewById(R.id.author_editText);
        mDescription =(EditText)findViewById(R.id.description_editText);
    }

    public void add_quote(View view) {
        String quote = mQuote.getText().toString();
        String author = mAuthor.getText().toString();
        String description = mDescription.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(Quote,quote);
        replyIntent.putExtra(Author,author);
        replyIntent.putExtra(Description,description);
        setResult(RESULT_OK,replyIntent);
        finish();
    }
}