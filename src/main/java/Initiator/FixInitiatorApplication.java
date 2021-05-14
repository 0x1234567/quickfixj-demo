package Initiator;

import field.TDBeginDate;
import field.TDEndDate;
import quickfix.*;
import quickfix.field.MDReqID;
import quickfix.field.SubscriptionRequestType;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.MessageCracker;

public class FixInitiatorApplication extends MessageCracker implements Application {

    // 以下是Application的固定七件套
    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println("onCreate is called");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("onLogon is called");
    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("onLogout is called");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("toAdmin is called");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("fromAdmin is called");
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        System.out.println("toApp is called: " + message);
    }

    /*
        当请求发送成功之后收到服务端返回的数据后会调用此方法
     */
    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("fromApp is called");
        crack(message, sessionId);
    }

    @Override
    public void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("Received TDFData: " + message + ", sessionID: " + sessionID);

    }


    // 以下是发消息的功能

    /**
     * 向服务端发起行情数据获取请求
     *
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendMarketDataRequest(SessionID sessionID) throws SessionNotFound {
        // 具体set哪些字段，参考你的FIX44.modified.xml
        // 以下演示发送历史数据请求
        // 注意：只能在9:00-15:00之间发送实时行情请求，其余时间可以发送历史数据请求或请求回放当天数据
        MarketDataRequest req = new MarketDataRequest();
        req.set(new MDReqID("mockedMDReqID"));
        // SubscriptionRequestType参数:
        // 0-请求回放当天行情数据
        // 1-请求实时行情数据
        // 2-请求历史行情数据
        req.set(new SubscriptionRequestType('2'));
        // 当请求历史行情数据时需要设置历史数据的时间范围，请求其他类型的数据时可以忽略
        req.setField(9051, new TDBeginDate(20201101));
        req.setField(9052, new TDEndDate(20201105));

        System.out.println("Sending MarketDataRequest");
        Session.sendToTarget(req, sessionID);
    }


}
