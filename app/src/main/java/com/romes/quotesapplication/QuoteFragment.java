package com.romes.quotesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuoteFragment extends Fragment {
    Activity context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private ArrayList<Quote> mQuoteData;
    private QuoteAdapter mAdapter;
    private static int REQUEST_CODE = 1;
    private static int upREQUEST_CODE = 2;
    private static int userREQUEST_CODE = 3;
    private static int logInREQUEST_CODE = 4;
    private TextView mQuoteTextHere;
    private QuoteVIewModel mQuoteViewModel;
    private Button editButton;
    private Quote updateQuote;
    private int selectedQuoteID;
    public static final String popQuote = "com.romes.example.Quote";
    public static final String popAuthor = "com.romes.example.Author";
    public static final String popDescription = "com.romes.example.Description";
    public static final String id = "com.romes.example.id";
    public static final String loginUsername="com.romes.example.USERNAME";
    private UserViewModel mUserViewModel;
    //    public String logInUsername;
//    public String logInPassword;
    private SharedPreferences pref;
    private BottomNavigationView mMainBottomNavView;
    private Runnable mOnActivityResultTask;

    public QuoteFragment() {
        MainActivity main = (MainActivity)getActivity();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuoteFragment newInstance(String param1, String param2) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v=getView();
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        mRecyclerView = v.findViewById(R.id.recyclerView);
//        editButton = v.findViewById(R.id.edit_button);
        mMainBottomNavView = v.findViewById(R.id.bottom_nav);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mQuoteData = new ArrayList<>();
        mAdapter = new QuoteAdapter(context, mQuoteData);
        mRecyclerView.setAdapter(mAdapter);
        mQuoteViewModel = ViewModelProviders.of(this).get(QuoteVIewModel.class);
        mQuoteViewModel.getAllQuotes().observe(getViewLifecycleOwner(), new Observer<List<Quote>>() {
            //  @Override
            public void onChanged(List<Quote> quotes) {
                mAdapter.setQuotes(quotes);
            }
        });
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //  mUserViewModel.update(user);
            }
        });
        pref = context.getSharedPreferences("UserLogin", Context.MODE_PRIVATE);


        //    mQuoteTextHere.setVisibility(View.INVISIBLE);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mQuoteData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Quote quote = mAdapter.getQuoteAtPosition(position);
                selectedQuoteID = quote.getId();
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(context, "Deleting quote", Toast.LENGTH_SHORT).show();
                    mQuoteViewModel.deleteQuote(quote);
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                } else if (direction == ItemTouchHelper.LEFT) {
                    Intent updateIntent = new Intent(context, UpdateQuote.class);
                    updateIntent.putExtra(popQuote, quote.getQuote());
                    updateIntent.putExtra(popAuthor, quote.getAuthor());
                    updateIntent.putExtra(popDescription, quote.getDescription());
                    updateIntent.putExtra(id, selectedQuoteID);
                    startActivityForResult(updateIntent, upREQUEST_CODE);


                }
            }

        });

        helper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    private void getData() {
        Intent intent = new Intent(context, AddQuoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        return inflater.inflate(R.layout.fragment_quote, container, false);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == upREQUEST_CODE) {
                    if (resultCode == Activity.RESULT_OK) {
                        String quote = data.getStringExtra(UpdateQuote.upQuote);
                        String author = data.getStringExtra(UpdateQuote.upAuthor);
                        String description = data.getStringExtra(UpdateQuote.upDescription);
                        int selectedQuoteID = data.getIntExtra(UpdateQuote.upID, 0);
                        Quote updateQuote = new Quote(selectedQuoteID, quote, author, description);
//                if(updateQuote!=null){
                        mQuoteViewModel.updateQuote(updateQuote);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                if (requestCode == REQUEST_CODE) {
                    if (resultCode == Activity.RESULT_OK) {
                        String quote = data.getStringExtra(AddQuoteActivity.Quote);
                        String author = data.getStringExtra(AddQuoteActivity.Author);
                        String description = data.getStringExtra(AddQuoteActivity.Description);
                        Quote newQuote = new Quote(quote, author, description);
                        mQuoteViewModel.insert(newQuote);
                        mAdapter.notifyDataSetChanged();
                    }
                }

        };
    }

