package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MapFragment extends Fragment
{
    private SelectFragment selector=null;
    private MapData map;
    private RecyclerView recycleView; //RecyclerView is basically a container that allows inserting large datasets and can be viewed through scrolling (save space)
    private RecyclerView.Adapter adapter; //Adapter creates ViewHolders and assigns data to them as needed
    //view hodler tiny controller object that allows 1 visible list row, updates their roll with new data when user scrolls
    //note: view holder and adapter are abstract so subclasses needs to be created for them
    public void setSelector(SelectFragment selector)
    {
        this.selector = selector;
    }
    public MapFragment()
    {

    }
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        this.map = MapData.get();
    }
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflator.inflate(R.layout.fragment_map,container,false);//reads the XML and creates the UI based on it then returns the root view object

        recycleView = (RecyclerView) view.findViewById(R.id.mapRecycleView);//assigns the recycler view by ID
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(),MapData.HEIGHT,GridLayoutManager.HORIZONTAL,false));// specify how it should be laid out
        adapter = new MapAdapter(); //creates adapter object
        recycleView.setAdapter(adapter); //set the recycleView to adapter
        return view;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private MapElement element;
        private ImageView northWestImage;
        private ImageView northEastImage;
        private ImageView southWestImage;
        private ImageView southEastImage;
        private ImageView structureImage;

        public MapViewHolder(LayoutInflater inflator, ViewGroup view)
        {
            super(inflator.inflate(R.layout.grid_cell,view,false));//there is a separate XML file for list rows in this case "grid_cell.xml"
            //we use a LayoutInflator object to convert the XML to a View object tree, after that the superclass then makes it available as "itemView".
            northWestImage=(ImageView)itemView.findViewById(R.id.northWest); //Grab UI element reference(s)
            northEastImage=(ImageView)itemView.findViewById(R.id.northEast); //these are pictures/images
            southWestImage=(ImageView)itemView.findViewById(R.id.southWest);
            southEastImage=(ImageView)itemView.findViewById(R.id.southEast);
            structureImage=(ImageView)itemView.findViewById(R.id.structure);
            int size = view.getMeasuredHeight()/MapData.HEIGHT+1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Structure newStructure = selector.getSelectedStructure();
                    if(newStructure != null && element.isBuildable())
                    {
                        element.setStructure(StructureData.get().get(0));
                        element.setStructure(newStructure);
                        adapter.notifyItemChanged(getAdapterPosition());
                    }
                }
            });
        }
        public void bind(MapElement element)//this method will be called by your adapter
        {
            //setImageResuouce is for images
            //if it is texts we can use example: textView.setText(data.(StringVariable))
            this.element = element;
            northWestImage.setImageResource(element.getNorthWest());
            northEastImage.setImageResource(element.getNorthEast());
            southWestImage.setImageResource(element.getSouthWest());
            southEastImage.setImageResource(element.getSouthEast());

            Structure structure = element.getStructure();
            if(structure == null)
            {
                structureImage.setVisibility(View.INVISIBLE);
            }
            else
            {
                structureImage.setVisibility(View.VISIBLE);
                structureImage.setImageResource(structure.getDrawableId());
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MapAdapter extends RecyclerView.Adapter<MapViewHolder>
    {
        @NonNull
        @Override
        public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) //view holder subclass
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());//layout inflator is needed to create view holder.
            //layout inflator belongs to the activity that's why we need to call the fragment's getActivity() method.
            return new MapViewHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull MapViewHolder holder, int position)//this is called when RecyclerView needs to use a view holder to display a different data element
        {
            int row = position % MapData.HEIGHT;
            int col = position / MapData.HEIGHT;
            holder.bind(map.get(row,col));//row and col identifies which element to display
        }
        @Override
        public int getItemCount()//export the total data size for RecyclerView
        {
            return MapData.WIDTH*MapData.HEIGHT;
        }
    }
}
