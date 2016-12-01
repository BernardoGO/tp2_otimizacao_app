package com.tproteiri.tp2otimizacao.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tproteiri.tp2otimizacao.OnSelectLinkListener;
import com.tproteiri.tp2otimizacao.R;
import com.tproteiri.tp2otimizacao.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josue on 11/30/16.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    private List<City> mDataset;
    private OnSelectLinkListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        LinearLayout mRemove;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
            mRemove = (LinearLayout) v.findViewById(R.id.remove);
        }
    }

    public SelectAdapter(City city, List<City> myDataset, OnSelectLinkListener listener) {
        mDataset = new ArrayList<>();
        for (City c : myDataset) {
            if (!c.getLocality().equals(city.getLocality()) && !city.hasLink(c))
                mDataset.add(c);
        }
        this.listener = listener;
    }

    @Override
    public SelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_view, parent, false);

        return new SelectAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectAdapter.ViewHolder holder, final int position) {
        holder.mRemove.setVisibility(View.GONE);
        holder.mTextView.setText(mDataset.get(position).getLocality());

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelectLink(mDataset.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
