package com.glauber.android.xposed.volumesliderdelayfix;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class App implements IXposedHookLoadPackage {

    private static final String PACKAGE_NAME = "com.android.systemui";
    private static final String CLASS_NAME = "com.android.systemui.volume.VolumeDialog";
    private static final String METHOD_NAME = "computeTimeoutH";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals(PACKAGE_NAME))
            return;

        // Original method implementation:
        // https://android.googlesource.com/platform/frameworks/base.git/+/android-6.0.0_r1/packages/SystemUI/src/com/android/systemui/volume/VolumeDialog.java
        findAndHookMethod(CLASS_NAME, lpparam.classLoader, METHOD_NAME, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return 1500; // AudioManager.STREAM_MUSIC timeout
            }
        });

    }
}
