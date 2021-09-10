package curtin.edu.lecture_3_recyclerview_in_fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView itemName;
    Button itemButton;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemText);
        itemButton = itemView.findViewById(R.id.itemButton);
    }
}
