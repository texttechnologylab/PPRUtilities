package org.texttechnologylab.utilities;

import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helperklasse für String-Operationen
 * @author Giuseppe Abrami
 *
 */
public class StringHelper {

    public static final SimpleDateFormat DATEOFRMAT = new SimpleDateFormat("dd.MM.y");
    public static final SimpleDateFormat TIMESTAMPFORMAT = new SimpleDateFormat("dd.MM.y hh:mm:ss");

    public static String[] FIRSTNAME = {"Dr. h. c.", "Dr."};
    public static String[] REGEXCLEAN = {"\\(.*\\)"};

    public static String replaceList(String sInput, String[] replaceList){

        String rString = sInput;

        rString = rString.replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}\\p{Z}]", " ");

        for (String s : replaceList) {
            rString = rString.replace(s, "");
        }
        rString = clean(rString);
        return rString;

    }

    /**
     * Trim a String
     * @param sInput
     * @return
     */
    public static String clean(String sInput){
        return sInput.trim();
    }

    /**
     * Parse a Date
     * @param sDate
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String sDate) throws ParseException {
        return DATEOFRMAT.parse(sDate);
    }

    /**
     * Parse a Date
     * @param pNode
     * @return
     * @throws ParseException
     */
    public static Date parseDate(Node pNode) throws ParseException {
        return parseDate(pNode, true);
    }

    /**
     * Parse a Date
     * @param pNode
     * @param bToday
     * @return
     * @throws ParseException
     */
    public static Date parseDate(Node pNode, boolean bToday) throws ParseException {
        if(pNode.getTextContent().length()>0){
            return parseDate(pNode.getTextContent());
        }
        return new Date(System.currentTimeMillis());

    }

    /**
     * Parse a Date
     * @param pNode
     * @param pDefault
     * @return
     * @throws ParseException
     */
    public static Date parseDate(Node pNode, Date pDefault) throws ParseException {
        if(pNode.getTextContent().length()>0){
            return parseDate(pNode.getTextContent());
        }
        return pDefault;

    }


    public static String formatDate(Date date) {
        return DATEOFRMAT.format(date);
    }
}
