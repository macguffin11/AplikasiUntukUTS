/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.aplikasiuntukuts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiuntukuts.data.Cheese;
import com.example.aplikasiuntukuts.viewmodel.CheeseViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CheeseAdapter mCheeseAdapter;
    private CheeseViewModel mCheeseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        mCheeseAdapter = new CheeseAdapter(this);
        list.setAdapter(mCheeseAdapter);
        mCheeseViewModel = new ViewModelProvider(this).get(CheeseViewModel.class);
        mCheeseViewModel.selectAll().observe(this, new Observer<List<Cheese>>() {
            @Override
            public void onChanged(List<Cheese> cheeses) {
                mCheeseAdapter.setCheeses(cheeses);
            }
        });
        mCheeseViewModel.count().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mCheeseViewModel.populateInitialData(integer.intValue());
            }
        });
    }

    private static class CheeseAdapter extends RecyclerView.Adapter<CheeseAdapter.ViewHolder> {

        private final LayoutInflater mInflater;
        private List<Cheese> mCheese;

        public CheeseAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (mCheese != null) {
                Cheese current = mCheese.get(position);
                holder.mText.setText(current.name);
            } else {
                // Covers the case of data not being ready yet.
                holder.mText.setText("No Cheese");
            }
        }

        @Override
        public int getItemCount() {
            if (mCheese != null)
                return mCheese.size();
            else return 0;
        }


        void setCheeses(List<Cheese> cheeses) {
            mCheese = cheeses;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mText;

            ViewHolder(View itemView) {
                super(itemView);
                mText = itemView.findViewById(android.R.id.text1);
            }

        }

    }

}
