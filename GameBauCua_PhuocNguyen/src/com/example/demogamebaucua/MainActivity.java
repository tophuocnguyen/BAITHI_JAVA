package com.example.demogamebaucua;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	Button NguyenveHome;
	GridView gridView;
	Custom_GridView_BanCo adapter;
	Integer[] dsHinh = { R.drawable.nai, R.drawable.bau, R.drawable.ga,
			R.drawable.ca, R.drawable.cua, R.drawable.tom };
	AnimationDrawable cdXiNgau1, cdXiNgau2, cdXiNgau3;
	ImageView hinhXiNgau1, hinhXiNgau2, hinhXiNgau3;
	Random randomXiNgau;
	int giaTriXiNgau1, giaTriXiNgau2, giaTriXiNgau3;
	public static Integer[] gtDatCuoc = new Integer[6];
	int tongtiencu, tongtienmoi;
	TextView tvTien, tvThoiGian;
	Timer timer = new Timer(); // tạo time task
	Handler handler;
	int tienThuong, kiemtra, id_amthanh;
	SharedPreferences luuTru;
	SoundPool amThanhXiNgau = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // luồn âm thanh là 1
	MediaPlayer nhacnen = new MediaPlayer();
	CheckBox ktAmthanh;
	CountDownTimer demthoigian;
	
	Callback callback = new Callback() { // trạm cập nhập lại giao diện
		
		@Override
		public boolean handleMessage(Message msg) {
			RandomXiNgau1(); // cập nhật giao diện (dừng lại xí ngầu lại trong 1 khoản thời gian break xử lý hình ảnh) 
			RandomXiNgau2();
			RandomXiNgau3();
			
			for (int i = 0; i < gtDatCuoc.length; i++) {
				if(gtDatCuoc[i] != 0){  // người dùng có đặt cược
					if(i == giaTriXiNgau1){   // so sánh vị trí đặt cược của người chơi với giá trị xí ngầu
						tienThuong += gtDatCuoc[i];
					}
					if(i == giaTriXiNgau2){
						tienThuong += gtDatCuoc[i];
					}
					if(i == giaTriXiNgau3){
						tienThuong += gtDatCuoc[i];
					}
					if(i != giaTriXiNgau1 && i != giaTriXiNgau2 && i != giaTriXiNgau3){
						tienThuong -= gtDatCuoc[i];
					}
				}
			}
			if(tienThuong > 0){
				Toast.makeText(getApplicationContext(), "Quá dữ bạn trúng được " + tienThuong, Toast.LENGTH_SHORT).show();
			}else if (tienThuong == 0) {
				Toast.makeText(getApplicationContext(), "Hên quá mém chết ! " , Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "Ôi xui quá mất " + tienThuong + " rồi !" , Toast.LENGTH_SHORT).show();
			}
			
			LuuDuLieuNguoiDung(tienThuong);  // khi so sánh trúng nó sẽ lưu
			tvTien.setText(String.valueOf(tongtienmoi));
			return false;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NguyenveHome =(Button) findViewById(R.id.button1);
		NguyenveHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent chuyen = new Intent(MainActivity.this,Home.class);
				startActivity(chuyen);
			}
		});
		hinhXiNgau1 = (ImageView) findViewById(R.id.xingau1);
		hinhXiNgau2 = (ImageView) findViewById(R.id.xingau2);
		hinhXiNgau3 = (ImageView) findViewById(R.id.xingau3);
		tvTien = (TextView) findViewById(R.id.tvTien);
		ktAmthanh = (CheckBox) findViewById(R.id.checkBox1);
		tvThoiGian = (TextView) findViewById(R.id.tvThoiGian);
		
		gridView = (GridView) findViewById(R.id.gvBanCo);
		adapter = new Custom_GridView_BanCo(this, R.layout.custom_banco, dsHinh);
		gridView.setAdapter(adapter);
		
		luuTru = getSharedPreferences("luutruthongtin", Context.MODE_PRIVATE); //tạo file lưu trữ 
		tongtiencu = luuTru.getInt("TongTien", 1000);  // lấy ra tổng tiền trong lưu trữ dữ liệu nếu không có sẽ mặc định là 1000
		tvTien.setText(String.valueOf(tongtiencu));  // lúc mở game sẽ hiện lên tiền lúc trước
		
		id_amthanh = amThanhXiNgau.load(this, R.raw.dice, 1); // load âm thanh (this ngay tại class này, 1 là độ ưu tiên) 
		nhacnen = MediaPlayer.create(this, R.raw.nhacnenbaucua); // load nhac nền
		
		nhacnen.start();
		
		ktAmthanh.setOnCheckedChangeListener(new OnCheckedChangeListener() {  //kt có tắt âm thanh ko
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean kt) {
				if(kt){
					nhacnen.stop();
				}else{
					try {
						nhacnen.prepare();   // đánh dấu chỗ ta ngưng nhạc
						nhacnen.start();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		
		demthoigian = new CountDownTimer(180000,1000) { // thời gian lúc đầu là 3 phút, sau 1 giây sẽ giảm xuống 1 giây
			
			@Override
			public void onTick(long millisUntilFinished) { 
				long milis = millisUntilFinished;  //truyền millisecond
				long gio = TimeUnit.MILLISECONDS.toHours(milis); //convert sang giờ
				long phut = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
				long giay = TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis));
				
				String giophutgiay = String.format("%02d:%02d:%02d", gio,phut,giay); // chuyển định dạng
				tvThoiGian.setText(giophutgiay);
			}
			
			@Override
			public void onFinish() {
				Editor edit = luuTru.edit(); // thay đổi dữ liệu của file lưu trữ
				tongtiencu = luuTru.getInt("TongTien", 1000); // sau khi đếm ngược hết 3 phút +1000
			    tongtienmoi = tongtiencu + 1000;
				edit.putInt("TongTien", tongtienmoi ); // cập nhập csdl
				edit.commit();
				
				tvTien.setText(String.valueOf(tongtienmoi)); //chuyển sang kiểu string và cập nhật lê texview
				demthoigian.cancel();
				demthoigian.start();
			}
		};
		
		demthoigian.start();
		handler = new Handler(callback);
	}
	
	private void LuuDuLieuNguoiDung(int tienthuong){
		Editor edit = luuTru.edit();    // thay đổi dữ liệu file lưu trữ
		tongtiencu = luuTru.getInt("TongTien", 1000); //lấy giá trị tổng tiền nếu mới chơi sẽ mặc định là 1000
	    tongtienmoi = tongtiencu + tienthuong;
		edit.putInt("TongTien", tongtienmoi );
		edit.commit();
	}

	public void LacXiNgau(View v) {
		hinhXiNgau1.setImageResource(R.drawable.hinhdongxingau); // load lại hình động
		hinhXiNgau2.setImageResource(R.drawable.hinhdongxingau);
		hinhXiNgau3.setImageResource(R.drawable.hinhdongxingau);

		cdXiNgau1 = (AnimationDrawable) hinhXiNgau1.getDrawable(); //lấy các item trong androi:src hinhdongxingau gán vào AnimmationDrawable, khi gọi animation ra nó sẽ duyệt từng item
		cdXiNgau2 = (AnimationDrawable) hinhXiNgau2.getDrawable();
		cdXiNgau3 = (AnimationDrawable) hinhXiNgau3.getDrawable();
		
		kiemtra = 0;
		for (int i = 0; i < gtDatCuoc.length; i++) {
			kiemtra += gtDatCuoc[i];
		}
		
		if(kiemtra == 0){
			Toast.makeText(getApplicationContext(), "Bạn vui lòng đặt cược ! ", Toast.LENGTH_SHORT).show();
		}else{
			if(kiemtra > tongtiencu ){
				Toast.makeText(getApplicationContext(), "Bạn không đủ tiền để đặt cược ! ", Toast.LENGTH_SHORT).show();
			}else{
				
				amThanhXiNgau.play(id_amthanh, 1.0f, 1.0f, 1, 0, 1.0f); // load âm thanh (âm thanh trái, âm thanh phải là 1.0f, 0 không lặp lại)
				cdXiNgau1.start(); //duyệt qua các ảnh hiển thị lên 
				cdXiNgau2.start();
				cdXiNgau3.start();
				
				tienThuong = 0;
				timer.schedule(new LacXiNgau(), 1000); // thời gian dừng là 1000s
				
			}
			
		}
		
		
		
	}

	class LacXiNgau extends TimerTask { // timer nhận giá trị timetask

		@Override
		public void run() { //timetaskk tạo ra 1 phương thưc run ( dùng để xử lý các nguồn về code)
			handler.sendEmptyMessage(0); // dừng lại 1 hình ảnh item nào đó (lấy message rồi gửi về call back)
			

		}
	}

	private void RandomXiNgau1() {

		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6); //random 6 hình
		switch (rd) {

		case 0:
			hinhXiNgau1.setImageResource(dsHinh[0]); // dựa vào danh sách hình lấy ra hình con nai
			giaTriXiNgau1 = rd;
			break;

		case 1:
			hinhXiNgau1.setImageResource(dsHinh[1]);
			giaTriXiNgau1 = rd;
			break;

		case 2:
			hinhXiNgau1.setImageResource(dsHinh[2]);
			giaTriXiNgau1 = rd;
			break;

		case 3:
			hinhXiNgau1.setImageResource(dsHinh[3]);
			giaTriXiNgau1 = rd;
			break;

		case 4:
			hinhXiNgau1.setImageResource(dsHinh[4]);
			giaTriXiNgau1 = rd;
			break;

		case 5:
			hinhXiNgau1.setImageResource(dsHinh[5]);
			giaTriXiNgau1 = rd;
			break;

		}
	}
	
	private void RandomXiNgau2() {

		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6);
		switch (rd) {

		case 0:
			hinhXiNgau2.setImageResource(dsHinh[0]);
			giaTriXiNgau2 = rd;
			break;

		case 1:
			hinhXiNgau2.setImageResource(dsHinh[1]);
			giaTriXiNgau2 = rd;
			break;

		case 2:
			hinhXiNgau2.setImageResource(dsHinh[2]);
			giaTriXiNgau2 = rd;
			break;

		case 3:
			hinhXiNgau2.setImageResource(dsHinh[3]);
			giaTriXiNgau2 = rd;
			break;

		case 4:
			hinhXiNgau2.setImageResource(dsHinh[4]);
			giaTriXiNgau2 = rd;
			break;

		case 5:
			hinhXiNgau2.setImageResource(dsHinh[5]);
			giaTriXiNgau2 = rd;
			break;

		}
	}
	
	private void RandomXiNgau3() {

		randomXiNgau = new Random();
		int rd = randomXiNgau.nextInt(6);
		switch (rd) {

		case 0:
			hinhXiNgau3.setImageResource(dsHinh[0]);
			giaTriXiNgau3 = rd;
			break;

		case 1:
			hinhXiNgau3.setImageResource(dsHinh[1]);
			giaTriXiNgau3 = rd;
			break;

		case 2:
			hinhXiNgau3.setImageResource(dsHinh[2]);
			giaTriXiNgau3 = rd;
			break;

		case 3:
			hinhXiNgau3.setImageResource(dsHinh[3]);
			giaTriXiNgau3 = rd;
			break;

		case 4:
			hinhXiNgau3.setImageResource(dsHinh[4]);
			giaTriXiNgau3 = rd;
			break;

		case 5:
			hinhXiNgau3.setImageResource(dsHinh[5]);
			giaTriXiNgau3 = rd;
			break;

		}
	}


}
