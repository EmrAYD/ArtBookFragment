package com.emrayd.artfragment.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.emrayd.artfragment.R;
import com.emrayd.artfragment.databinding.RecyclerRowBinding;
import com.emrayd.artfragment.model.Art;
import com.emrayd.artfragment.view.ListFragmentDirections;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ArtHolder> {


    List<Art> arts;

    public ListAdapter(List<Art> arts) {
        this.arts = arts;
    }

    @NonNull
    @Override
    public ArtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding= RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ArtHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return arts.size();
    }

    public class ArtHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;
        public ArtHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(int position){
            binding.rowTextView.setText(arts.get(position).artname);
            System.out.println(arts.get(position).image);

            if (arts.get(position).image!= null){
            Bitmap bitmap= BitmapFactory.decodeByteArray(arts.get(position).image,0,arts.get(position).image.length);
            binding.imageView.setImageBitmap(bitmap);
            }

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListFragmentDirections.ActionListFragmentToDetailsFragment action= ListFragmentDirections.actionListFragmentToDetailsFragment("old");
                    action.setArtId(arts.get(position).id);
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }
    }

}
