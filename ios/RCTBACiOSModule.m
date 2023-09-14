//
//  RCTBACiOSModule.m
//  acms
//
//  Created by Spencer White on 8/28/23.
//

// RCTCalendarModule.m
#import "RCTBACiOSModule.h"
#import <React/RCTLog.h>
#import "BACtrack.h"


@interface RCTBACiOSModule () <BacTrackAPIDelegate>
{
    BacTrackAPI * mBacTrack;
}
@end

@implementation RCTBACiOSModule {
  bool hasListeners;
}

BacTrackAPI * mBacTrack;

// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"BacTrackConnected", @"BacTrackDisconnected", @"BactrackAPIKeyDeclined", @"BacTrackError", @"BacTrackBatteryLevel", @"BacTrackBatteryVoltage", @"BacTrackSerial", @"BacTrackUseCount", @"BacTrackCountdown", @"BacTrackError", @"BacTrackStart", @"BacTrackBlow", @"BacTrackAnalyzing", @"BacTrackResults"];
}

RCT_EXPORT_METHOD(initBacTrackAPI:(NSString *)apiKey resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  @try {
    mBacTrack = [[BacTrackAPI alloc] initWithDelegate:self AndAPIKey:apiKey];
    resolve(@"Successful Init of BacTrackAPI");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Unable to Init API", error);
  }
  
}

RCT_EXPORT_METHOD(BacTrackAPIKeyAuthorized) {
  
}

RCT_EXPORT_METHOD(connectToNearestBreathalyzer:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  @try {
    [mBacTrack connectToNearestBreathalyzer];
    resolve(@"Successfully Started Scan for BacTrack Device");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Error tying to connect to breathalyzer", error);
  }
}

RCT_EXPORT_METHOD(disconnect:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  @try {
    [mBacTrack disconnect];
    resolve(@"Successfully Started Disconnect of BacTrack Device");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Error trying to disconnect from breathalyzer", error);
  }
}

RCT_EXPORT_METHOD(getBreathalyzerBatteryLevel:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  @try {
    [mBacTrack getBreathalyzerBatteryLevel];
    resolve(@"Successfully Started Battery Level Retrieval of BacTrack Device");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Error trying to retrieve battery level from breathalyzer", error);
  }
}

RCT_EXPORT_METHOD(getBreathalyzerSerialNumber:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  @try {
    [mBacTrack getBreathalyzerSerialNumber];
    resolve(@"Successfully Started Serial Number Retrieval of BacTrack Device");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Error trying to retrieve serial number from breathalyzer", error);
  }
}

RCT_EXPORT_METHOD(startCountdown:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
  @try {
    [mBacTrack startCountdown];
    resolve(@"Successfully started countdown on breathalyzer");
  }
  @catch (NSError *error) {
    reject(@"BAC Error", @"Error starting test flow with breathalyzer", error);
  }
}

-(void)BacTrackConnected:(BACtrackDeviceType)device
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackConnected" body:@{@"status": @"success"}];
   }
}

-(void)BacTrackDisconnected
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackDisconnected" body:@{@"status": @"success"}];
   }
}

-(void)BacTrackAPIKeyDeclined:(NSString *)errorMessage
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackAPIKeyDeclined" body:@{@"status": errorMessage}];
   }
}

-(void)BacTrackError:(NSError*)error
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackError" body:@{@"status": error.description}];
   }
}

-(void)BacTrackBatteryLevel:(NSNumber *)number
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackBatteryLevel" body:@{@"status": [number stringValue]}];
   }
}

-(void)BacTrackBatteryVoltage:(NSNumber *)number
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackBatteryVoltage" body:@{@"status": [number stringValue]}];
   }
}

-(void)BacTrackSerial:(NSString *)serial_hex
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackSerial" body:@{@"status": serial_hex}];
   }
}

-(void)BacTrackUseCount:(NSNumber *)count {
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackUseCount" body:@{@"status": [count stringValue]}];
   }
}

-(void)BacTrackCountdown:(NSNumber*)seconds executionFailure:(BOOL)error {
  
  if (error) {
    [self sendEventWithName:@"BacTrackError" body:@{@"status": @"failure"}];
    return;
  }
  
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackCountdown" body:@{@"status": [seconds stringValue]}];
   }
}

-(void)BacTrackStart {
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackStart" body:@{@"status": @"success"}];
   }
}

-(void)BacTrackBlow:(NSNumber *)breathFractionRemaining
{
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackBlow" body:@{@"status": [breathFractionRemaining stringValue]}];
   }
}

-(void)BacTrackAnalyzing {
  if (hasListeners) {// Only send events if anyone is listening
     [self sendEventWithName:@"BacTrackAnalyzing" body:@{@"status": @"success"}];
   }
}

-(void)BacTrackResults:(CGFloat)bac {
  if (hasListeners) {// Only send events if anyone is listening
     NSString *myString = [[NSNumber numberWithFloat:bac] stringValue];
     [self sendEventWithName:@"BacTrackResults" body:@{@"status": myString}];
   }
}

@end