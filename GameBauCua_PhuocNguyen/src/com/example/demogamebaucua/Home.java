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

public class Home extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
	}

	public void chuyenSangactivity_main(View v){
		Intent chuyen = new Intent(Home.this,MainActivity.class);
		startActivity(chuyen);
	}
	public void chuyenSangcachchoi(View v){
		Intent chuyen = new Intent(Home.this,Cachchoi.class);
		startActivity(chuyen);
	}
}