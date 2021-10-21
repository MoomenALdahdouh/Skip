package com.example.skip.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skip.R;
import com.example.skip.model.Category;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class CategoryAdapter  extends FirestoreRecyclerAdapter<Category, CategoryAdapter.ViewHolder> {

    private OnItemClickListener listener;


    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position, @NonNull Category model) {
        Picasso.get()
                .load(model.getImage())
                .into(holder.categoryImage);
        Picasso.get()
                .load(model.getBackground())
                .into(holder.categoryBackground);
        holder.categoryTitle.setText(model.getTitle());
        holder.categoryDateSubTitle.setText(model.getSubTitle());
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage,categoryBackground;
        TextView categoryTitle;
        TextView categoryDateSubTitle;
        ConstraintLayout constraintLayoutCategoryItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryItemImage);
            categoryBackground = itemView.findViewById(R.id.categoryItemBackground);
            categoryTitle = itemView.findViewById(R.id.categoryItemTitle);
            categoryDateSubTitle = itemView.findViewById(R.id.categoryItemSubTitle);
            constraintLayoutCategoryItem = itemView.findViewById(R.id.categoryItemLayout);
            constraintLayoutCategoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getAdapterPosition() != RecyclerView.NO_POSITION && listener != null) {
                        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(getAdapterPosition());
                        listener.onItemClick(documentSnapshot, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void onItemSetOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

