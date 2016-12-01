package com.tproteiri.tp2otimizacao.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.tproteiri.tp2otimizacao.R;
import com.tproteiri.tp2otimizacao.models.City;
import com.tproteiri.tp2otimizacao.utils.CacheMemory;

import java.util.List;

/**
 * Created by josue on 11/30/16.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private List<City> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        LinearLayout mRemove;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
            mRemove = (LinearLayout) v.findViewById(R.id.remove);
        }
    }

    public CitiesAdapter(List<City> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position).getLocality());
        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheMemory.removeCity(position);
                CitiesAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


