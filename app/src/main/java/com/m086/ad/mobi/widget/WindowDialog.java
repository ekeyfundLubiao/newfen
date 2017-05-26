package com.m086.ad.mobi.widget;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
public abstract class WindowDialog {
	protected Context context;
	protected LayoutInflater inflater;
	protected PopupWindow window;
	
	
	public WindowDialog(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void postSendMessage(int id, Object obj) {
		Message msg = new Message();
		msg.obj = obj;
		msg.what = id;
		post.sendMessage(msg);
	}
	
	public void ShowWindow() {
		Message msg = new Message();
		msg.obj = null;
		msg.what = 0;
		post.sendMessage(msg);
	}
	public void closeWindow() {
		Message msg = new Message();
		msg.obj = null;
		msg.what = -1;
		System.out.println("close action");
		post.sendMessage(msg);
	}
	public PostHandler post = new PostHandler();
	public abstract PopupWindow getWindow(int i, Object tag,PopupWindow window);

	public class PostHandler extends Handler {

		@Override
		public void handleMessage(final Message msg) {
			final Object obj=msg.obj;
			final Integer i=msg.what;
			
			this.post(new Runnable() {

				@Override
				public void run() {
					
					window=getWindow(i, obj, window);

				}

			});

		}
	}
}

