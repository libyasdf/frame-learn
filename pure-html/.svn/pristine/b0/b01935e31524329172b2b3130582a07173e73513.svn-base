package com.sinosoft.sinoep.message.websocket.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.services.MessageService;

@Component("webSocketService")
@Service
public class MyHandler extends TextWebSocketHandler implements WebSocketHandler {

    private static Logger WEBSCOKETLOG = Logger.getLogger("WEBSCOKET");

    private static Logger log = Logger.getLogger(MyHandler.class);

    private static final Map<String, WebSocketSession> users;

    /**
     * 用户标识
     */
    private static final String DUN_ID = "userId";

    private static int onlineCount = 0;

    private Timer timer = null;

    /**
     * 发送信息结果通知
     */
    private boolean sendMessageToAllUsers;

    private static String STATUS = "sortOfTime";

    @Autowired
    private NotifyController notifyController;

    @Autowired
    private MessageService messageService;

    // 类加载初始化一个map集合，存放用户的websocket对象
    static {
        users = new HashMap<>();
    }

    public static synchronized void addOnlineCount(String userId, WebSocketSession session) {
        if (!users.containsKey(userId)) {
            MyHandler.onlineCount++;
            users.put(userId, session);
            WEBSCOKETLOG.info("当前人数    加的：" + onlineCount);
        }
    }

    public static synchronized void delOnlineCount(String userId) {
        if (users.containsKey(userId)) {
            MyHandler.onlineCount--;
            WEBSCOKETLOG.info("当前人数    减的：" + onlineCount);
        }

    }

    /**
     * 获取用户标识,获取websocekt对象的map集合
     *
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            // 获取存入websocket的userid
            String clientId = (String) session.getAttributes().get(DUN_ID);
            return clientId;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            WEBSCOKETLOG.info(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 成功建立连接触发的方法，
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 取出拦截器放入的dunID，为当前的websoket绑定用户到map
        String userId = getClientId(session);
        WEBSCOKETLOG.info(userId + "：链接获得id");
        if (userId != null) {
            addOnlineCount(userId, session);
            try {
                if (onlineCount == 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            //当前时间
                            WEBSCOKETLOG.info("websocket正在运行");
                            try {
                                sendMessageToAllUsers = sendMessageToAllUsers();
                            } catch (Exception e) {
                                WEBSCOKETLOG.info("推送消息过程中有用户加入或退出");
                                e.printStackTrace();
                            }
                            WEBSCOKETLOG.info("当次消息发送结果为：" + sendMessageToAllUsers);
                        }
                    }, 0, Integer.parseInt(ConfigConsts.WEBSOCKET_TIMER));
                }
            } catch (Exception e) {
                WEBSCOKETLOG.info("定时器出错", e);
                e.printStackTrace();
            }
            WEBSCOKETLOG.info(userId);
            WEBSCOKETLOG.info(session);
            WEBSCOKETLOG.info("当前在线人数" + onlineCount);
            WEBSCOKETLOG.info("当前在线不重复人数" + users.size());
        }
    }

    /**
     * 当接收到客户端浏览器后接收的方法
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
        TextMessage textMessage = new TextMessage(message.getPayload());
        if ("ping".equals(message.getPayload())) {
            try {
                session.sendMessage(textMessage);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        if ("sortOfTime".equals(message.getPayload())) {
            STATUS = "sortOfTime";
            return;
        }
        if ("sortOfRead".equals(message.getPayload())) {
            STATUS = "sortOfRead";
            return;
        }
        notifyController.edit(message.getPayload());
        WEBSCOKETLOG.info("消息已刷新");
    }

    /**
     * 发送信息给指定用户
     *
     * @param dunId
     * @param message
     * @return map中根据用户的id获取对应得websoket，发送信息
     */
    public boolean sendMessageToUser(String dunId, TextMessage message) {
        // 获取当前的
        if (users.get(dunId) == null) {
            return false;
        }
        WebSocketSession session = users.get(dunId);
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            WEBSCOKETLOG.info(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息（发送给所有人）
     *
     * @return 遍历出map中所有的websocket发送在线消息
     */
    public boolean sendMessageToAllUsers() {
        WEBSCOKETLOG.info("=============================推送开始=========================");
        boolean allSendSuccess = true;
        Set<String> dunIds = users.keySet();
        WEBSCOKETLOG.info("当前推送人数：" + dunIds.size());
        WebSocketSession session = null;
        if ("sortOfTime".equals(STATUS)) {
            try {
                for (String dunId : dunIds) {

                    List<NotityMessage> todayMsg = messageService.getTodayMsg(dunId);
                    session = users.get(dunId);
                    WEBSCOKETLOG.info("当前人为：" + getClientId(session));
                    if (session.isOpen()) {
                        TextMessage message2 = new TextMessage(JSON.toJSONString(todayMsg));
                        session.sendMessage(message2);
                        WEBSCOKETLOG.info("当前人成功推送：" + getClientId(session));
                        message2 = null;
                    }

                }
            } catch (IOException e) {
                WEBSCOKETLOG.info("ioe=======" + e.getMessage());
                e.printStackTrace();
                allSendSuccess = false;
            } catch (Exception e1) {
                e1.printStackTrace();
                WEBSCOKETLOG.info("最大=======" + e1.getMessage());
                allSendSuccess = false;
            }
        } else if ("sortOfRead".equals(STATUS)) {

        }
        WEBSCOKETLOG.info("=============================推送结束=========================");
        return allSendSuccess;
    }

    /**
     * 当链接发生异常后触发的方法，关闭出错会话的连接，和删除在Map集合中的记录
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

        WEBSCOKETLOG.info("消息传送错误！当前用户为" + getClientId(session));
        WEBSCOKETLOG.info("消息传送错误" + exception);
        // 判断当前的链接是否在继续，关闭连接
//		if (session.isOpen()) {
//			try {
//				session.close();
//			} catch (IOException e) {
//				WEBSCOKETLOG.info("连接出错");
//				WEBSCOKETLOG.info(e.getMessage());
//				e.printStackTrace();
//			} finally {
//				users.remove(getClientId(session));
//				delOnlineCount(getClientId(session));
//			}
//		} 

//		else {//用户关闭状态
//			try {
//				session.close();
//			} catch (IOException e) {
//				WEBSCOKETLOG.info(e.getMessage());
//				e.printStackTrace();
//			} finally {
//				delOnlineCount(getClientId(session));
//				users.remove(getClientId(session));
//			}
//		}

    }

    /**
     * 当链接关闭后触发的方法，连接已关闭，移除在Map集合中的记录。
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 当前的状态码，并删除存储在map中的websocket的链接对象
        delOnlineCount(getClientId(session));
        if (onlineCount <= 0) {
            timer.cancel();
            WEBSCOKETLOG.info("定时器已经关闭");
            onlineCount = 0;
        }
        WEBSCOKETLOG.info("连接已关闭：" + status);

        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
