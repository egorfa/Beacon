package test.com.ibeacontest.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import test.com.ibeacontest.R;
import test.com.ibeacontest.adapters.ListViewInRecyclerAdapter;
import test.com.ibeacontest.utils.Utility;

/**
 * Created by Egor on 23.05.2015.
 */
public class ViewHolderWithListView  extends RecyclerView.ViewHolder  {

    private TextView tvDay;
    private TextView tvNumbers;
    private ListView lvSubjects;
    private View.OnClickListener listener;


    public ViewHolderWithListView(View itemView, View.OnClickListener listener) {
        super(itemView);

        tvDay = (TextView) itemView.findViewById(R.id.tv_day);
        tvNumbers = (TextView) itemView.findViewById(R.id.tv_numbers);
        lvSubjects = (ListView) itemView.findViewById(R.id.listView);
        this.listener = listener;
    }

    public void setTitle(String Name){
        tvDay.setText(Name);
    }

    public void setNumbers(String numbers){tvNumbers.setText(numbers);}

    public void setAdapter(ListViewInRecyclerAdapter adapter){ lvSubjects.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lvSubjects);}

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener)
    {
        lvSubjects.setOnItemClickListener(listener);
    }

}
