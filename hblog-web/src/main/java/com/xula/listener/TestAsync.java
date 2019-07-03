package com.xula.listener;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class TestAsync implements Callable<AsyncResult<Object>> {

    @Override
    public AsyncResult<Object> call() throws Exception {

        int sum = 0;
        // do work
        for (int i = 0; i < 10; i++) {
            sum = sum + i;
        }

        if (sum == 45) {
            throw  new Exception("exception ......");
        }

        return new AsyncResult(sum);
    }


    public static void main(String[] args) {
        Executor executor = new ConcurrentTaskExecutor();
        TestAsync testAsync = new TestAsync();
        ListenableFuture result = ((ConcurrentTaskExecutor) executor).submitListenable(testAsync);


        result.addCallback(new ListenableFutureCallback<AsyncResult>() {
            @Override
            public void onSuccess(AsyncResult result) {
                try {
                    System.out.println(result.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
