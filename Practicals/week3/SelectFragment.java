package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectFragment extends Fragment
{
    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;
    private StructureData structures;
    private Structure selectedStructure = null;
    public Structure getSelectedStructure()
    {
        return selectedStructure;
    }
    public SelectFragment()
    {

    }
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        this.structures = StructureData.get();
    }
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflator.inflate(R.layout.fragment_select,container,false);

        recycleView = (RecyclerView) view.findViewById(R.id.selectRecycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new SelectAdapter();
        recycleView.setAdapter(adapter);
        return view;
    }
    private class SelectViewHolder extends RecyclerView.ViewHolder
    {
        private Structure structure = null;
        private ImageView image;
        private TextView label;

        public SelectViewHolder(LayoutInflater inflator, ViewGroup view)
        {
            super(inflator.inflate(R.layout.list_selection,view,false));

            image=(ImageView)itemView.findViewById(R.id.structureImage);
            label = (TextView)itemView.findViewById(R.id.structureLabel);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                   selectedStructure = structure;

                }
            });
        }
        public void bind(Structure structure)
        {
            this.structure = structure;
            image.setImageResource(structure.getDrawableId());
            label.setText(structure.getLabel());
        }

    }
    private class SelectAdapter extends RecyclerView.Adapter<SelectViewHolder>
    {
        @NonNull
        @Override
        public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SelectViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectViewHolder holder, int position)
        {
            holder.bind(structures.get(position));
        }

        @Override
        public int getItemCount() {
            return structures.size();
        }
    }

}

