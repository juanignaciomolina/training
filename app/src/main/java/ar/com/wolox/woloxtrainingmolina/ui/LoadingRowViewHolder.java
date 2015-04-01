package ar.com.wolox.woloxtrainingmolina.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import ar.com.wolox.woloxtrainingmolina.R;

public class LoadingRowViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar mProgressBar;

    public LoadingRowViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        this.mProgressBar = (ProgressBar) itemLayoutView.findViewById(R.id.loading_indicator);
    }

}