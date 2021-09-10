package curtin.edu.lab3;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //ADDED-------------------------------------------------
    private SelectFragment selector=null;
    private MapData map;
    private RecyclerView recycleView;
    //^^RecyclerView is basically a container that allows inserting large datasets and can be
    //viewed through scrolling (save space)
    private RecyclerView.Adapter adapter;
    //^^Adapter creates ViewHolders and assigns data to them as needed
    //view holder tiny controller object that allows 1 visible list row, updates their roll
    //with new data when user scrolls
    //note: view holder and adapter are abstract so subclasses needs to be created for them
    public void setSelector(SelectFragment selector)
    {
        this.selector = selector;
    }
    //------------------------------------------------------

    public MapFragment() { //-------------------------------------------------
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {//-----------------------------------
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //ADDED----------------------------------
        this.map = MapData.get();
        //---------------------------------------
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_map, container, false);

        //ADDED-------------------------------------------------------------
        //defining fragments
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        //^^reads the XML and creates the UI based on it then returns the root view object

        //setting up RecyclerView
        //Obtain the RecyclerView UI element
        recycleView = (RecyclerView) view.findViewById(R.id.mapRecycleView);
        //^^assigns the recycler view by ID

        //Specify how it should be laid out
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(),MapData.HEIGHT,
                                                           GridLayoutManager.HORIZONTAL,
                                                false));
        //^^specify how it should be laid out

        //Have your data ready
        //List<MyData> date = ...;

        //Create your adapter
        adapter = new MapAdapter(); //creates adapter object
        //Hook it up
        recycleView.setAdapter(adapter); //set the recycleView to adapter
        return view;
        //------------------------------------------------------------------
    }


    //ADDED-----------------------------------------------------------------

    //MapViewHolder Inner Class
    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private MapElement element;
        private ImageView northWestImage; //Reference to UI element(s)
        private ImageView northEastImage; //Reference to UI element(s)
        private ImageView southWestImage; //Reference to UI element(s)
        private ImageView southEastImage; //Reference to UI element(s)
        private ImageView structureImage; //Reference to UI element(s)

        public MapViewHolder(LayoutInflater li, ViewGroup view)
        {
            super(li.inflate(R.layout.grid_cell,view,false));
            //^^there is a separate XML file for list rows in this case "grid_cell.xml"
            //we use a LayoutInflater object to convert the XML to a View object tree,
            //after that the superclass then makes it available as "itemView".

            //Grab UI element reference(s)
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

        //Called by your adapter
        public void bind(MapElement element)//this method will be called by your adapter
        {
            //setImageResource is for images
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

    //MapAdapter Inner Class
    //It is easier to nest your adapter inside your activity/fragment. Like this.
    private class MapAdapter extends RecyclerView.Adapter<MapViewHolder>
    {
        //About Adapter.onCreateViewHolder()
        @NonNull
        @Override
        public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {//^^view holder subclass

            //we need LayoutInflater to create a ViewHolder.
            LayoutInflater li = LayoutInflater.from(getActivity()); //Fragment method
            //layout inflater belongs to the activity that's why we need to call the fragment's
            //getActivity() method.
            return new MapViewHolder(li, parent);
        }

        //About Adapter.onBindViewHolder()
        //Description: called when RecyclerView needs to use a ViewHolder to display a different
        //             data element,
        //             index identifies which element to display.
        @Override
        public void onBindViewHolder(@NonNull MapViewHolder holder, int position)
        {//this is called when RecyclerView needs to use a view holder to display
         //a different data element

            int row = position % MapData.HEIGHT;
            int col = position / MapData.HEIGHT;
            holder.bind(map.get(row,col));
            //row and col identifies which element to display
        }

        //About Adapter.getItemCount()
        @Override
        public int getItemCount()//export the total data size for RecyclerView
        {
            return MapData.WIDTH*MapData.HEIGHT;
        }
    }
    //----------------------------------------------------------------------
}