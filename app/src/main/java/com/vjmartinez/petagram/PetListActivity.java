package com.vjmartinez.petagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PetListActivity extends PetagramActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        //Se toman los parametros enviados
        Bundle extras = getIntent().getExtras();
        String oppeningDate = extras.getString("KEY_DATE");
        showToast(oppeningDate);
    }
}
