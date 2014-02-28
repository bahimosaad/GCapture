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
public interface IWorker<T> {

	public void done(T result);
	
	public T doInBackground();
	
}