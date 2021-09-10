package curtin.edu.lecture_3_recyclerview_in_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MyData> data;

    public MainActivity(){
        data = new ArrayList<MyData>();
        data.add(new MyData("AAAAA","0000000"));
        data.add(new MyData("BBBBB","000312321"));
        data.add(new MyData("CCCCC","054545454"));
        data.add(new MyData("DDDDDD","000005"));
        data.add(new MyData("EEEEEE","0000050"));
        data.add(new MyData("FFFFFF","60000600"));
        data.add(new MyData("GGGGGG","60000444"));
        data.add(new MyData("HHHHHH","70003333"));
        data.add(new MyData("IIIIII","90004444"));
        data.add(new MyData("JJJJJJ","80077777"));
        data.add(new MyData("KKKKKK","80444444"));
        data.add(new MyData("LLLLLL","600054353"));
        data.add(new MyData("MMMMMM","5000543545"));
        data.add(new MyData("NNNNNN","3000543543"));
        data.add(new MyData("OOOOOO","2000545435"));
        data.add(new MyData("PPPPPP","1000666666"));
        data.add(new MyData("QQQQQQ","343543"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager frag = getSupportFragmentManager();
        RecyclerFragment recyclerFragment = (RecyclerFragment) frag.findFragmentById(R.id.fragment_container);
        if (recyclerFragment == null){
            recyclerFragment = new RecyclerFragment(data);
            frag.beginTransaction().add(R.id.fragment_container, recyclerFragment).commit();
        }
        FragmentB fragmentB = (FragmentB) frag.findFragmentById(R.id.fragmentBContainer);
        if (fragmentB == null){
            fragmentB = new FragmentB("This is the initial");
            frag.beginTransaction().add(R.id.fragmentBContainer, fragmentB).commit();
        }

    }
}