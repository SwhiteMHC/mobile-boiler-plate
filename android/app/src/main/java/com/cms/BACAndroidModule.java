package com.cms; // replace your-apps-package-name with your app’s package name

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.Arguments;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Promise;

import androidx.annotation.Nullable;

import BACtrackAPI.API.BACtrackAPI;
import BACtrackAPI.API.BACtrackAPICallbacks;
import BACtrackAPI.Constants.BACTrackDeviceType;
import BACtrackAPI.Constants.BACtrackUnit;
import BACtrackAPI.Exceptions.LocationServicesNotEnabledException;
import BACtrackAPI.Constants.Errors;
import BACtrackAPI.Exceptions.BluetoothLENotSupportedException;
import BACtrackAPI.Exceptions.BluetoothNotEnabledException;


public class BACAndroidModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mCurrentContext = null;

    BACAndroidModule(ReactApplicationContext context) {
        super(context);
        mCurrentContext = context;
    }

    private BACtrackAPI mAPI;

    @Override
    public String getName() {
        return "BACAndroidModule";
    }

    @ReactMethod
    protected void initBacTrackAPI(String apiKey, Promise promise) {
        try {
            mAPI = new BACtrackAPI(getReactApplicationContext(), mCallbacks, apiKey);
            Log.d("BACModule", "LOG and INIT Test");
            promise.resolve(mAPI.toString());
        } catch (BluetoothLENotSupportedException e) {
            e.printStackTrace();
            Log.d("BACModule", "BluetoothLENotSupportedException");
            promise.reject("BluetoothLENotSupportedException", e);
        } catch (BluetoothNotEnabledException e) {
            e.printStackTrace();
            Log.d("BACModule", "BluetoothNotEnabledException");
            promise.reject("BluetoothNotEnabledException", e);
        } catch (LocationServicesNotEnabledException e) {
            e.printStackTrace();
            Log.d("BACModule", "LocationServicesNotEnabledException");
            promise.reject("LocationServicesNotEnabledException", e);
        }
    }

    @ReactMethod
    protected void connectToNearestBreathalyzer(Promise promise) {
        try {
            mAPI.connectToNearestBreathalyzer();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Connect to Breathalyzer Failure", e);
        }
    }

    @ReactMethod
    protected void disconnect(Promise promise) {
        try {
            mAPI.disconnect();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Disconnect Failure", e);
        }
    }

    @ReactMethod
    protected void getBreathalyzerBatteryLevel(Promise promise) {
        try {
            mAPI.getBreathalyzerBatteryVoltage();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Error trying to retrieve battery level from breathalyzer", e);
        }
    }

    @ReactMethod
    protected void getBreathalyzerSerialNumber(Promise promise) {
        try {
            mAPI.getSerialNumber();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Error trying to retrieve serial number from breathalyzer", e);
        }
    }

    @ReactMethod
    protected void getUseCount(Promise promise) {
        try {
            mAPI.getUseCount();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Error trying to retrieve use count from breathalyzer", e);
        }
    }

    @ReactMethod
    protected void startCountdown(Promise promise) {
        try {
            mAPI.startCountdown();
            promise.resolve(true);
        } catch(Error e) {
            promise.reject("Error starting test flow with breathalyzer", e);
        }
    }


    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           String params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private int listenerCount = 0;

    @ReactMethod
    public void addListener(String eventName) {
        if (listenerCount == 0) {
            // Set up any upstream listeners or background tasks as necessary
        }

        listenerCount += 1;
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        listenerCount -= count;
        if (listenerCount == 0) {
            // Remove upstream listeners, stop unnecessary background tasks
        }
    }

    private final BACtrackAPICallbacks mCallbacks = new BACtrackAPICallbacks() {

        @Override
        public void BACtrackAPIKeyDeclined(String errorMessage) {
            Log.d("BACModule", "BacTrackAPIKeyDeclined");

        }

        @Override
        public void BACtrackAPIKeyAuthorized() {
            Log.d("BACModule", "BacTrackAPIKeyAuthorized");

        }

        @Override
        public void BACtrackConnected(BACTrackDeviceType bacTrackDeviceType) {
            Log.d("BACModule", "BacTrackConnected");

        }

        @Override
        public void BACtrackDidConnect(String s) {
            Log.d("BACModule", "BacTrackDidConnect");
        }

        @Override
        public void BACtrackDisconnected() {
            Log.d("BACModule", "BacTrackDisconnected");

        }
        @Override
        public void BACtrackConnectionTimeout() {
            Log.d("BACModule", "BacTrackConnectionTimeout");

        }

        @Override
        public void BACtrackFoundBreathalyzer(BACtrackAPI.BACtrackDevice device) {
            Log.d("BACModule", "BacTrackFoundBreathalyzer");
        }

        @Override
        public void BACtrackCountdown(int currentCountdownCount) {
            try {
                Log.d("BACModule", "BacTrackCountdown");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(currentCountdownCount));
                sendEvent(mCurrentContext, "BacTrackCountdown", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackStart() {
            try {
                Log.d("BACModule", "BacTrackStart");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", "success");
                sendEvent(mCurrentContext, "BacTrackStart", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackBlow(float breathVolumeRemaining) {
            try {
                Log.d("BACModule", "BacTrackBlow");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(breathVolumeRemaining));
                sendEvent(mCurrentContext, "BacTrackBlow", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackAnalyzing() {
            try {
                Log.d("BACModule", "BacTrackAnalyzing");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", "success");
                sendEvent(mCurrentContext, "BacTrackAnalyzing", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackResults(float measuredBac) {
            try {
                Log.d("BACModule", "BacTrackResults");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(measuredBac));
                sendEvent(mCurrentContext, "BacTrackResults", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackFirmwareVersion(String version) {
            Log.d("BACModule", "BacTrackFirmwareVersion");
        }

        @Override
        public void BACtrackSerial(String serialHex) {
            Log.d("BACModule", "BacTrackSerial");
            try {
                Log.d("BACModule", "BacTrackSerial");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", serialHex);
                sendEvent(mCurrentContext, "BacTrackSerial", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackUseCount(int useCount) {
            try {
                Log.d("BACModule", "BacTrackUseCount");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(useCount));
                sendEvent(mCurrentContext, "BacTrackUseCount", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackBatteryVoltage(float voltage) {
            try {
                Log.d("BACModule", "BacTrackBatteryVoltage");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(voltage));
                sendEvent(mCurrentContext, "BacTrackBatteryVoltage", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackBatteryLevel(int level) {
            Log.d("BACModule", "BacTrackBatteryLevel");
            try {
                Log.d("BACModule", "BacTrackBatteryLevel");
                WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                params.putString("status", String.valueOf(level));
                sendEvent(mCurrentContext, "BacTrackBatteryLevel", params.toString());
            } catch (Exception e) {
                Log.e("BACModule", "Exception:", e);
            }
        }

        @Override
        public void BACtrackError(int errorCode) {

            switch (errorCode)
            {
                case Errors.ERROR_TIME_OUT:
                    try {
                        Log.d("BACModule", "ERROR_TIME_OUT");
                        WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                        params.putString("status", "ERROR_TIME_OUT");
                        sendEvent(mCurrentContext, "BacTrackError", params.toString());
                    } catch (Exception e) {
                        Log.e("BACModule", "Exception:", e);
                    }
                    break;
                case Errors.ERROR_BLOW_ERROR:
                    try {
                        Log.d("BACModule", "ERROR_BLOW_ERROR");
                        WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                        params.putString("status", "ERROR_BLOW_ERROR");
                        sendEvent(mCurrentContext, "BacTrackError", params.toString());
                    } catch (Exception e) {
                        Log.e("BACModule", "Exception:", e);
                    }
                    break;
                case Errors.ERROR_LOW_BATTERY:
                    try {
                        Log.d("BACModule", "ERROR_LOW_BATTERY");
                        WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                        params.putString("status", "ERROR_LOW_BATTERY");
                        sendEvent(mCurrentContext, "BacTrackError", params.toString());
                    } catch (Exception e) {
                        Log.e("BACModule", "Exception:", e);
                    }
                    break;
                case Errors.ERROR_MAX_BAC_EXCEEDED_ERROR:
                    try {
                        Log.d("BACModule", "ERROR_MAX_BAC_EXCEEDED_ERROR");
                        WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                        params.putString("status", "ERROR_MAX_BAC_EXCEEDED_ERROR");
                        sendEvent(mCurrentContext, "BacTrackError", params.toString());
                    } catch (Exception e) {
                        Log.e("BACModule", "Exception:", e);
                    }
                    break;
                default:
                    Log.d("BACModule", "ERROR_DEFAULT");
                    try {
                        Log.d("BACModule", "ERROR_DEFAULT");
                        WritableNativeMap params = (WritableNativeMap) Arguments.createMap();
                        params.putString("status", "ERROR_DEFAULT");
                        sendEvent(mCurrentContext, "BacTrackError", params.toString());
                    } catch (Exception e) {
                        Log.e("BACModule", "Exception:", e);
                    }
            }
        }

        @Override
        public void BACtrackUnits(BACtrackUnit units) {

        }
    };
}

