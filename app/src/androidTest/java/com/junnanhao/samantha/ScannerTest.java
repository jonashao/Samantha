package com.junnanhao.samantha;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.junnanhao.samantha.model.entity.Raw;
import com.junnanhao.samantha.workflow.scanner.Scanner;
import com.junnanhao.samantha.workflow.scanner.SmsScanner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ScannerTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        Scanner smsScanner = new SmsScanner(appContext);
        List<Raw> raws = smsScanner.scan();

    }
}
