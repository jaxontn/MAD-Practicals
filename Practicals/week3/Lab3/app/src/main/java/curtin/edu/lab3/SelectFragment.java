package curtin.edu.lab3;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //ADDED----------------------------------------
    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;
    private StructureData structures;
    private Structure selectedStructure = null;
    public Structure getSelectedStructure()
    {
        return selectedStructure;
    }
    //---------------------------------------------

    public SelectFragment() { //---------------------------
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectFragment newInstance(String param1, String param2) {//-----------------
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //----------------------------------
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //ADDED-----------------------------------------------
        this.structures = StructureData.get();
        //----------------------------------------------------
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { //-----------------------------
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_select, container, false);

        //ADDED--------------------------------------------------
        //defining fragments
        View view = inflater.inflate(R.layout.fragment_select,container,false);

        //setting up RecyclerView
        //Obtain the RecyclerView UI element
        recycleView = (RecyclerView) view.findViewById(R.id.selectRecycleView);
        //Specify how it should be laid out
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //Have your data ready
        //List<MyData> data = ...;
        //Create your adapter
        adapter = new SelectAdapter();
        //Hook it up
        recycleView.setAdapter(adapter);
        return view;
        //------------------------------------------------------------
    }


    //ADDED---------------------------------------------------------------------------------
    //SelectViewHolder Inner Class
    private class SelectViewHolder extends RecyclerView.ViewHolder
    {
        private Structure structure = null;
        private ImageView image; //Reference to UI element(s)
        private TextView label; //Reference to UI element(s)

        public SelectViewHolder(LayoutInflater li, ViewGroup view)
        {
            super(li.inflate(R.layout.list_selection,view,false));

            //Grab UI element reference(s)
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

        //Called by your adapter
        public void bind(Structure structure)
        {
            this.structure = structure;
            image.setImageResource(structure.getDrawableId());
            label.setText(structure.getLabel());
        }
        //

    }

    //SelectAdapter Inner Class
    //It is easier to nest your adapter inside your activity/fragment. Like this.
    private class SelectAdapter extends RecyclerView.Adapter<SelectViewHolder>
    {
        //private List<MyData> data;
        //public SelectAdapter(List<MyData> data)
        //{
        //    this.data = data;
        //}

        //About Adapter.onCreateViewHolder()
        @NonNull
        @Override
        public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            //we need LayoutInflater to create a ViewHolder.
            LayoutInflater li = LayoutInflater.from(getActivity()); //Fragment method
            //layout inflater belongs to the activity that's why we need to call the fragment's
            //getActivity() method.
            return new SelectViewHolder(li, parent);
        }

        //About Adapter.onBindViewHolder()
        //Description: called when RecyclerView needs to use a ViewHolder to display a different
        //             data element,
        //             index identifies which element to display.
        @Override
        public void onBindViewHolder(@NonNull SelectViewHolder holder, int index)
        {
            holder.bind(structures.get(index));
        }

        //About Adapter.getItemCount()
        @Override
        public int getItemCount()
        {
            return structures.size();
        }
    }
    //-------------------------------------------------------------------------------------
}