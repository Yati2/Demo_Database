package sg.edu.rp.c346.id20028056.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert,btnGetTasks;
    TextView tvResults;
    ListView lvResults;
    EditText etDes,etDate;
    ArrayAdapter<String> aaList;
    boolean ascended=false;
    String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnInsert=findViewById(R.id.btnInsert);
        btnGetTasks=findViewById(R.id.btnGetTask);
        tvResults=findViewById(R.id.tvResults);
        lvResults=findViewById(R.id.lv);
        etDate=findViewById(R.id.etDate);
        etDes=findViewById(R.id.etDes);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db=new DBHelper(MainActivity.this);
                String des=etDes.getText().toString();
                String date=etDate.getText().toString();
                Log.w("message",des);
                if(des.equalsIgnoreCase("") || date.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Please Enter the fields",Toast.LENGTH_LONG).show();
                }
                else {
                    db.insertTask(des, date);
                }
                etDate.setText("");
                etDes.setText("");

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db=new DBHelper(MainActivity.this);
                ArrayList<String> dataList=db.getTaskContent();
                if(!ascended)
                {
                    order="ASC";
                    ascended=true;
                }
                else
                {
                    order="DESC";
                    ascended=false;
                }
                ArrayList<Task> taskList=db.getTasks(order);
                db.close();
                aaList=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,taskList);
                lvResults.setAdapter(aaList);


                String txt = "";
                for (int i = 0; i < dataList.size(); i++) {
                    Log.d("Database Content", i +". "+dataList.get(i));
                    txt += (i+1) + ". " + dataList.get(i) + "\n";
                }
                tvResults.setText(txt);


            }
        });
    }


}