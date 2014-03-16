/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.test.*;

/**
 *
 * @author Bahi
 */
public interface SwingWorkerFactory<T, V> {
	
	public org.jdesktop.swingworker.SwingWorker<T, V> getInstance(final IWorker<T> worker);

}