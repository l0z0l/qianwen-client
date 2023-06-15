package in.zhangyu.qianwen.handler;

/**
 * StreamHandler 接口定义了处理消息流的处理器。
 */
public interface StreamHandler {
    /**
     * 处理给定的消息。
     *
     * @param message 要处理的消息
     */
    void handle(String message);
}
