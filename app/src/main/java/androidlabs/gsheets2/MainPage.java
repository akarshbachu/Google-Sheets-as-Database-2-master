package androidlabs.gsheets2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidlabs.gsheets2.Post.PostData;

/**
 * Created by ADJ on 2/21/2017.
 */
public class MainPage extends AppCompatActivity{

    Button getData;
    Button sendData,loc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


        loc=(Button)findViewById(R.id.location);

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,ServerMap.class);
                startActivity(i);
            }
        });


    };



    }
