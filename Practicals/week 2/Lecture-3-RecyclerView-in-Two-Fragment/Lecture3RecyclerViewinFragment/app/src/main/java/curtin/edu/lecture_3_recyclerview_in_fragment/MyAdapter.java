package curtin.edu.lecture_3_recyclerview_in_fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    ArrayList<MyData> data;
    FragmentManager frag;

    public MyAdapter(ArrayList<MyData> data, FragmentManager frag) {
        this.data = data;
        this.frag = frag;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FragmentB fragmentB = new FragmentB(data.get(position).getName());
        holder.itemName.setText(data.get(position).getName());
        holder.itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.beginTransaction().replace(R.id.fragmentBContainer, fragmentB).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
