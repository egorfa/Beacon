package test.com.ibeacontest.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.com.ibeacontest.R;

/**
 * Created by Egor on 22.05.2015.
 */
public class ViewHolderImgTitleDescription extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private ImageView tvImage;

    public ViewHolderImgTitleDescription(View itemView, View.OnClickListener listener) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.item_title);
        tvImage = (ImageView) itemView.findViewById(R.id.item_image);
        tvDescription = (TextView) itemView.findViewById(R.id.item_description);

        itemView.setOnClickListener(listener);
    }

    public void setDescription(String description){ tvDescription.setText(description);}

    public void setTitle(String Name){
        tvTitle.setText(Name);
    }

    public void setImage(int id){
        tvImage.setImageResource(id);
    }
}
