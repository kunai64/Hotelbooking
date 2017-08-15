package com.example.hp.hotelbooking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.hotelbooking.data.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kunai on 3/8/17.
 */

public class nearbyAdapter extends RecyclerView.Adapter<nearbyAdapter.MyViewHolder> {
    public List<Result> list;
    final String API_KEY = "AIzaSyD0vwXNlmIu_O4lAyCOB8imnnl3olpaVN8";
    public ImageView image_f;
    public nearbyAdapter(List<Result> list )
    {
        this.list = list;
    }
    private Context context;




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private final List<Result> list;
        public TextView name_f,status_f,rating_f;
        public MyViewHolder(View itemView,List<Result> list) {
            super(itemView);
            name_f = (TextView) itemView.findViewById(R.id.name);
            status_f = (TextView) itemView.findViewById(R.id.status);
            rating_f = (TextView) itemView.findViewById(R.id.rating);
            image_f = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            this.list = list;

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,details.class);
            intent.putExtra("id",list.get(position).getPlaceId());
            intent.putExtra("position",getAdapterPosition());
            intent.putExtra("photoRef",list.get(position).getPhotos().get(0).getPhotoReference());
            intent.putExtra("Address",list.get(position).getVicinity());
            intent.putExtra("Rating",list.get(position).getRating());
            intent.putExtra("name",list.get(position).getName());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content, parent, false);

        MyViewHolder holder = new MyViewHolder(itemView,list);
        context = itemView.getContext();
        return holder;

    }

    @Override
    public void onBindViewHolder( MyViewHolder  holder, int position) {
        Result c = list.get(position);

       TextView name_f,status_f,rating_f;


        try {

            holder.name_f.setText(c.getName());
            if (c.getOpeningHours() != null) {
                if (c.getOpeningHours().getOpenNow())
                    holder.status_f.setText("Open");
                else
                    holder.status_f.setText("Closed");
                holder.rating_f.setText(String.valueOf(c.getRating()));
            }

            int pos = getItemViewType(position);
                if (list.get(pos).getPhotos() != null) {
                        Picasso.with(context).load(
                                "https://maps.googleapis.com/maps/api/place/photo?photoreference=" +  c.getPhotos().get(0).getPhotoReference() + "&maxwidth="

                                        + c.getPhotos().get(0).getWidth() + "&maxheight=" + c.getPhotos().get(0).getHeight() + "&key=" + API_KEY).resize(200, 200).into(image_f);
                    } else {
                    Picasso.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyD0vwXNlmIu_O4lAyCOB8imnnl3olpaVN8").resize(200, 200).into(image_f);
                }

            }catch(Exception e){
                //System.println("Array out of bound");
            }

        //holder.image_f.setImageDrawable((Drawable)c.getPhotos() );
        Log.d("", "onBindViewHolder: binding happened");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //Click event handling


}