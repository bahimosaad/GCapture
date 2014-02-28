/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

/**
 *
 * @author bahi
 */


import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;


public class HijriCalendar
{


  private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
  private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
  public static HijriCalendarImpl impl = new HijriCalendarICUImpl();
  /**
   * @return A string representing the equivalent hijri date in the format dd/MM/yyyy
   */
  public static String toHijri(Date gdate)
  {
    String result = "";
    if(gdate != null) {
        try  {
            result = format.format(gdate);
        } catch (Exception ex)  {
        }
    }
    return result;
  }

  /**
   *
   * @return the number of days in the specified hijri month
   * @param givenMonth a month in the formate MMyyyy
   */
  public static int getHijriMonthCount(String givenMonth)
  {
    return impl.getHijriMonthCount(givenMonth);
  }


  /**
   * @return The date accordant to the provided hijri date
   * @param hdate hijri date string in the format d/M/yyyy
   */
  public static Date toGreg(String hdate)
  {
    Date result = null;
    if( hdate != null && !hdate.equals("") ) {
        try  {
            result = format.parse(hdate);
        } catch (Exception ex)  {}
    }
    return result;
  }

  public static Date toGregYearDate(String hdate)
      {
        Date result = null;
        if( hdate != null && !hdate.equals("") ) {
            try  {
                result = yearFormat.parse(hdate);
            } catch (Exception ex)  {}
        }
        return result;
  }

  /**
   * @return The date string accordant to the provided hijri date in the format dd/MM/yyyy
   * @param hdate hijri date string in the format d/M/yyyy
   */
  public static String toGregString(String hdate)
  {
    if( hdate == null || hdate.equals("") )
      return "";

    return impl.toGregString(hdate);
  }

  /**
   * @return the next month in the format MMyyyy
   * @param givenMonth a month in the formate MMyyyy
   */
  public static String getPreviousMonth(String givenMonth)
  {
    return impl.getPreviousMonth(givenMonth);
  }

  /**
   * @return the next month in the format MMyyyy
   * @param givenMonth a month in the formate MMyyyy
   */
  public static String getNextMonth(String givenMonth)
  {
    return impl.getNextMonth(givenMonth);
  }

  /**
   * Validate the given hijri date string to be of the formate d/M/yyyy or d-M-yyyy
   * and convert it to the formate ddMMyyyy
   * @return hijri date string in the format ddMMyyyy
   */
  public static String getProperHijriFormate(String hdate)
  {
    return impl.getProperHijriFormate(hdate);
  }



  public static Date parse(String date, String fmt)
  {
    return impl.parse(date,fmt);
  }

  public static String toStringDate(Date date) {
      return date == null ? "" : format.format(date);
  }

  public static String toYearFormat(java.util.Date date) {
    return date == null ? "" : yearFormat.format(date);
  }



  public static void main(String[] args)
  {
    Date gdate = new Date("29/01/1429");
    String hdate = toHijri(gdate);
    System.out.print("Today, Hijri:" + hdate );
    System.out.println(" ,Greg:" + toGregString(hdate));
    System.out.println("Parse"+parse("04-10-2005","dd-MM-yyyy"));
  }

}