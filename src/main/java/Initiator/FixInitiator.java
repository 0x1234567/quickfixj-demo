package Initiator;

import quickfix.*;

public class FixInitiator {

    private static SocketInitiator initiator;
    private static SessionSettings settings;
    private static FixInitiatorApplication application;

    public FixInitiator() {
        try {
            // 加载配置文件
            settings = new SessionSettings("quickfix.properties");
        } catch (ConfigError configError) {
            System.out.println("Warning: config error!" + configError);
        }

        // 初始化必要组件
        application = new FixInitiatorApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory(); // 不是quickfix.fix44.MessageFactory
        try {
            initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            System.out.println("Warning: config error! " + configError);
        }
    }

    private void startServer() {
        try {
            initiator.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    private void stopServer() {
        initiator.stop();
    }

    public static void main(String[] args) {
        FixInitiator fixInitiator = new FixInitiator();
        fixInitiator.startServer();

        // 启动一个Session，构造器中第一参数为FIX版本号，无需变动
        // 第二个参数为每个人分配到的账号，请自己修改。另：同一时间段只允许一个相同账号发起连接，请同一个小组的同学进行协调
        // 第三个参数为服务端ID，统一不需要修改
        SessionID sessionID = new SessionID("FIX.4.4", "Wind_K0BGG51c", "QUICKFIX_ACCEPTOR");

        // 开始发消息
        try {
            // 发消息之前建议Sleep一小段时间，确保连接已经建立成功
            Thread.sleep(5000);
            application.sendMarketDataRequest(sessionID);
            Thread.sleep(1000);


        } catch (SessionNotFound | InterruptedException exception) {
            exception.printStackTrace();
        }
    }


}











