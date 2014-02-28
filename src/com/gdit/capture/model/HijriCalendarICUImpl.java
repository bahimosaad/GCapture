package com.gdit.capture.model;

import com.ibm.icu.util.IslamicCalendar;
import java.text.DecimalFormat;
import java.util.Date;

public class HijriCalendarICUImpl  extends HijriCalendarImpl 
{
  // Note: In ICU4J ,  Month index start from 0, i.e. Muharram = 0 
  // because of that we always have to +/- 1 from the month index.
  
  public int getHijriMonthCount(String givenMonth)
  {
    IslamicCalendar cal = new IslamicCalendar(
      Integer.parseInt( givenMonth.substring(2) ) ,
      Integer.parseInt( givenMonth.substring(0,2) ) - 1 ,
      1
      );
    return cal.getActualMaximum(cal.DATE);
  }

  public Date toGreg(String hdate)
  {
    if( hdate == null ) return null;
    
    hdate = getProperHijriFormate(hdate);
    IslamicCalendar cal = new IslamicCalendar(
      Integer.parseInt( hdate.substring(4) ),
      Integer.parseInt( hdate.substring(2,4) ) -1 ,
      Integer.parseInt( hdate.substring(0,2) )
      );
      
    return cal.getTime();
  }
  
  /**
   * @return A string representing the equivalent hijri date in the format dd/MM/yyyy
   */
  public String toHijri(Date gdate)
  {
    if( gdate == null ) return null;
    
    IslamicCalendar cal =  new IslamicCalendar(gdate);
    DecimalFormat dfmt = new DecimalFormat("00");
    
    String hdate = dfmt.format( cal.get(cal.DATE))+"/"+
      dfmt.format( cal.get(cal.MONTH) + 1 )+"/"+
      cal.get(cal.YEAR);
    
    return hdate;
      
  }
}