package com.example.cov_19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razerdp.widget.animatedpieview.AnimatedPieView;

import java.util.ArrayList;

import static com.example.cov_19tracker.DistrictActivity.DISTRICT_ACTIVE;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_CONFIRMED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_DECEASED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NAME;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_CONFIRMED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_DECEASED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_RECOVERED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_RECOVERED;


public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.districtViewHolder> {
    public Context mcontext;
    public ArrayList<District_Items> mdistrictlist;
    private OnItemClickListener mListner;


    public DistrictAdapter(Context context, ArrayList<District_Items> districtlist) {
        mcontext = context;
        mdistrictlist = districtlist;
    }

    @NonNull
    @Override
    public districtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.district_list_layout, parent, false);
        return new districtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull districtViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District_Items clickedItem = mdistrictlist.get(position);
                Intent districtIntent = new Intent(mcontext, DistrictAnalysis.class);


                districtIntent.putExtra(DISTRICT_NAME, clickedItem.getMdistrict());
                districtIntent.putExtra(DISTRICT_CONFIRMED, clickedItem.getMconfirmed());
                districtIntent.putExtra(DISTRICT_ACTIVE, clickedItem.getMactive());
                districtIntent.putExtra(DISTRICT_DECEASED, clickedItem.getMdeceased());
                districtIntent.putExtra(DISTRICT_NEW_CONFIRMED, clickedItem.getMnewconfirmed());
                districtIntent.putExtra(DISTRICT_NEW_RECOVERED, clickedItem.getMnewrecovered());
                districtIntent.putExtra(DISTRICT_NEW_DECEASED, clickedItem.getMnewdeceased());
                //   stateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getLastupdate());
                districtIntent.putExtra(DISTRICT_RECOVERED, clickedItem.getMrecovered());

                mcontext.startActivity(districtIntent);

            }
        });
        District_Items currentitems = mdistrictlist.get(position);

    /*    String Active = currentitems.getMactive();
        String Confirmed = currentitems.getMconfirmed();
        String Recovered = currentitems.getMrecovered();
        String Deceased = currentitems.getMdeceased();
        String New_confirmed = currentitems.getMnewconfirmed();

        String New_recovered = currentitems.getMnewrecovered();
        String New_deceased = currentitems.getMnewdeceased(); */
        String New_district = currentitems.getMdistrict();

    /*    holder.stateconfirmed.setText(Active);
        holder.stateactive.setText(Confirmed);
        holder.staterecovered.setText(Recovered);
        holder.statedeceased.setText(Deceased);
        holder.newstateconfirmed.setText("+" + New_confirmed);
        holder.newrecovered.setText("+" + New_recovered);
        holder.newdeceased.setText("+" + New_deceased);   */
        holder.districtview.setText(New_district);

    }

    @Override
    public int getItemCount() {
        return mdistrictlist.size();
    }

    public void updateList(ArrayList<District_Items> mdistrictListFull) {
        mdistrictlist = new ArrayList<>();
        mdistrictlist.addAll(mdistrictListFull);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        mListner = listner;
    }



    public class districtViewHolder extends RecyclerView.ViewHolder {

        public TextView districtview;
        public TextView districtconfirmed;
        public TextView districtactive;
        public TextView districtrecovered;
        public TextView districtdeceased;
        public TextView newdistrictconfirmed;
        public TextView newdistrictrecovered;
        public TextView newdistrictdeceased;
        AnimatedPieView District_pieView;

        public districtViewHolder(View itemView) {
            super(itemView);
            districtview = itemView.findViewById(R.id.textView_district);
            districtconfirmed = itemView.findViewById(R.id.tvdistrict_confirmed);
            districtactive = itemView.findViewById(R.id.tvdistrict_active);
            districtrecovered = itemView.findViewById(R.id.tvdistrict_recovered);
            districtdeceased = itemView.findViewById(R.id.tvdistrict_deceased);
            newdistrictconfirmed = itemView.findViewById(R.id.tvdistrict_newconfirmed);
            newdistrictrecovered = itemView.findViewById(R.id.tvdistrict_newrecovered);
            newdistrictdeceased = itemView.findViewById(R.id.tvdistrict_newdeceased);
            District_pieView = itemView.findViewById(R.id.District_PieChart);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListner.OnItemClick(position);
                        }
                    }
                }
            });
        }





    }
}
