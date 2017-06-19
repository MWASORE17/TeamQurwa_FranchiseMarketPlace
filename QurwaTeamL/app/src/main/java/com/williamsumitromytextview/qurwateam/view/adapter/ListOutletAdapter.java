package com.williamsumitromytextview.qurwateam.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.entity.Outlet;

import java.util.List;

/**
 * Created by william on 28/05/2017.
 */

public class ListOutletAdapter extends RecyclerView.Adapter<ListOutletAdapter.EventHolder> {
    private List<Outlet> outletList;
    private LayoutInflater inflater;
    Context context;
    public ListOutletAdapter(List<Outlet> outletList, Context c){
        this.inflater = LayoutInflater.from(c);
        this.outletList = outletList;
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_outletlist, parent, false);
        context = parent.getContext();
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Outlet daftarOutlet = outletList.get(position);
        holder.namaFranchisee.setText(daftarOutlet.getNamaPTFranchisee());
        holder.alamatFranchisee.setText(daftarOutlet.getAlamatFranchisee());
        holder.contactFranchisee.setText(daftarOutlet.getContactFranchisee());
        holder.ownerFranchisee.setText(daftarOutlet.getOwner_Franchisee());
        holder.tahunberdiriFranchisee.setText(daftarOutlet.getTahun_berdiri_Franchisee());
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        private TextView namaFranchisee, alamatFranchisee, contactFranchisee, ownerFranchisee, tahunberdiriFranchisee;
        private View Container;

        public EventHolder(View itemView) {
            super(itemView);
            namaFranchisee = (TextView) itemView.findViewById(R.id.homedetailfranchisedaftaroutletNamaFranchisee);
            alamatFranchisee = (TextView) itemView.findViewById(R.id.homedetailfranchisedaftaroutletAlamatFranchisee);
            contactFranchisee = (TextView) itemView.findViewById(R.id.homedetailfranchisedaftaroutletContactFranchisee);
            ownerFranchisee = (TextView) itemView.findViewById(R.id.homedetailfranchisedaftaroutletOwner);
            tahunberdiriFranchisee = (TextView) itemView.findViewById(R.id.homedetailfranchisedaftaroutletTahunBerdiri);
            Container = itemView.findViewById(R.id.homedetailfranchisedaftaroutletlayout);
        }
    }
}
