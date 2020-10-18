package com.romes.quotesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder>{
    private List<Quote> mQuoteData;
    private Context mContext;
    private final LayoutInflater mInflater;
    public static final String popQuote = "com.romes.example.Quote";
    public static final String popAuthor = "com.romes.example.Author";
    public static final String popDescription="com.romes.example.Description";
    public static final String id = "com.romes.example.id";
    public static final int upRequestCode = 2;
   // public TextView mUserText;
     QuoteAdapter(Context context,ArrayList<Quote> quoteData){
         mInflater = LayoutInflater.from(context);
        this.mQuoteData=quoteData;
        this.mContext=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.quotes_itemlist,parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Quote currentQuote = mQuoteData.get(position);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quote prevQuote = mQuoteData.get(position);
                int selectedQuoteID = prevQuote.getId();
                Intent updateIntent = new Intent(mContext,UpdateQuote.class);
                   updateIntent.putExtra(popQuote,prevQuote.getQuote());
                    updateIntent.putExtra(popAuthor,prevQuote.getAuthor());
                   updateIntent.putExtra(popDescription,prevQuote.getDescription());
                  updateIntent.putExtra(id,selectedQuoteID);
                ((Activity) mContext).startActivityForResult(updateIntent,upRequestCode);
               
            }
        });

        holder.bindTo(currentQuote);

    }
    void setQuotes(List<Quote> quotes){
        mQuoteData = quotes;
        notifyDataSetChanged();

    }
    public Quote getQuoteAtPosition (int position) {
        return mQuoteData.get(position);
    }

    @Override
    public int getItemCount() {
        return mQuoteData.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View

            .OnClickListener{
        public TextView mQuoteText;
        public TextView mAuthorText;
        public TextView mDescriptionText;
        public Button editButton;
        public TextView mUserText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuoteText = itemView.findViewById(R.id.Quote);
            mAuthorText = itemView.findViewById(R.id.Author);
            mDescriptionText = itemView.findViewById(R.id.Description);
            editButton = itemView.findViewById(R.id.edit_button);


        }
        void bindTo(Quote currentQuote){
            mQuoteText.setText("Quote: " +currentQuote.getQuote());
            mQuoteText.setBackgroundColor(Color.BLACK);
            mAuthorText.setText("Author: "+currentQuote.getAuthor());
            mAuthorText.setBackgroundColor(Color.BLACK);
            mDescriptionText.setText(currentQuote.getDescription());
            mDescriptionText.setBackgroundColor(Color.BLACK);


        }


        @Override
        public void onClick(View view) {
//
        }

    }




}
