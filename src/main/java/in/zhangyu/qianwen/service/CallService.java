package in.zhangyu.qianwen.service;

import in.zhangyu.qianwen.handler.StreamHandler;

/**
 * CallService 接口定义了调用服务的方法。
 */
public interface CallService {

    /**
     * 调用服务并返回结果。
     *
     * @param prompt 调用的提示信息
     * @return 调用结果
     */
    String call(String prompt);

    /**
     * 使用指定的 API 密钥和模型调用服务并返回结果。
     *
     * @param prompt 调用的提示信息
     * @param apiKey API 密钥
     * @param model  模型名称
     * @return 调用结果
     */
    String call(String prompt, String apiKey, String model);

    /**
     * 使用流式处理方式调用服务。
     *
     * @param prompt  调用的提示信息
     * @param handler 消息处理器
     */
    void streamCall(String prompt, StreamHandler handler);

    /**
     * 使用指定的 API 密钥、模型和流式处理方式调用服务。
     *
     * @param prompt  调用的提示信息
     * @param apiKey  API 密钥
     * @param model   模型名称
     * @param handler 消息处理器
     */
    void streamCall(String prompt, String apiKey, String model, StreamHandler handler);
}
