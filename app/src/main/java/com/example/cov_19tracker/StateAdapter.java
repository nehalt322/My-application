package com.example.cov_19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.razerdp.widget.animatedpieview.AnimatedPieView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.cov_19tracker.stateactivity.STATE_ACTIVE;
import static com.example.cov_19tracker.stateactivity.STATE_CONFIRMED;
import static com.example.cov_19tracker.stateactivity.STATE_DECEASED;
import static com.example.cov_19tracker.stateactivity.STATE_LAST_UPDATE;
import static com.example.cov_19tracker.stateactivity.STATE_NAME;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_CONFIRMED;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_DECEASED;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_RECOVERED;
import static com.example.cov_19tracker.stateactivity.STATE_RECOVERED;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.stateViewHolder> {
    public Context mcontext;
    public ArrayList<state_items> mstatelist;
    private ArrayList<state_items> mstateListFull;
    private OnItemClickListener mListner;


    public StateAdapter(Context context, ArrayList<state_items> statelist) {
        mcontext = context;
        mstatelist = statelist;
        mstateListFull = new ArrayList<>(statelist);
    }

    @NonNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.state_list_layout, parent, false);
        return new stateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stateViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state_items clickedItem = mstatelist.get(position);
                Intent stateIntent = new Intent(mcontext, StateAnalysis.class);


                stateIntent.putExtra(STATE_NAME, clickedItem.getMstate());
                stateIntent.putExtra(STATE_CONFIRMED, clickedItem.getMconfirmed());
                stateIntent.putExtra(STATE_ACTIVE, clickedItem.getMactive());
                stateIntent.putExtra(STATE_DECEASED, clickedItem.getMdeceased());
                stateIntent.putExtra(STATE_NEW_CONFIRMED, clickedItem.getMnewconfirmed());
                stateIntent.putExtra(STATE_NEW_RECOVERED, clickedItem.getMnewrecovered());
                stateIntent.putExtra(STATE_NEW_DECEASED, clickedItem.getMnewdeceased());
                stateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getMdate());
                stateIntent.putExtra(STATE_RECOVERED, clickedItem.getMrecovered());

                mcontext.startActivity(stateIntent);

            }
        });
        state_items currentitems = mstatelist.get(position);

    /*    String Active = currentitems.getMactive();
        String Confirmed = currentitems.getMconfirmed();
        String Recovered = currentitems.getMrecovered();
        String Deceased = currentitems.getMdeceased();
        String New_confirmed = currentitems.getMnewconfirmed();

        String New_recovered = currentitems.getMnewrecovered();
        String New_deceased = currentitems.getMnewdeceased(); */
        String New_state = currentitems.getMstate();

    /*    holder.stateconfirmed.setText(Active);
        holder.stateactive.setText(Confirmed);
        holder.staterecovered.setText(Recovered);
        holder.statedeceased.setText(Deceased);
        holder.newstateconfirmed.setText("+" + New_confirmed);
        holder.newrecovered.setText("+" + New_recovered);
        holder.newdeceased.setText("+" + New_deceased);   */
        holder.stateview.setText(New_state);

    }

    @Override
    public int getItemCount() {
        return mstatelist.size();
    }

   // @Override
   /* public Filter getFilter() {
        return statefilter;
    }
     private Filter statefilter = new Filter() {
         @Override
         protected FilterResults performFiltering(CharSequence charSequence) {
             List<state_items> filteredList = new ArrayList<>();
             if (charSequence.toString().isEmpty()){
                 filteredList.addAll(mstateListFull);
             }
             else{
                 for (state_items items: mstateListFull) {
                     if (items.toString().toLowerCase().contains(charSequence.toString().toLowerCase())){
                         filteredList.add(items);
                     }
                     
                 }
             }

             FilterResults results = new FilterResults();
             results.values = filteredList;
             return results;
         }

         @Override
         protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

             mstatelist.clear();
             mstatelist.addAll((Collection<? extends state_items>) filterResults.values);
             notifyDataSetChanged();
         }

     };*/

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        mListner = listner;
    }





    public class stateViewHolder extends RecyclerView.ViewHolder {

        public TextView stateview;
        public TextView stateconfirmed;
        public TextView stateactive;
        public TextView staterecovered;
        public TextView statedeceased;
        public TextView newstateconfirmed;
        public TextView newrecovered;
        public TextView newdeceased;
        public TextView lastupdate;
        public AnimatedPieView State_piechart;

        public stateViewHolder(View itemView) {
            super(itemView);
            stateview = itemView.findViewById(R.id.textView_state);
            stateconfirmed = itemView.findViewById(R.id.tvstate_confirmed);
            stateactive = itemView.findViewById(R.id.tvstate_active);
            staterecovered = itemView.findViewById(R.id.tvstate_recovered);
            statedeceased = itemView.findViewById(R.id.tvstate_deceased);
            newstateconfirmed = itemView.findViewById(R.id.tvstate_newconfirmed);
            newrecovered = itemView.findViewById(R.id.tvstate_newrecovered);
            newdeceased = itemView.findViewById(R.id.tvstate_newdeceased);
            lastupdate = itemView.findViewById(R.id.tvstatedate);
            State_piechart = itemView.findViewById(R.id.State_PieChart);


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
