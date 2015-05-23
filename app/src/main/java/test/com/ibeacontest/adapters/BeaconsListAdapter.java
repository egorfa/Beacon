package test.com.ibeacontest.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import test.com.ibeacontest.R;
import test.com.ibeacontest.adapters.view_holders.ViewHolderImgTitleDescription;
import test.com.ibeacontest.model.Beacon;

/**
 * Created by Egor on 23.05.2015.
 */
public class BeaconsListAdapter  extends RecyclerView.Adapter<ViewHolderImgTitleDescription>  {

    private final Context mContext;
    private ArrayList<Beacon> beacons;
    private final View.OnClickListener listener;

    public BeaconsListAdapter(Context context, ArrayList<Beacon> beacons, View.OnClickListener listener){
        mContext = context;
        this.beacons = beacons;
        this.listener = listener;
    }

    @Override
    public ViewHolderImgTitleDescription onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolderImgTitleDescription(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolderImgTitleDescription beaconViewHolder, int i) {
        beaconViewHolder.setTitle(beacons.get(i).getName());
        beaconViewHolder.setDescription("Аудитория");
        beaconViewHolder.setImage(R.drawable.pin);
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    public void clear(){
        for(int i=0;i<beacons.size();i++) {
            beacons.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void add(Beacon beacon) {
        for(int i=0;i<beacons.size();i++) {
            if (!(beacon.getUUID().equals(beacons.get(i).getUUID())))
                beacons.add(beacon);
            notifyItemInserted(beacons.size());
        }
        if(beacons.size()==0)
            beacons.add(beacon);
            notifyItemInserted(0);
    }

    public Beacon getItem(int position)
    {
        return beacons.get(position);
    }
}
