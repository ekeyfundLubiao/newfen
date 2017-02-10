package moni.anyou.com.view.tool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONObject;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import moni.anyou.com.view.config.SysConfig;


public class FileHelper {
	private Context context;
	private boolean hasSD = false;
	private String SDPATH;
	private String FILESPATH;

	public FileHelper(Context context) {

		this.context = context;

		hasSD = Environment.getExternalStorageState().equals(

		Environment.MEDIA_MOUNTED);

		SDPATH = Environment.getExternalStorageDirectory().getPath();

		FILESPATH = this.context.getFilesDir().getPath();

	}

	public boolean exits(String fileName, String dir) {
		File file = new File(SDPATH + "//" + SysConfig.File_DIR + dir
				+ fileName);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean DownloadFromUrlToData(String downloadUrl,String fileName) {
		try {
			URL url = new URL(downloadUrl); // you can write
			// here any link
			File file = new File(SDPATH + "//"+ SysConfig.File_DIR + "//" + fileName);
			long startTime = System.currentTimeMillis();
			Log.d("FileDownloader", "download begining");
			Log.d("FileDownloader", "download url:" + url);
			Log.d("FileDownloader", "downloaded file name:" + fileName);

			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();
			ucon.setConnectTimeout(5000);

			/*
			 * * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1204];
	        int length;
	        int byteread = 0;
	        int bytesum = 0;
	        FileOutputStream fs = new FileOutputStream(SDPATH
					+ "//"+SysConfig.File_DIR+"//" + fileName);
	        while ((byteread = bis.read(buffer)) != -1) {
	                bytesum += byteread;
	                fs.write(buffer, 0, byteread);
	        }
            fs.close();
			//ByteArrayBuffer baf = new ByteArrayBuffer(1024);
			/*
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			FileOutputStream fout = new FileOutputStream(SDPATH
					+ "//"+SysConfig.File_DIR+"//" + fileName);
			fout.write(baf.toByteArray());
			fout.close();
		    */
			Log.d("FileDownloader",
					"download ready in"
							+ ((System.currentTimeMillis() - startTime) / 1000)
							+ " sec");
							
			bis.close();
			is.close();
		    
			UnZipFolder(fileName);
			return true;
		} catch (Exception e) {

			Log.d("ImageManager", "Error: " + e);
			return false;
		}
	}

	public static void initData(Context context, String fileName) {
		try {
			FileHelper fileHelper = new FileHelper(context);
			String data = "";
			if (!fileHelper.hasSD()) {
				try {
					data = Tools.replace(fileHelper.readFile(fileName).trim(),
							"\n", "");

					data = Tools.replace(data, "\t", "");
					data = Tools.replace(data, "\r", "");
					int index = data.indexOf("{");
					data = Tools.substring(data, index, data.length() - index);
					// System.out.println(data);

					// fileHelper.deleteFile(FileHelper.replace(fileName,".json",".zip"));
					SysConfig.dataJson = new JSONObject(data);
				

				} catch (Exception ex) {
					// System.out.println(ex.toString());
				}
				// UnZipFolder(SDPATH + "//fashicode//", String outPathString);
			} else {

				// fileHelper.UnZipFolder("data.zip");
				try {
					// System.out.println(fileName);
					data = Tools.replace(
							fileHelper.readSDFile(fileName).trim(), "\n", "");

					data = Tools.replace(data, "\t", "");
					data = Tools.replace(data, "\r", "");
					// System.out.println(data);
					int index = data.indexOf("{");
					data = Tools.substring(data, index, data.length() - index);
					// System.out.println(data);
					// DB
					// DAO_Config dao = new DAO_Config();
					// dao.config = data;
					// InitData.db.add(dao);
					//
					// fileHelper.deleteSDFile(FileHelper.replace(fileName,".json",".zip"));
               
                	//Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                	try{
						SysConfig.dataJson = new JSONObject(data);
                	}
                	catch(Exception ex){
                		System.out.println(ex.toString());
                	}
   

				} catch (Exception ex) {
					System.out.println(ex.toString());
				}
			}

		} catch (Exception ex) {

		}
	}


	/**
	 * DeCompress the ZIP to the path
	 * 
	 * @param
	 *
	 * @param
	 *
	 * @throws Exception
	 */
	public void UnZipFolder(String srcFile) throws Exception {
		String zipFileString = SDPATH + "//" + SysConfig.File_DIR + "//"
				+ srcFile;
		String outPathString = SDPATH + "//" + SysConfig.File_DIR + "//";
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(
				zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
			if (zipEntry.isDirectory()) {
				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(outPathString + File.separator + szName);
				folder.mkdirs();
			} else {

				File file = new File(outPathString + File.separator + szName);
				file.createNewFile();
				// get the output stream of the file
				FileOutputStream out = new FileOutputStream(file);
				int len;
				byte[] buffer = new byte[1024];
				// read (len) bytes into buffer
				while ((len = inZip.read(buffer)) != -1) {
					// write (len) byte from buffer at the position 0
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}
		inZip.close();
	}

	public void createDir(String dir) {
		try {

			File f = new File(SDPATH + "//" + dir);
			if (!f.exists()) {
				f.mkdir();
			} else {

			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public void createDir(String dir1, String dir2) {
		try {

			File f = new File(SDPATH + "//" + dir1 + "//" + dir2);
			if (!f.exists()) {
				f.mkdir();
			} else {

			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	public File createSDFile(String fileName, String str) {

		try {
			File file = new File(SDPATH + "//fashioncode//" + fileName);

			if (!file.exists()) {

				FileOutputStream out = new FileOutputStream(file);
				out.write(str.getBytes("utf8"));
				out.close();

			} else {

				file.delete();
				FileOutputStream out = new FileOutputStream(file);
				out.write(str.getBytes("utf8"));
				out.close();

			}

			return file;
		} catch (Exception ex) {

			return null;
		}

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param fileName
	 */

	public boolean deleteSDFile(String fileName) {

		File file = new File(SDPATH + "//" + SysConfig.File_DIR + "//"
				+ fileName);

		if (file == null || !file.exists() || file.isDirectory())

			return false;

		return file.delete();

	}

	public boolean deleteSDFile(String fileName, String dir) {
		File f = new File(SDPATH + "//" + SysConfig.File_DIR + "//" + dir);
		if (!f.exists()) {
			f.mkdir();
		}
		File file = new File(SDPATH + "//" + SysConfig.File_DIR + "//" + dir
				+ "//" + fileName);

		if (file == null || !file.exists() || file.isDirectory())

			return false;

		return file.delete();

	}
	public String readFile(String fileName) {
		File f = new File("data/data/com.anyou.mobi/files/" + fileName);
		try {
			StringBuffer buf = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}
			in.close();
			return buf.toString();
		} catch (Exception ex) {
			return "";
		}
	

	}
	/**
	 * 
	 * 
	 * 
	 * @param fileName
	 * 
	 * @return
	 */

	public String readSDFile(String fileName) {
		File f = new File(SDPATH + "//" + SysConfig.File_DIR + "//" + fileName);
		try {
			StringBuffer buf = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			// BufferedReader in = new BufferedReader(new InputStreamReader(new
			// FileInputStream(f)));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
				// buf.append('\n');
			}
			in.close();
			
			return buf.toString();
		} catch (Exception ex) {
			return "";
		}
		/*
		 * try {
		 * 
		 * FileInputStream fis = new FileInputStream(file);
		 * 
		 * int c;
		 * 
		 * while ((c = fis.read()) != -1) {
		 * 
		 * sb.append((char) c);
		 * 
		 * }
		 * 
		 * fis.close();
		 * 
		 * } catch (FileNotFoundException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } catch (IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * }
		 */

	}

	public String getFILESPATH() {

		return FILESPATH;

	}

	public String getSDPATH() {

		return SDPATH;

	}

	public boolean hasSD() {

		return hasSD;

	}


}
