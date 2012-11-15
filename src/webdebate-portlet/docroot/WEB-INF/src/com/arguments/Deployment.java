/**
 * 
 */
package com.arguments;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.arguments.functional.datamodel.ArgumentsException;
import com.arguments.support.Logger;

/**
 * @author mirleau
 * 
 */
public class Deployment
{
    private static Deployment theInstance = new Deployment();

    public final String theDbUrl;
    public final String theDbName;
    public final String theDbDriver;
    public final String theDbUserName;
    public final String theDbPassword;
    
    public final String theWebinfPath;
    
    // ------------------------------------------------------------------------
    public static Deployment i()
    {
        return theInstance;
    }

    // ------------------------------------------------------------------------
    public Deployment()
    {
        final URL myClassRoot = Deployment.class.getResource("/");
        final String myClassRootPath = myClassRoot.getFile();
        Logger.logAlways("Class root path: " + myClassRootPath);
        // From eclipse: /mnt/bigspace/opt/linux/i386/liferay/liferay-sdk/portlets/argumentation-portlet/docroot/WEB-INF/classes/
        // From tomcat: /mnt/bigspace/opt/linux/i386/liferay/liferay-portal-6.1.0-ce-ga1.2/tomcat-7.0.23/webapps/argumentation-portlet/WEB-INF/classes/

        File myWebappFile = new File(myClassRootPath).
                getParentFile().getParentFile().getParentFile().getParentFile();
        File myWebinfFile = new File(myClassRootPath).getParentFile();
        
        final String myWebappPath = myWebappFile.getAbsolutePath()+"/"; 
        Logger.logAlways("Webapp path: " + myWebappPath);
        theWebinfPath = myWebinfFile.getAbsolutePath()+"/";
        
        ArrayList<String> myPropertyLocations = new ArrayList<String>()
                {{
                    add(myWebappPath + "webapps-conf/arguments.deployment.properties");
                    add(myClassRootPath + "com/arguments/testdeployment.properties");
                }};
        
        Properties myDeploymentProperties = getProperties(myPropertyLocations);

        theDbUrl = myDeploymentProperties.getProperty("dbUrl");
        theDbName = myDeploymentProperties.getProperty("dbName");
        theDbDriver = myDeploymentProperties.getProperty("dbDriver");
        theDbUserName = myDeploymentProperties.getProperty("dbUserName");
        theDbPassword = myDeploymentProperties.getProperty("dbPassword");
        assertNotNull(theDbUrl);
    }
    
    // ------------------------------------------------------------------------
    private static Properties getProperties(ArrayList<String> aPropertyLocations)
    {
        Properties myDeploymentProperties = new Properties();

        try
        {
            InputStream myPropertiesStream = null;
            String myLocation = null;
            while(myPropertiesStream == null && !aPropertyLocations.isEmpty())
            {
                myLocation = aPropertyLocations.remove(0);
                File myPropertiesFile = new File(myLocation);
                if (!myPropertiesFile.exists())
                {
                    Logger.logAlways("Didn't find arguments properties at " + myLocation);
                }
                else
                {
                    myPropertiesStream = new FileInputStream(myPropertiesFile);
                }
            }

            assertNotNull("Didn't find deployment properties file.", myPropertiesStream);
            
            Logger.logAlways("Found aruments properties at " + myLocation);
            
            myDeploymentProperties.load(myPropertiesStream);
        } catch (IOException anException)
        {
            throw new ArgumentsException(anException);
        }
        return myDeploymentProperties;
    }
}
