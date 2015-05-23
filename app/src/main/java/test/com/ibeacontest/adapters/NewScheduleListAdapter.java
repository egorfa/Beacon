package test.com.ibeacontest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Calendar;

import test.com.ibeacontest.R;
import test.com.ibeacontest.activities.CabinetScheduleActivity;
import test.com.ibeacontest.activities.InfoDescriptionActivity;
import test.com.ibeacontest.adapters.view_holders.ViewHolderWithListView;
import test.com.ibeacontest.model.DayWithSubjects;

/**
 * Created by Egor on 23.05.2015.
 */
public abstract class NewScheduleListAdapter extends RecyclerView.Adapter<ViewHolderWithListView> {

    private final Context mContext;
    private ArrayList<DayWithSubjects> days;
    private final View.OnClickListener listener;

    public NewScheduleListAdapter(Context context, ArrayList<DayWithSubjects> subjects, View.OnClickListener listener) {
        mContext = context;
        this.days = subjects;
        this.listener = listener;
    }

    @Override
    public ViewHolderWithListView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_days, viewGroup, false);
        return new ViewHolderWithListView(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolderWithListView subjectViewHolder, final int position) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        //TODO
        subjectViewHolder.setTitle(days.get(position).getDay());
        subjectViewHolder.setNumbers("  -  " + days.get(position).getSubjects().size() + " пары");
        subjectViewHolder.setAdapter(new ListViewInRecyclerAdapter(mContext, days.get(position).getSubjects(), listener));
        subjectViewHolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int listPosition, long id) {
                click(position, listPosition);

            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    protected abstract void click(int position, int listPosition);
}

