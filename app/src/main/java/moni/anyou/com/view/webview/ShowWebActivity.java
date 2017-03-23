package moni.anyou.com.view.webview;

import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.config.SysConfig;

public class ShowWebActivity extends BaseActivity implements OnClickListener {
	private long exitTime = 0;
	Intent intent;
	TextView tvTitle;
	Context mcontext = null;
	WebView web;
	public int currIndex = 0;
	private RelativeLayout rlLeft;
	String title = "";
	String webUrl = "";
	final Handler myHandler = new Handler();
	private static final String APP_CACAHE_DIRNAME = "/insurance_webcache"; 
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webview);
		mcontext = this;
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		webUrl = intent.getStringExtra("url");
		try {
			CookieSyncManager.createInstance(mcontext);
			CookieManager cookieManager = CookieManager.getInstance();
		    cookieManager.removeAllCookie();
			cookieManager.setCookie(webUrl, "token=" + SysConfig.token);
			cookieManager.setCookie(webUrl,
					"uid=" + SysConfig.userInfoJson.getString("user_id"));
			cookieManager.setCookie(webUrl, "cid="
					+ SysConfig.userInfoJson.getString("cid"));
			cookieManager.setCookie(webUrl, "gid="
					+ SysConfig.userInfoJson.getString("gid"));
			//Toast.makeText(getApplicationContext(), Config.UserInfo.getString("employee_id")+"|"+Config.UserInfo.getString("organization_name")+"|"+Config.UserInfo.getString("autority"),
			//		Toast.LENGTH_SHORT).show();
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				CookieSyncManager cookieSyncManager = CookieSyncManager
						.createInstance(mcontext);
				cookieSyncManager.sync();
			}

		} catch (Exception ex) {

		}

	
		web = (WebView) findViewById(R.id.webView);
		// web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// 允许缩放
		// web.getSettings().setBuiltInZoomControls(true);
		web.getSettings().setDefaultTextEncodingName("UTF-8");
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebChromeClient(new WebChromeClient());
		//关闭缓存 功能 
        web.getSettings().setAppCacheEnabled(false); 
        web.getSettings().setAppCacheMaxSize(0);
        web.getSettings().setAppCachePath("");
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setDatabaseEnabled(false); 
		web.setBackgroundColor(0);
		web.setFocusable(true);
		// webViewClient = new MyWebViewClient(web);
		// webViewClient.enableLogging();
		// web.setWebViewClient(webViewClient);

		if (web != null) {
			web.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
				
				}
			});
		
			loadUrl(webUrl);
		}

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus(true);
		// }

	}


	public void loadUrl(String url) {
		if (web != null) {
			web.loadUrl(url);
		
			web.reload();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000
					&& currIndex == 0) {

				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {

				System.exit(0);

			}
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}
	 /**
     * 清除WebView缓存
     */ 
    public void clearWebViewCache(){ 

        //清理Webview缓存数据库 
        try { 
            deleteDatabase("webview.db");  
            deleteDatabase("webviewCache.db"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 

        //WebView 缓存文件 
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME); 
       
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache"); 
      
        //删除webview 缓存目录 
        if(webviewCacheDir.exists()){ 
            deleteFile(webviewCacheDir); 
        } 
        //删除webview 缓存 缓存目录 
        if(appCacheDir.exists()){ 
            deleteFile(appCacheDir); 
        } 
    } 

    /**
     * 递归删除 文件/文件夹
     * 
     * @param file
     */ 
    public void deleteFile(File file) { 

    
        if (file.exists()) { 
            if (file.isFile()) { 
                file.delete(); 
            } else if (file.isDirectory()) { 
                File files[] = file.listFiles(); 
                for (int i = 0; i < files.length; i++) { 
                    deleteFile(files[i]); 
                } 
            } 
            file.delete(); 
        } else { 
           
        } 
    } 




}
