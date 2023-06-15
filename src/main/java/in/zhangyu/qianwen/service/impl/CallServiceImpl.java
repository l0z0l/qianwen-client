package in.zhangyu.qianwen.service.impl;

import com.alibaba.dashscope.common.Protocol;
import com.alibaba.dashscope.conversation.Conversation;
import com.alibaba.dashscope.conversation.ConversationResult;
import com.alibaba.dashscope.conversation.qwen.QWenConversationParam;
import com.alibaba.dashscope.utils.Constants;
import in.zhangyu.qianwen.handler.StreamHandler;
import in.zhangyu.qianwen.service.CallService;
import io.reactivex.Flowable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * CallServiceImpl 实现了 CallService 接口，用于调用服务并处理结果。
 */
@Service
public class CallServiceImpl implements CallService {
    @Value("${dashscope.apiKey}")
    private String apiKey;

    @Value("${dashscope.model}")
    private String model;

    /**
     * {@inheritDoc}
     */
    @Override
    public String call(String prompt) {
        Conversation conversation = buildConversation(false);
        QWenConversationParam param = buildParams(prompt, null, null, false);
        ConversationResult result = conversation.call(param);
        return result.getMessage().getPayload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String call(String prompt, String apiKey, String model) {
        Conversation conversation = buildConversation(false);
        QWenConversationParam param = buildParams(prompt, apiKey, model, false);
        ConversationResult result = conversation.call(param);
        return result.getMessage().getPayload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void streamCall(String prompt, StreamHandler handler) {
        Conversation conversation = buildConversation(true);
        QWenConversationParam param = buildParams(prompt, null, null, true);
        Flowable<ConversationResult> flowable = conversation.streamCall(param);
        flowable.blockingForEach(msg -> handler.handle(msg.getMessage().getPayload()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void streamCall(String prompt, String apiKey, String model, StreamHandler handler) {
        Conversation conversation = buildConversation(true);
        QWenConversationParam param = buildParams(prompt, apiKey, model, true);
        Flowable<ConversationResult> flowable = conversation.streamCall(param);
        flowable.blockingForEach(msg -> handler.handle(msg.getMessage().getPayload()));
    }

    /**
     * 构建 Conversation 对象。
     * @param stream 是否使用流式处理方式
     * @return  Conversation 对象
     */
    private Conversation buildConversation(boolean stream) {
        if (stream) {
            return new Conversation(Protocol.WEBSOCKET.getValue());
        } else {
            return new Conversation(Protocol.HTTP.getValue());
        }
    }

    /**
     * 构建 QWenConversationParam 对象。
     * @param prompt    调用的提示信息
     * @param apiKey    API 密钥
     * @param model     模型名称
     * @param stream    是否使用流式处理方式
     * @return  QWenConversationParam 对象
     */
    private QWenConversationParam buildParams(String prompt, String apiKey, String model, boolean stream) {
        if (StringUtils.isBlank(this.apiKey)) {
            if (StringUtils.isNotBlank(Constants.apiKey)) {
                this.apiKey = Constants.apiKey;
            }
        }
        apiKey = StringUtils.isNoneBlank(apiKey) ? apiKey : this.apiKey;
        model = StringUtils.isNoneBlank(model) ? model : this.model;
        //TODO 参数异常处理，返回自定义BussinessException
        return
                QWenConversationParam.builder().model(model).prompt(prompt)
                        .stream(stream)
                        .apiKey(apiKey)
                        .build();
    }
}
