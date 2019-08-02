package com.example.learnworderapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.LinkedList;

public class WordsAdapter extends
        RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private LinkedList<String> wordsInDictionary;
    private LinkedList<String> translatesInDictionary;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    @Override
    public int getItemCount(){
        return wordsInDictionary.size();
    }

    @Override
    public WordsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.words_cards, parent, false);
        return new ViewHolder(cv);
    }

    public WordsAdapter(LinkedList<String> words, LinkedList<String> translates){
        this.wordsInDictionary = words;
        this.translatesInDictionary = translates;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Определить представление для каждого элемента данных
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;

        TextView textView = (TextView)cardView.findViewById(R.id.wordInDictionary1);
        textView.setText(wordsInDictionary.get(position));

        TextView textView2 = (TextView)cardView.findViewById(R.id.translateInDictionary1);
        textView2.setText(translatesInDictionary.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}
