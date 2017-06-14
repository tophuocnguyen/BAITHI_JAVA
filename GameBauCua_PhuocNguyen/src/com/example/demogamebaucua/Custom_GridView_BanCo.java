package com.example.demogamebaucua;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Custom_GridView_BanCo extends ArrayAdapter<Integer> {
	
	Context context;
	int resource;  // khai báo các biến trong phương thức khởi tạo ta truyền vào
	Integer[] objects; //danh sach cac hinh
	Integer[] giaTien = {0,100,200,300,400,500};
	ArrayAdapter<Integer> adapter;
	
	public Custom_GridView_BanCo(Context context, int resource, Integer[] objects) {
		super(context, resource, objects);

		this.context = context;  //gán các biến vừa khai báo = các cái biến ta truyền vào khi gọi custom_griview
		this.resource = resource;
		this.objects = objects;
		adapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item,giaTien);// đổ dữ liệu vào adapter 
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) { // khởi tạo giao diện griview
		
		View view = View.inflate(context,resource, null);
		ImageView imgBanCo = (ImageView) view.findViewById(R.id.imgBanCo);
		Spinner spinGiaTien = (Spinner) view.findViewById(R.id.spinGiaTien);
		
		imgBanCo.setImageResource(objects[position]); //position là vị trí hình
		spinGiaTien.setAdapter(adapter); // spinGiaTien = adapter truyền vào
		
		spinGiaTien.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, // khi người dùng chọn item vị trí đó thì sẽ xử lý
					int positionspin, long id) { 						//positionspin là vị trí của giá tiền của 1 item
				MainActivity.gtDatCuoc[position] = giaTien[positionspin]; // position griview lưu giá trị đặt cược ở vị trí item ta đặt cược
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		return view;
	}

}
