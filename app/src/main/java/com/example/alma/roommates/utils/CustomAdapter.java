package com.example.alma.roommates.utils;

/**
 * Created by alma on 16/04/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.alma.roommates.R;
import com.parse.ParseObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static List<ParseObject> parseList;
    String[] mDataSet;

     public static class ViewHolder extends RecyclerView.ViewHolder {

         private  CircleImageView mProfilePicNormal;
         private  TextView mTextView;

        public ViewHolder(View view) {
            super(view);


//            this.mView = view;
//            this.mTextView = (TextView) view.findViewById(R.id.item_text);
//            this.mEditText = (EditText) view.findViewById(R.id.item_edittext);
//            this.mNormalLayout = (LinearLayout) view.findViewById(R.id.shoplist_item_layout);
//            this.mProfilePicNormal = (CircleImageView) view.findViewById(R.id.profile_image_shopping);
//            this.saveBtn = (ImageView) view.findViewById(R.id.shoplist_item_save_btn);
//            this.editBtn = (ImageView) view.findViewById(R.id.shoplist_item_edit_btn);
//            this.vs = (ViewSwitcher) view.findViewById(R.id.shoplist_item_view_switcher);

            // Define click listener for the ViewHolder's View.
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MyApp", "Element " + getPosition() + " clicked.");
                }
            });
        }

         public String toString() {
             return super.toString() + " '" + this.mTextView.getText();
         }
        public TextView getTextView() {
            return mTextView;
        }
    }

    public CustomAdapter(String[] mDataSet) {
        this.mDataSet = mDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shoplist_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d("MyApp", "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
