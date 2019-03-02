package com.example.mobin.wallboo;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Image in recyclerview
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {


    public static ImageViewHolder inflate(ViewGroup parent) {

        /// commented out dynamically adding of image
   /*     LinearLayout lt = parent.findViewById(R.Layout.item_image);

        ViewGroup.LayoutParams params = lt.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 100;
        params.width = 100;
        lt.setLayoutParams(params);
*/

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image , parent, false);

        return new ImageViewHolder(view);
    }

    public TextView mTextTitle;

    public ImageViewHolder(View view) {
        super(view);
        mTextTitle = view.findViewById(R.id.title);
    }

    private void bind(String title) {
        mTextTitle.setText(title);
    }
}
