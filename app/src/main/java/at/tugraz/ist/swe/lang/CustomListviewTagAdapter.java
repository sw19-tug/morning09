package at.tugraz.ist.swe.lang;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import at.tugraz.ist.swe.lang.R;

public class CustomListviewTagAdapter<S> extends ArrayAdapter<String> {
    Vocabulary vocabulary;



    private Context context;
    private ArrayList<String> data = new ArrayList<String>();
    private int objID;

    public CustomListviewTagAdapter(Context context, ArrayList<String> dataItem, int objectID) {
        super(context, R.layout.custom_listview_layout, dataItem);
        this.data = dataItem;
        this.context = context;
        this.objID = objectID;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_listview_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.tvLvTxt);
            viewHolder.button = (Button) convertView
                    .findViewById(R.id.btnRemove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String temp = getItem(position);
        viewHolder.text.setText(temp);

        Button deleteBtn = (Button)convertView.findViewById(R.id.btnRemove);
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                vocabulary.removeTag(objID,temp);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView text;
        Button button;
    }
}