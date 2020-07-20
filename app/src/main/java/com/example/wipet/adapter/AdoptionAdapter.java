package com.example.wipet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.R;
import com.example.wipet.activity.DetailAdoptionActivity;
import com.example.wipet.object.Adoption;

import java.util.ArrayList;

public class AdoptionAdapter extends RecyclerView.Adapter<AdoptionAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Adoption> adoptionArrayList;
    private View view;

    public AdoptionAdapter(Context mContext, ArrayList<Adoption> adoptionArrayList) {
        this.mContext = mContext;
        this.adoptionArrayList = adoptionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_adoption_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Adoption adoption = adoptionArrayList.get(position);

        holder.tvName.setText(adoption.getTitle());
        holder.tvCategory.setText(adoption.getCategory());
        holder.tvKota.setText(adoption.getUser().getKota());
        if (adoption.getPhoto() != null){

            Glide.with(mContext)
                    .load(Api.DIR_ADOPTION_PHOTO + adoption.getPhoto().get(0))
                    .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(R.drawable.ic_cats);
        }
        holder.tvPrice.setText("Rp. "+ GlobalFunc.getFormatCurrency(adoption.getPrice()));
        holder.itemLayout.setOnClickListener(v->{
            Intent intent = new Intent(mContext, DetailAdoptionActivity.class);
            intent.putExtra("adoption", adoption);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return adoptionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView tvName, tvCategory, tvPrice, tvKota;
        private CardView itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_item_adoption);
            tvName = itemView.findViewById(R.id.tv_name_item_adoption);
            tvCategory = itemView.findViewById(R.id.tv_category_item_adoptoin);
            tvPrice = itemView.findViewById(R.id.tv_price_item_adoption);
            tvKota = itemView.findViewById(R.id.tv_kota_item_adoption);
            itemLayout = itemView.findViewById(R.id.item_layout_listadoption);

        }
    }
}
