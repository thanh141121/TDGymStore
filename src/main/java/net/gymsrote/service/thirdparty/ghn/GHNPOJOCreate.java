package net.gymsrote.service.thirdparty.ghn;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@JsonAppend()
public class GHNPOJOCreate {
	private int payment_type_id = 2;
	private String from_name = "TDGYMSTORE";
	private String from_address = "Đường số 8";
	private String from_ward_name = "Phường 5";
	private String from_district_name = "Quận 11";
	private String from_province_name = "TP Hồ Chí Minh";
	private String required_note = "KHONGCHOXEMHANG";
//	private String return_name;
//	private String return_phone;
//	private String return_address;
//    private String return_ward_name;
//    private String return_district_name;
    private String client_order_code; //Mã đơn hàng riêng của khách hàng.
    private String to_name = "Nguoi mua test";
    private String to_phone = "0909998877";
    private String to_address = "address test";
    private String to_ward_name = "Phường 14";
    private String to_district_name = "Quận 10";
    private String to_province_name = "TP Hồ Chí Minh";
    

    private int cod_amount = 0;
    private String content;
    private int weight = 200; //Khối lượng của đơn hàng (gram).
    private int length = 1; //Chiều dài của đơn hàng (cm).
    private int width = 19; //Chiều rộng của đơn hàng (cm).
    private int height = 10; //Chiều cao của đơn hàng (cm).
//    private int pick_station_id;
//    private int deliver_station_id;
    private int insurance_value = 10000; //Giá trị của đơn hàng ( Trường hợp mất hàng , bể hàng sẽ đền theo giá trị của đơn hàng).
    private int service_id = 0;
    private int service_type_id = 2;
    //private int coupon;
    // pick_shift;
//    private int  pickup_time;
    
    private List<Item> items = new ArrayList<>();
    
	@Getter @Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Item {
		private String name = "Sản phẩm";
		private int quantity;
		
	}
	public GHNPOJOCreate() {
		this.items.add(new Item("test tên sp", 1));
	}
}
