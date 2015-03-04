/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
#import "CDVDevice.h"


@implementation CDVDevice

@synthesize callbackId;
@synthesize notificationMessage;
@synthesize isInline;
@synthesize token;

- (void)getPushID:(CDVInvokedUrlCommand*)command
{
    NSLog(@"getPushID received");
    
	NSMutableDictionary *results = [NSMutableDictionary dictionary];
	[results setValue:self.token forKey:@"id"];
	
  CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:results];
  [self.commandDelegate sendPluginResult:commandResult callbackId:command.callbackId];
}

- (void)setCallback:(CDVInvokedUrlCommand*)command
{
	self.callbackId = command.callbackId;

#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 80000
		UIUserNotificationType UserNotificationTypes = UIUserNotificationTypeNone | UIUserNotificationTypeBadge | UIUserNotificationTypeSound | UIUserNotificationTypeAlert | UIUserNotificationActivationModeBackground;
		if ([[UIApplication sharedApplication]respondsToSelector:@selector(registerUserNotificationSettings:)]) {
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:UserNotificationTypes categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    } else {
    		[[UIApplication sharedApplication] registerForRemoteNotificationTypes:notificationTypes];
    }
#else
    UIRemoteNotificationType notificationTypes = UIRemoteNotificationTypeNone | UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeNewsstandContentAvailability;
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:notificationTypes];
#endif

    isInline = NO;

	[self notificationReceived];			// if there is a pending startup notification, go ahead and process it

}

- (void)didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
    NSLog(@"didRegisterForRemoteNotificationsWithDeviceToken");


    NSMutableDictionary *results = [NSMutableDictionary dictionary];
    self.token = [[[[deviceToken description] stringByReplacingOccurrencesOfString:@"<"withString:@""]
                        stringByReplacingOccurrencesOfString:@">" withString:@""]
                       stringByReplacingOccurrencesOfString: @" " withString: @""];
    [results setValue:@"pushid" forKey:@"evt"];
    [results setValue:self.token forKey:@"data"];

		[self successWithMessage:results];
}

- (void)didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
        NSLog(@"didFailToRegisterForRemoteNotificationsWithError");
	//[self failWithMessage:nil withError:error];
}

- (void)notificationReceived {

    if (notificationMessage && self.callbackId)
    {
        NSLog(@"Notification received");
        
        if (isInline)
        {
        	[self.notificationMessage setValue:@"1" forKey:@"foreground"];
            isInline = NO;
        }
				else
				{
            [self.notificationMessage setValue:@"0" forKey:@"foreground"];
        }
				
				[self successWithMessage:self.notificationMessage];

        self.notificationMessage = nil;
    }
}

-(void)successWithMessage:(NSDictionary *)message
{
    if (self.callbackId != nil)
    {
        CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:message];
        [commandResult setKeepCallbackAsBool:YES];
        [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
    }
}

-(void)failWithMessage:(NSDictionary *)message withError:(NSError *)error
{
	if(!message){
		message = [NSMutableDictionary dictionary];
	}
	if(error){
		[message setValue:[error localizedDescription] forKey:@"desc"];
	}
  CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:message];
	[commandResult setKeepCallbackAsBool:YES];
  [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
}

/*
- (void)getDeviceInfo:(CDVInvokedUrlCommand*)command
{
    NSDictionary* deviceProperties = [self deviceProperties];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:deviceProperties];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSDictionary*)deviceProperties
{
    UIDevice* device = [UIDevice currentDevice];
    NSMutableDictionary* devProps = [NSMutableDictionary dictionaryWithCapacity:4];

    [devProps setObject:[device modelVersion] forKey:@"model"];
    [devProps setObject:@"iOS" forKey:@"platform"];
    [devProps setObject:[device systemVersion] forKey:@"version"];
    [devProps setObject:[device uniqueAppInstanceIdentifier] forKey:@"uuid"];
    [devProps setObject:[[self class] cordovaVersion] forKey:@"cordova"];

    NSDictionary* devReturn = [NSDictionary dictionaryWithDictionary:devProps];
    return devReturn;
}

+ (NSString*)cordovaVersion
{
    return CDV_VERSION;
}
*/
@end
