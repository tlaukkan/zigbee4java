package org.bubblecloud.zigbee;

import android.app.Application;
import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;

import java.io.File;

//import au.com.arvis.hub.Arvis;

public class ZigBeeConsoleAndroid extends Application{

	@Override
	public void onCreate() {

		super.onCreate();

		// configureLog4j();

		//startService(new Intent(this.getApplicationContext(), Arvis.class));
	}

	private void configureLog4j()
	{
		final LogConfigurator logConfigurator = new LogConfigurator();

		logConfigurator.setUseFileAppender(false);
		logConfigurator.setFileName(Environment.getExternalStorageDirectory()+ File.separator + "Arvis" + ".log");

		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);

		logConfigurator.configure();
	}
}
