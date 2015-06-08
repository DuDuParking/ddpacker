//
//  pgwxpay.h
//  pgtest
//
//  Created by breadth on 14-7-31.
//
//

#import "Pgwxpay.h"
#import "payRequsestHandler.h"
#import "WXApi.h"
#import <QuartzCore/QuartzCore.h>


@implementation Pgwxpay

@synthesize callbackId;

NSString *timeStamp;
NSString *nonceStr;
NSString *traceId;

static int lastErr = 0;
static Pgwxpay *wxpay = nil;

#pragma mark - 主体流程
// 获取token

+(void)onResult:(int)code{
    if(wxpay != nil){
        [wxpay onResult:code];
    }
}

-(void)onResult:(int)code{
	lastErr = code;
	if (self.callbackId != nil)
  {
  	NSMutableDictionary *results = [NSMutableDictionary dictionary];
		[results setValue:@(lastErr) forKey:@"code"];
		
      CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:results];
      [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
      
      self.callbackId = nil;
  }
}

-(void)getcode:(CDVInvokedUrlCommand*)command{
	
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:lastErr];
  [self.commandDelegate sendPluginResult:commandResult callbackId:command.callbackId];
}
	
//检查是否安装了微信
- (void)iswx:(CDVInvokedUrlCommand *)command{
    
    BOOL iswx =  [WXApi isWXAppInstalled];
    CDVPluginResult* pluginResult = nil;
    
    if (iswx) {
        
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"1"];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        
    } else {
        
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"0"];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        
    }
    
}

- (void)wxpay2:(CDVInvokedUrlCommand *)command{
    if(wxpay == nil){
        wxpay = self;
    }
    
    NSDictionary* args = [command.arguments objectAtIndex:0];
    
    //调起微信支付
    PayReq* req             = [[[PayReq alloc] init]autorelease];
    req.openID              = [args objectForKey:@"appid"];
    req.partnerId           = [args objectForKey:@"partnerid"];
    req.prepayId            = [args objectForKey:@"prepayid"];
    req.nonceStr            = [args objectForKey:@"noncestr"];
    NSMutableString *stamp  = [args objectForKey:@"timestamp"];
    req.timeStamp           = stamp.intValue;
    req.package             = [args objectForKey:@"package"];
    req.sign                = [args objectForKey:@"sign"];
    
    self.callbackId = command.callbackId;
    [WXApi sendReq:req];
}

- (void)wxpay:(CDVInvokedUrlCommand *)command{
    if(wxpay == nil){
        wxpay = self;
    }

   NSDictionary* args = [command.arguments objectAtIndex:0];
    
   NSString* out_trade_no = [args objectForKey:@"out_trade_no"];
   NSString* url = [args objectForKey:@"url"];
   NSString* bodtxt = [args objectForKey:@"bodtxt"];
   NSString* total_fee = [args objectForKey:@"total_fee"];
 
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:out_trade_no forKey:@"out_trade_no"];
    [userDefaults setObject:url forKey:@"url"];
    [userDefaults setObject:bodtxt forKey:@"bodtxt"];
    [userDefaults setObject:total_fee forKey:@"total_fee"];

    
    //创建支付签名对象
    payRequsestHandler *req = [[payRequsestHandler alloc] autorelease];
    //初始化支付签名对象
    [req init:APP_ID mch_id:MCH_ID];
    //设置密钥
    [req setKey:PARTNER_key];
    
 
    
    //获取到实际调起微信支付的参数后，在app端调起支付
    NSMutableDictionary *dict = [req sendPay];
    
    if(dict == nil){
        //错误提示
        NSString *debug = [req getDebugifo];
        
        //[self alert:@"提示信息" msg:debug];
        
        NSLog(@"%@\n\n",debug);
    }else{
        NSLog(@"%@\n\n",[req getDebugifo]);
        //[self alert:@"确认" msg:@"下单成功，点击OK后调起支付！"];
        
        NSMutableString *stamp  = [dict objectForKey:@"timestamp"];
        
        //调起微信支付
        PayReq* req             = [[[PayReq alloc] init]autorelease];
        req.openID              = [dict objectForKey:@"appid"];
        req.partnerId           = [dict objectForKey:@"partnerid"];
        req.prepayId            = [dict objectForKey:@"prepayid"];
        req.nonceStr            = [dict objectForKey:@"noncestr"];
        req.timeStamp           = stamp.intValue;
        req.package             = [dict objectForKey:@"package"];
        req.sign                = [dict objectForKey:@"sign"];
        
        self.callbackId = command.callbackId;
        [WXApi sendReq:req];
    }



}

//客户端提示信息
- (void)alert:(NSString *)title msg:(NSString *)msg
{
    UIAlertView *alter = [[UIAlertView alloc] initWithTitle:title message:msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
    
    [alter show];
    [alter release];
}




  
@end
 
