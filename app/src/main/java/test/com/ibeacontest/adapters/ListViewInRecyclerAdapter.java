package test.com.ibeacontest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import test.com.ibeacontest.R;
import test.com.ibeacontest.model.Subject;

/**
 * Created by Egor on 23.05.2015.
 */
public class ListViewInRecyclerAdapter  extends ArrayAdapter<Subject>{

    private Context mContext;
    private ArrayList<Subject> mSubjects;
    private View.OnClickListener listener;

    public ListViewInRecyclerAdapter(Context context, ArrayList<Subject> subjects, View.OnClickListener listener){
        super(context, R.layout.item_list_days, subjects);
        mContext = context;
        mSubjects = subjects;
        this.listener = listener;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_subject,parent, false);
        }

        TextView subjectTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView subjectDescription = (TextView) convertView.findViewById(R.id.tv_description);
        ImageView subjectImageView = (ImageView) convertView.findViewById(R.id.img);


        subjectTitle.setText(mSubjects.get(position).getName());
        subjectDescription.setText(mSubjects.get(position).getTime());
        if((mSubjects.get(position).getName().equals("Теория автоматов - Л")) || (mSubjects.get(position).getName().equals("Теория автоматов - С"))){
            subjectImageView.setImageResource(R.drawable.icon_lesson_automata_theory);
        }
        if((mSubjects.get(position).getName().equals("Операционные системы - Л")) || (mSubjects.get(position).getName().equals("Операционные системы - Лаб"))){
            subjectImageView.setImageResource(R.drawable.icon_lesson_os);
        }
        if((mSubjects.get(position).getName().equals("Вычислительная математика - Л")) || (mSubjects.get(position).getName().equals("Вычислительная математика - С"))){
            subjectImageView.setImageResource(R.drawable.icon_lesson_computional_math);
        }
        if((mSubjects.get(position).getName().equals("Дискретная математика - Л")) || (mSubjects.get(position).getName().equals("Дискретная математика - С"))) {
            subjectImageView.setImageResource(R.drawable.icon_lesson_descrete_math);
        }
        if((mSubjects.get(position).getName().equals("Сети и телекоммуникации - Л")) || (mSubjects.get(position).getName().equals("Сети и телекоммуникации - Лаб"))) {
            subjectImageView.setImageResource(R.drawable.icon_lesson_networks);
        }
        if((mSubjects.get(position).getName().equals("Электротехника, электроника и схемотехника - Л")) || (mSubjects.get(position).getName().equals("Электротехника, электроника и схемотехника - С")) || (mSubjects.get(position).getName().equals("Электротехника, электроника и схемотехника - Лаб"))){
            subjectImageView.setImageResource(R.drawable.icon_lesson_electronics);
        }

        return convertView;

    }

}
