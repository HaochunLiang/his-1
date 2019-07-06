package edu.neu.his.util;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。线程安全。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String sid="";

    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("new user listening..."+sid+",current users: " + getOnlineCount());
        this.sid=sid;
        try {
            sendMessage("info;连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("one user disconnected, current users: " + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("websocket message from "+sid+":"+message);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //主动推送 uid msg,op操作类型,msg和op不能有;
    public static void sendInfo(String op,String message,@PathParam("sid") String uid) throws IOException {
        System.out.println("send to uid:"+uid+"，websocket msg: "+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                if(item.sid.equals(uid)){
                    item.sendMessage(op+";"+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}