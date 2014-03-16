/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

/**
 *
 * @author bahy
 */
public interface CaptureStatus {

    public final static int ScanMode = 1;
    public final static int QAMode = 2;
    public final static int ExceptionMode = 3;
    public final static int IndexMode = 4;
    public static int INDEXED = 5;
    public static int INDEX_EXCEPTION = 6;
    public static int INDEX_FINISHED = 7;
    
}
