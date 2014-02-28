/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexing;

/**
 *
 * @author ehab
 */
public class Constants {
    /* Batch type 1
    document type = 2
    page type = 3
     * 
     */


    public static final int BATCH_TYPE = 1;
    public static final int DOC_TYPE = 2;
    public static final int PAGE_TYPE = 3;

    public static final String CLIENT_MORENA_FOLDER = System.getProperty("user.home") + "/.morena";
    public static final String CLIENT_CAPTURE_FOLDER = System.getProperty("user.home")+"/.capture";
    public static final String CLIENT_MORENA_PROPERTIES_FILE = System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties";
    public static final String SERVER_PROPERTIES_FILE_NAME = "/.mConfig/scanner.properties";
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static final String LINE_SEPARATOR = "/";
    public static final String PAGE_STRING= "page";
    public static final String DOT_PROPERTIES = ".properties";
    public static final String DOT_TIF = ".tif";
    public static final String DOT_JPG = ".jpg";
    public static final String DOT_PDF = ".pdf";
    public static final String JPG_EXTENTION = "jpg";

    

    public static final String PERCENT_TXT = "%";
    public static final int PRINT = 1;
    public static final int PDF = 2;
    public static final int RAR = 3;
    public static final int JPG = 4;

    public static final int ZOOM_DEFAULT = 50;
    public static final int ROTATE_90 = 90;

    public static final String VIEW_HOLD ="HOLD";
    public static final String VIEW_FREE ="FREE";
    
    public static String VAL_SELECT = "Select...";
    public static String VAL_STRING = "String";
    public static String VAL_NUMBER = "Number";
    public static String VAL_BOOLEAN = "Boolean";
    public static String VAL_DATE = "Date/Time";
    public static String VAL_LIST = "List";

    public static String PAGE_ID_LBL = "id";
    public static String PAGE_IMG_ID_LBL = "Image ID";
    public static String PAGE_NAME_LBL = "Name";
    public static String PAGE_PATH_LBL = "Path";
    public static String PAGE_DOC_CODE = "Doc Code";
    public static String PAGE_DOC_VAL = "Doc Value";
    public static String COMBO_SELECT_ALL = "All(الكل)";
     public static String ATTRIBUTE_TXT = "Images";

}
