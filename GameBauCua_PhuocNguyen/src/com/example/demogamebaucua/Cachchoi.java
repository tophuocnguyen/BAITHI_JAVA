package com.example.demogamebaucua;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Cachchoi extends Activity{
	Button ChuyenveHome;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cachchoigame);
        ChuyenveHome =(Button) findViewById(R.id.button1);
		ChuyenveHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent chuyen = new Intent(Cachchoi.this,Home.class);
				startActivity(chuyen);
			}
		});
	}

}
