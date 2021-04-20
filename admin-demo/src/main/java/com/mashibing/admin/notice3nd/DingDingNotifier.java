package com.mashibing.admin.notice3nd;

import com.alibaba.fastjson.JSONObject;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Map;

/***********************
 * @Description: 钉钉通知类 <BR>
 * @author: zhao.song
 * @since: 2021/4/20 22:01
 * @version: 1.0
 ***********************/
public class DingDingNotifier extends AbstractStatusChangeNotifier {

    @Autowired
    private DingDingMessageUtil dingDingMessageUtil;

    public DingDingNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        String serviceName = instance.getRegistration().getName();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        String status = instance.getStatusInfo().getStatus();
        System.out.printf("serviceName:%s,serviceUrl:%s,Status:%s\n"
                , serviceName, serviceUrl, status);
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("系统警告:%s", serviceName))
                .append(String.format("【服务地址:%s】", serviceUrl))
                .append(String.format("【状态:%s】", status))
                .append(String.format("【详情:%s】", JSONObject.toJSONString(details)));
        return Mono.fromRunnable(() -> dingDingMessageUtil.sendTextMessage(sBuilder.toString()));
    }
}
