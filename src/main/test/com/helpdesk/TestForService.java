package com.helpdesk;

import org.junit.*;

/**
 * Created by lokesh on 9/2/16.
 */
public class TestForService {
    @BeforeClass
    private void doThisBeforeClassLoad(){

    }

    @Before
    private void doThisPriorToEveryTest(){

    }

    // @Test (excepted = InvalidParameterException.class)
    @Test
    public void testTheService(){
        //User asserts after calling services to compare actual and expected result
    }

    @After
    private void doThisAfterEveryTest(){

    }

    @AfterClass
    public void doThisAfterUnloadingClass(){

    }
}
