/*
 * JFileSync
 * Copyright (C) 2002-2007, Jens Heidrich
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA, 02110-1301, USA
 */

package jfs;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import java.util.Vector;
import javax.swing.UIManager;

import jfs.conf.JFSConfig;
import jfs.conf.JFSConst;
import jfs.conf.JFSDirectoryPair;
import jfs.conf.JFSFilter;
import jfs.conf.JFSLog;
import jfs.conf.JFSSettings;
import jfs.conf.JFSText;
import jfs.gui.JFSConfirmationView;
import jfs.gui.JFSMainView;
import jfs.gui.JFSProgressView;
import jfs.server.JFSServerFactory;
import jfs.shell.JFSShell;
import jfs.sync.JFSComparison;
import jfs.sync.JFSCopyStatement;
import jfs.sync.JFSSynchronization;
import jfs.sync.JFSTable;

/**
 * JFileSync is an application for synchronizing pairs of directories. This
 * class is the main class of the application. It processes all command line
 * options and starts (1) a JFS server, (2) the Java Swing-based graphical user
 * interface, or (3) a command line shell.
 *
 * @see JFSServerFactory#startCmdLineServer()
 * @see jfs.gui.JFSMainView
 * @see JFSShell#startShell(boolean)
 * @author Jens Heidrich
 * @version $Id: JFileSync.java,v 1.40 2007/07/20 15:24:22 heidrich Exp $
 */
public class NewMain {

	/**
	 * Determines the home directory, where the JAR file respectively the class
	 * files are located.
	 */
	public static String getHome() {
		try {
			URL packageUrl = ClassLoader.getSystemResource("jfs");
			File jfsLibDir = null;

			// JFS started from JAR:
			if (packageUrl.getProtocol().equals("jar")) {
				URL jarUrl = new URL(packageUrl.getFile());
				File jfsFile = new File(jarUrl.toURI());
				jfsLibDir = jfsFile.getParentFile().getParentFile();
			}

			// JFS started from classes directory:
			if (packageUrl.getProtocol().equals("file")) {
				File jfsFile = new File(packageUrl.toURI());
				jfsLibDir = jfsFile.getParentFile();
			}

			return jfsLibDir.getPath();
		} catch (Exception e) {
			return ".";
		}
	}

	/**
	 * Start of the application.
	 *
	 * @param args
	 *            Command line arguments.
	 */
	public  void go() {
		// Get settings for the first time in order to load stored
		// settings before doing any actions:
		JFSSettings s = JFSSettings.getInstance();

		// Get translation and configuration object:
		JFSText t = JFSText.getInstance();
		JFSConfig config = JFSConfig.getInstance();
		PrintStream p = JFSLog.getOut().getStream();

        JFSMainView  mainView = null;
		// Clean config before starting (if main method is used as service):
		config.clean();

		boolean quiet = false;
		boolean launchServer = false;
		boolean startService = false;
		boolean stopService = false;
		boolean loadDefaultFile = true;
		boolean nogui = false;
		int i = 0;

		// Handle command line arguments:


		// Start server/service and exit afterwards if server should be
		// launched:
		if (launchServer) {
			JFSServerFactory.getInstance().startCmdLineServer();
		} else if (startService) {
			JFSServerFactory.getInstance().startService();
		} else if (stopService) {
			JFSServerFactory.getInstance().stopService();
		} else {
			// Initialize new synchronization table:
			JFSTable table = JFSTable.getInstance();
			config.attach(table);


			if (!nogui) {
				// Start GUI:
				p.println(t.get("cmd.startGui"));

				// Determine whether the last configuration when (stored when
				// the program was exited should be loaded at GUI startup:
				s.setNoGui(false);
			mainView =	new JFSMainView(loadDefaultFile);
			} else {
				s.setNoGui(true);
				JFSShell.startShell(quiet);
			}
		}

         JFSComparison comp = new JFSComparison();
            comp.compare();
            Vector<JFSCopyStatement> copy = JFSTable.getInstance().getCopyStatements();
            System.out.println("Copy Statements "+copy.size());
            JFSSynchronization.getInstance().computeSynchronizationLists();

			// Show list of files to copy and to delete:


            JFSSynchronization.getInstance().synchronize();
            mainView.getFrame().setVisible(false);
	}


    public static void main(String[] args){
        NewMain main  = new NewMain();
        main.go();
    }

	/**
	 * Performs a busy wait.
	 *
	 * @param duration
	 *            The number of milli-seconds to wait.
	 */
	public static final void busyWait(long duration) {
		long time = System.currentTimeMillis();
		long stop = time + duration;
		while (time <= stop) {
			time = System.currentTimeMillis();
		}
	}

	/**
	 * Prints the command line help file and exits.
	 */
	private static final void printCmdLineHelpAndExit() {
		JFSShell.printURL(JFSConst.getInstance().getResourceUrl(
				"jfs.help.topic.cmdLine"));
		System.exit(0);
	}
}