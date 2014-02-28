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
    public final static int IndexSelect = 7;
    public final static int ExceptionSelect = 9;
    public static int INDEX_EXCEPTION = 6;
    public static int INDEX_FINISHED = 7;
    public static int INDEXED = 5;
}
