package test.com.ibeacontest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

import test.com.ibeacontest.R;
import test.com.ibeacontest.adapters.view_holders.ViewHolderImgTitleDescription;
import test.com.ibeacontest.model.Subject;

/**
 * Created by Egor on 23.05.2015.
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ViewHolderImgTitleDescription> {

    private final Context mContext;
    private ArrayList<Subject> subjects;
    private final View.OnClickListener listener;

    public ScheduleListAdapter(Context context, ArrayList<Subject> subjects, View.OnClickListener listener){
        mContext = context;
        this.subjects = subjects;
        this.listener = listener;
    }

    @Override
    public ViewHolderImgTitleDescription onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolderImgTitleDescription(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolderImgTitleDescription subjectViewHolder, int position) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        //TODO
        subjectViewHolder.setTitle(subjects.get(position).getName());
        subjectViewHolder.setDescription(subjects.get(position).getTime());
        if((subjects.get(position).getName().equals("Теория автоматов - Л")) || (subjects.get(position).getName().equals("Теория автоматов - С"))){
            subjectViewHolder.setImage(R.drawable.icon_lesson_automata_theory);
        }
        if((subjects.get(position).getName().equals("Операционные системы - Л")) || (subjects.get(position).getName().equals("Операционные системы - Лаб"))){
            subjectViewHolder.setImage(R.drawable.icon_lesson_os);
        }
        if((subjects.get(position).getName().equals("Вычислительная математика - Л")) || (subjects.get(position).getName().equals("Вычислительная математика - С"))){
            subjectViewHolder.setImage(R.drawable.icon_lesson_computional_math);
        }
        if((subjects.get(position).getName().equals("Дискретная математика - Л")) || (subjects.get(position).getName().equals("Дискретная математика - С"))) {
            subjectViewHolder.setImage(R.drawable.icon_lesson_descrete_math);
        }
        if((subjects.get(position).getName().equals("Сети и телекоммуникации - Л")) || (subjects.get(position).getName().equals("Сети и телекоммуникации - Лаб"))) {
            subjectViewHolder.setImage(R.drawable.icon_lesson_networks);
        }
        if((subjects.get(position).getName().equals("Электротехника, электроника и схемотехника - Л")) || (subjects.get(position).getName().equals("Электротехника, электроника и схемотехника - С")) || (subjects.get(position).getName().equals("Электротехника, электроника и схемотехника - Лаб"))){
            subjectViewHolder.setImage(R.drawable.icon_lesson_electronics);
        }

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
