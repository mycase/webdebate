package com.arguments.support;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.arguments.application.liferay.LiferayErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.requeststate.ArgsRenderRequest;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Logger
{
    protected static boolean LOG = false;
    protected static boolean DEBUG = false;
    protected static PrintStream theOut = System.out;
    protected static PrintStream theErr = System.err;
    
    // ------------------------------------------------------------------------
    public static void log(String aValue)
    {
        if (LOG)
            theOut.println(aValue);
    }

    // ------------------------------------------------------------------------
    public static void debug(String aValue)
    {
        if (DEBUG)
            theErr.println(aValue);
    }

    // ------------------------------------------------------------------------
    public static void logAlways(String aString)
    {
        if (LOG)
            theOut.println(aString);
    }

    // ------------------------------------------------------------------------
    public static void debug(Object aValue)
    {
        if (!DEBUG) return;

        debug(prettyString(aValue));
    }

    // ------------------------------------------------------------------------
    public class TestExclStrat implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> anArg) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes aField) {

            return (
                aField.getDeclaringClass() == LiferayErrorHandler.class &&
                aField.getName().equals("theRequest"));
        }
    }

    // ------------------------------------------------------------------------
    public static void logRender(ArgsRenderRequest aRequest)
    {
        String myJsonString = prettyString(aRequest);
     
        try {
            FileWriter writer = new FileWriter(getRenderRequestLogFilePath());
            writer.write(myJsonString);
            writer.close();
            Logger.log(myJsonString);
        } catch (IOException anException) {
            anException.printStackTrace();
        }
    }
    
    // ------------------------------------------------------------------------
    public static void logAction(ArgsRequest aRequest)
    {
        String myJsonString = prettyString(aRequest);
     
        try {
            FileWriter writer = new FileWriter(getActionRequestLogFilePath());
            writer.write(myJsonString);
            writer.close();
            Logger.log(myJsonString);
        } catch (IOException anException) {
            anException.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------
    private static String getActionRequestLogFilePath()
    {
        Date myNow = new Date();
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String myDateString = myFormatter.format(myNow);
        return "/tmp/MakeYourCase." + myDateString + ".actionRequest.json";
    }

    // ------------------------------------------------------------------------
    private static String getRenderRequestLogFilePath()
    {
        Date myNow = new Date();
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String myDateString = myFormatter.format(myNow);
        return "/tmp/MakeYourCase." + myDateString + ".renderRequest.json";
    }

    // ------------------------------------------------------------------------
    private static String prettyString(Object anObject)
    {
        TestExclStrat myExcl = new Logger().new TestExclStrat();

        Gson myGson = new GsonBuilder()
        .setExclusionStrategies(myExcl)
        .setPrettyPrinting()
        .serializeNulls()
        .create();

        return myGson.toJson(anObject);
    }

    public static void main(String[] anArgs)
    {
        Date myNow = new Date();
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String myDateString = myFormatter.format(myNow);

        System.out.println("Date: " + myDateString);
    }
}
