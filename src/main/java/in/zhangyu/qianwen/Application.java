package in.zhangyu.qianwen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        AtomicInteger head_idx = new AtomicInteger(0);
//        context.getBean(CallService.class).streamCall("你好", null, null, new StreamHandler() {
//            @Override
//            public void handle(String payload) {
//                System.out.print("\r" + payload.substring(head_idx.get()));
//                System.out.flush();
//                if (payload.lastIndexOf("\n") != -1) {
//                    head_idx.set(payload.lastIndexOf("\n") + 1);
//                }
//            }
//        });
    }
}
