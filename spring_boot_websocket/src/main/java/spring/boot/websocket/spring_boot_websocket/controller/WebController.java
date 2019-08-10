package spring.boot.websocket.spring_boot_websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xcxu
 * @date 2019/8/10
 */
@Controller
@Slf4j
public class WebController {

    @RequestMapping({ "/" })
    public String index(Model model) {
        model.addAttribute("msg", "welcome you!");

        return "ws";
    }

    /**
     * 接收消息
     * @param name 姓名
     * @return welcome, [姓名] !
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getBro")
    public String say(String name) {
        log.info("name: " + name);
        return "welcome, " + name + " !";
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 广播式发送消息给订阅了「/topic/getBro」的客户端
     */
    @RequestMapping("sendMsgBro")
    @ResponseBody
    public void sendMsg() {
        messagingTemplate.convertAndSend("/topic/getBro", "服武器主动推送的广播消息");
    }

    /**
     * 发送消息给指定 sessionId 的客户端, 且该客户端订阅了「/topic/getBro」
     *
     * @param sessionId 客户端的 sessionId
     */
    @RequestMapping("sendMsgPoint")
    @ResponseBody
    public void sendMsgPoint(String sessionId) {
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/getPoint", "服武器主动推送的点对点消息", createHeaders(sessionId));
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

}
