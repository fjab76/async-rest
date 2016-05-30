package fjab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by franciscoalvarez on 28/05/2016.
 */
@RestController
@RequestMapping("/")
public class MyController {

    Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/sync", method = GET)
    public String getSyncResource() throws InterruptedException {
        logger.info("ThreadName-" + Thread.currentThread().getName());
        Thread.sleep(10000);
        return "hello sync";
    }

    @RequestMapping(value = "/callable", method = GET)
    public Callable<String> getAsyncResource() throws InterruptedException {
        logger.info("ThreadName-" + Thread.currentThread().getName());

        return ()->{
                logger.info("ChildThread-" + Thread.currentThread().getName());
                Thread.sleep(10000);
                return "hello callable";
            };

    }

    @RequestMapping(value = "/future", method = GET)
    public CompletableFuture<String> getFutureResource() {
        logger.info("ThreadName-" + Thread.currentThread().getName());

        return CompletableFuture.supplyAsync(() -> {
            logger.info("ChildThread-" + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello future";
        });

    }

    @RequestMapping(value = "/deferred", method = GET)
    public DeferredResult<String> getDeferredResource() {
        logger.info("ThreadName-" + Thread.currentThread().getName());
        DeferredResult<String> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> {
            logger.info("ChildThread-" + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello deferred";
        }).whenCompleteAsync((result,throwable)->deferredResult.setResult(result));

        return deferredResult;

    }

    @Async
    @RequestMapping(value = "/async", method = GET)
    public ListenableFuture<String> getSpringAsyncResource() throws InterruptedException {
        logger.info("ThreadName-" + Thread.currentThread().getName());

        Thread.sleep(10000);
        return new AsyncResult<>("hello async");

    }

}
