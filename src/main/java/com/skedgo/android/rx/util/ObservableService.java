package com.skedgo.android.rx.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * A service that is able to attach its life to any {@link Observable}.
 */
public abstract class ObservableService extends Service {
  private final AtomicInteger counter = new AtomicInteger(0);

  /**
   * Should be composed with {@link Observable#doOnRequest(Action1)}.
   */
  protected final Action1<Long> onRequest = new Action1<Long>() {
    @Override public void call(Long unused) {
      counter.incrementAndGet();
    }
  };

  /**
   * Should be composed with {@link Observable#doOnTerminate(Action0)}.
   */
  protected final Action0 onTerminate = new Action0() {
    @Override public void call() {
      if (counter.decrementAndGet() == 0) {
        stopSelf();
      }
    }
  };

  @Nullable @Override public final IBinder onBind(Intent intent) {
    return null;
  }

  @Override public final int onStartCommand(Intent intent, int flags, int startId) {
    final int r = super.onStartCommand(intent, flags, startId);
    if (intent != null) {
      onHandleIntent(intent);
    } else {
      stopSelf();
    }
    return r;
  }

  /**
   * This is where for subclasses to initialize {@link Observable}.
   */
  protected abstract void onHandleIntent(@NonNull Intent intent);
}