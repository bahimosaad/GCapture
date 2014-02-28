package com.gdit.capture.model;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class HijriCalendarImpl 
{
  public abstract String toHijri(Date gdate);
  public abstract int getHijriMonthCount(String givenMonth);
  public abstract Date toGreg(String hdate);
  
  private SimpleDateFormat gfmt = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
  public final String toGregString(String hdate)
  {
    return gfmt.format(toGreg(hdate));
  }
  
  public final String getPreviousMonth(String givenMonth)
  {
    int month = Integer.parseInt( givenMonth.substring(0,2) );
    int year = Integer.parseInt( givenMonth.substring(2) );
    
    if( month == 1 )
    {  
      return "12" + (year-1);
    }
    
    DecimalFormat fmt = new DecimalFormat("00");
    return fmt.format(month-1) + year;
  }
  
  public final String getNextMonth(String givenMonth)
  {
    int month = Integer.parseInt( givenMonth.substring(0,2) );
    int year = Integer.parseInt( givenMonth.substring(2) );
    
    if( month == 12 )
    {  
      return "01" + (year+1);
    }
    
    DecimalFormat fmt = new DecimalFormat("00");
    return fmt.format(month+1) + year;
  }
  
  public final String getProperHijriFormate(String hdate)
  {
    try
    {
      if( hdate == null || hdate.length() < 6 || hdate.length() > 10 )
        throw new RuntimeException();
      
      int date, month, year; 
      
      int cursor = 2;
      if( ! Character.isDigit( hdate.charAt(1) ) )
        cursor = 1;
      
      date = Integer.parseInt(hdate.substring(0,cursor));
        
      cursor += 1;
      if( ! Character.isDigit( hdate.charAt(cursor+1) ) ){
        month = Integer.parseInt(hdate.substring(cursor,cursor+1));
        cursor += 2;
      }
      else
      {
        month = Integer.parseInt(hdate.substring(cursor,cursor+2));
        cursor += 3;
      }
      
      String yearString = hdate.substring(cursor);
      
      if( yearString.length() != 4 || month > 12 )
        throw new RuntimeException();
        
      year = Integer.parseInt(yearString);
      
      DecimalFormat fmt = new DecimalFormat("00");
      return fmt.format(date)+fmt.format(month)+year;
    }
    catch (Exception e)
    {
      throw new RuntimeException(
        "Date value ["+hdate+"] doesn't comply with the format d/M/yyyy nor the format d-M-yyyy.");
    }
  }
  
  private SimpleDateFormat dbfmt = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
  public final Date parse(String date, String fmt)
  {
    try
    {
      if( date == null || date.equals("") )
        throw new RuntimeException( "The date ["+date+"] is null or empty.");  
      dbfmt.applyPattern(fmt);
      return dbfmt.parse( date );
    }
    catch (ParseException e)
    {
      throw new RuntimeException( "The date ["+date+"] doesn't comply with the format ["+fmt+"].",e);  
    }
  }
  
}