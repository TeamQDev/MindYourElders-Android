package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Document;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class DocumentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Document> documentList;
    LayoutInflater lf;

    ViewHolder holder;

    public DocumentAdapter(Context context, ArrayList<Document> documentList) {
        this.context=context;
        this.documentList=documentList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return documentList.size();
    }

    @Override
    public Object getItem(int position) {
        return documentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
           convertView=lf.inflate(R.layout.row_care_plan,parent,false);
           holder=new ViewHolder();
            holder.txtDocHeader= (TextView) convertView.findViewById(R.id.txtDocHeader);
           holder.txtDocTime= (TextView) convertView.findViewById(R.id.txtDocTime);
            holder.imgDocType= (ImageView) convertView.findViewById(R.id.imgDocType);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtDocHeader.setText(documentList.get(position).getName());
        holder.txtDocTime.setText(documentList.get(position).getDate());
        holder.imgDocType.setImageResource(documentList.get(position).getImage());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddDocumentActivity.class);
                i.putExtra("GoTo","View");
                i.putExtra("DocumentObject",documentList.get(position));
                context.startActivity(i);
            }
        });


     /*convertView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
      });*/
        return convertView;


    }



    public class ViewHolder
    {
        TextView txtDocHeader, txtDocDesc,txtDocTime;
        ImageView imgDocType,imgForword;
    }
}
