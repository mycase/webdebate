package com.arguments;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.arguments.application.ContainerException_Tester;
import com.arguments.application.TheContainerBridge_Tester;
import com.arguments.functional.datamodel.Perspective_Tester;
import com.arguments.functional.datamodel.RelatedThesis_Tester;
import com.arguments.functional.report.ThesisFocusData_Tester;
import com.arguments.functional.report.html.ThesisFocusPage_Tester;
import com.arguments.functional.report.pagemodels.AddPremisePageModel_Tester;
import com.arguments.functional.report.pagemodels.PageModel_Tester;
import com.arguments.functional.report.pagemodels.PageRenderer_Tester;
import com.arguments.functional.requeststate.ArgsStatefulRequest_Tester;
import com.arguments.functional.requeststate.Macro_Tester;
import com.arguments.functional.requeststate.PortalArgsBridge_Tester;
import com.arguments.functional.requeststate.asstrings.ArgsRequestKeyAsString_Tester;
import com.arguments.functional.store.TheArgsStore_Tester;
import com.arguments.support.CollectionTransforms_Tester;
import com.arguments.support.Container_Tester;
import com.arguments.support.IndentHtmlPrinter_Tester;
import com.arguments.support.Logger_Tester;
import com.arguments.support.TreeHandler_Tester;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // application
    TheContainerBridge_Tester.class,
    ContainerException_Tester.class,
    // application/liferay
    // functional/report/html
    ThesisFocusPage_Tester.class,
    // functional/report/pagemodels
    AddPremisePageModel_Tester.class,
    PageModel_Tester.class,
    PageRenderer_Tester.class,
    ThesisFocusData_Tester.class,
    // functional/requeststate
    Macro_Tester.class,
    ArgsRequestKeyAsString_Tester.class,
    ArgsStatefulRequest_Tester.class,
    PortalArgsBridge_Tester.class,
    //functional/store
    TheArgsStore_Tester.class,
    // functional/datamodel
    Perspective_Tester.class,
    RelatedThesis_Tester.class,
    // support
    CollectionTransforms_Tester.class,
    Container_Tester.class,
    IndentHtmlPrinter_Tester.class,
    Logger_Tester.class,
    TreeHandler_Tester.class,
})

public class TestSuite
{
}
