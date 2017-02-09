package moni.anyou.com.view.bean;
import java.io.Serializable;
import org.kymjs.aframe.database.annotate.Id;

/**
 * �������
 * */
public class DAO_DATA_Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id()
	private int id;  //增长
	private int key=1;  //标识
 	public String jsonData; //基础数据

  
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJson(String jsonData) {
		this.jsonData = jsonData;
	}
	@Override
	public String toString() {
		return "DAO_DATA_Info [id=" + id + ", jsonData=" + jsonData + "]";
	}

	
	
}
