package com.spark.sqlitedemo_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Spark
 * @package com.spark.sqlitedemo_android
 * @fileName MyAdapter
 * @date 2018/9/6
 * @describe
 */
public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<StatBean> datas;
    public MyAdapter(Context context, List<StatBean> datas) {
        inflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.stat_item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.itemNameTv.setText(datas.get(i).getName());
        myViewHolder.itemPhoneTv.setText(datas.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTv;
        private TextView itemPhoneTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTv = (TextView)itemView.findViewById(R.id.itemNameTv);
            itemPhoneTv = (TextView)itemView.findViewById(R.id.itemPhoneTv);
        }
    }
}
