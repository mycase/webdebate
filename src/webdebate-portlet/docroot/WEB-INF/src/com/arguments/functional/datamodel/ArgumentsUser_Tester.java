/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore_Tester;
import com.arguments.support.EmailAddress;
import com.arguments.support.ScreenName;

/**
 * @author mirleau
 *
 */
public class ArgumentsUser_Tester
{
    // ------------------------------------------------------------------------
    public static ArgumentsUser getTestUser2()
    {
        return TheArgsStore_Tester.getTestUser2();
    }

    // ------------------------------------------------------------------------
    public static ArgumentsUser getTestUser7()
    {
        return TheArgsStore_Tester.getTestUser7();
    }

    // ------------------------------------------------------------------------
    public static EmailAddress getMailAddress(int aTestUserId)
    {
        return new EmailAddress("test_" + aTestUserId + "@provider" + aTestUserId + ".com");
    }

    // ------------------------------------------------------------------------
    public static ScreenName getScreenName(int aTestUserId)
    {
        return new ScreenName("testscreen" + aTestUserId);
    }

    // ------------------------------------------------------------------------
    public static ForeignUserId getForeignUserId(int aTestUserId)
    {
        return new ForeignUserId("" + (10000 + aTestUserId));
    }
}
