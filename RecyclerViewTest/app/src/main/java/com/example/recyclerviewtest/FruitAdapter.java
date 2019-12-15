package com.example.recyclerviewtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        Button bt1,bt2;

        public ViewHolder(View view) {
            super(view);
            int n=1;
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            bt1 = (Button) view.findViewById(R.id.bt1);
            bt2 = (Button) view.findViewById(R.id.bt2);

        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);



        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            int n = 1;
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                //Toast.makeText(v.getContext(), "你点击了水果信息：" + fruit.getName(), Toast.LENGTH_SHORT).show();
                if(n==1){
                    holder.bt1.setVisibility(View.VISIBLE);
                    holder.bt2.setVisibility(View.VISIBLE);
                    n--;
                }
                else{
                    holder.bt1.setVisibility(View.GONE);
                    holder.bt2.setVisibility(View.GONE);
                    n++;
                }


            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            int n = 1;
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                //Toast.makeText(v.getContext(), "你点击了水果图片： " + fruit.getName(), Toast.LENGTH_SHORT).show();
                if(n==1){
                    holder.bt1.setVisibility(View.VISIBLE);
                    holder.bt2.setVisibility(View.VISIBLE);
                    n--;
                }
                else{
                    holder.bt1.setVisibility(View.GONE);
                    holder.bt2.setVisibility(View.GONE);
                    n++;
                }
            }
        });
        holder.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int position = holder.getAdapterPosition();
                final Fruit fruit = mFruitList.get(position);

                final EditText editText = new EditText(view.getContext());
                editText.setText(fruit.getName());
                editText.setMaxLines(3);
                Toast.makeText(view.getContext(), "你点击了编辑： " + fruit.getName(), Toast.LENGTH_SHORT).show();

                //编辑对话框
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("编辑");
                dialog.setView(editText);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            fruit.setName(editText.getText().toString());
                            notifyItemChanged(position);
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();

            }
        });
        holder.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "你点击了删除： " + fruit.getName(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("确定删除吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.bt1.setVisibility(View.GONE);
                        holder.bt2.setVisibility(View.GONE);
                        mFruitList.remove(fruit);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                dialog.show();


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}