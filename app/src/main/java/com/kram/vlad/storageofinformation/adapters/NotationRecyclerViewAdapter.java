package com.kram.vlad.storageofinformation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.NotationListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vlad on 05.11.17.
 */

public class NotationRecyclerViewAdapter extends RecyclerView.Adapter<NotationRecyclerViewAdapter.CardViewHolder>{

    public static final String TAG = NotationRecyclerViewAdapter.class.getSimpleName();
    private LogInModel mLogInModel;

    private Context mContext;
    private int mStartRange = 1;
    private int mFinishRange = 5;
    private NotationsDownloadedCallback mNotationsDownloadedCallback;
    private NotationListPresenter mNotationListPresenter;

    public NotationRecyclerViewAdapter(Context context, LogInModel logInModel, int startRange, int finishRange,
                                       NotationsDownloadedCallback notationsDownloadedCallback,
                                       NotationListPresenter presenter) {
        mStartRange = startRange;
        mFinishRange = finishRange;
        mLogInModel = logInModel;
        mNotationsDownloadedCallback = notationsDownloadedCallback;
        mNotationListPresenter = presenter;
        mContext = context;
    }

    @Override
    public NotationRecyclerViewAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(parent
                .getContext())
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if(position + 1 >= Utils.sNotations.size()){
            mStartRange = mFinishRange;
            mFinishRange = mStartRange + 10;

            Log.d(TAG, String.valueOf(mStartRange));
            Log.d(TAG, String.valueOf(mFinishRange));
            mNotationListPresenter.downloadNotations(mContext, mLogInModel,
                    mNotationsDownloadedCallback, mStartRange, mFinishRange);
        }

        holder.mNotations.setText(Utils.sNotations.get(position));
    }

    @Override
    public int getItemCount() {
        return Utils.sNotations.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notation) TextView mNotations;

        CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
