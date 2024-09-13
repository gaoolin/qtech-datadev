package com.qtech.check.processor.handler;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:06:16
 * desc   :  这个接口的泛型逻辑涉及到了两个不同的泛型参数：T和R，它们各自服务于不同的目的：
 * 泛型参数T: 定义在接口级别，代表了MessageHandler接口处理的消息的具体类型。这意味着每个实现了MessageHandler<T>接口的类，都需要指定一个具体的类型T，这个类型将贯穿整个接口的所有方法，除了那些明确覆盖它的方法。例如，在supportsType(Class<T> clazz)方法中，T用来确保传入的Class参数与处理器所支持的消息类型相匹配。
 * 泛型参数R: 定义在handleByType方法级别，表示该方法根据传入的Class<R>参数处理消息后返回的类型。这里R是一个局部泛型参数，仅在handleByType方法的上下文中有效。它允许方法返回与传入的类类型相匹配的结果，增强了灵活性。例如，如果传入的是EmailMessage.class，则handleByType可以返回一个EmailMessage对象，R在这里就是EmailMessage类型。
 * 总结来说，T是接口的泛型类型参数，代表了消息处理器处理的消息的类型；而R是方法内的泛型类型参数，用于handleByType方法的返回类型，根据传入的Class<R>动态确定。这样的设计使得接口既能够处理特定类型的消息（通过T），又能灵活地根据不同的类型需求返回处理结果（通过R）。
 */


public abstract class MessageHandler<T> implements QtechBaseHandler<T> {

    public void handle(String msg) {
    }
}

