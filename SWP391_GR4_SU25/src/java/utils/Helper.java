/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author MSI
 */
public class Helper {
    public static final String VIETNAMESE_CHARACTERS =
            "ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ";

    private static String standardiztionString(String word){
        word = word.toLowerCase();
        return word.substring(0,1).toUpperCase()+word.substring(1).trim();
    }
    public static String formatName(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for(int i=0;i<searchArray.length;i++){
            if(!searchArray[i].isBlank()){
                result.append(standardiztionString(searchArray[i])).append(" ");
            }
        }
        return result.toString().trim();
    }
    public static String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("(?<=\\s)|(?=\\s)");
        boolean firstWordFound = false;

        for (String s : searchArray) {
            if (!firstWordFound && !s.isBlank()) {
                s = formatName(s);
                firstWordFound = true;
            }
            result.append(s);
        }
        return result.toString();
    }


    public static LocalDate convertDateToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, month, dayOfMonth);
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static Date convertLocalNowDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
}
