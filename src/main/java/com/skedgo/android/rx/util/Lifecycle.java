package com.skedgo.android.rx.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * A helper class for {@link Fragment} to propagate lifecycle events via {@link Observable}.
 * This is really helpful, for example, to terminate an {@link Observable}
 * before the fragment view is destroyed or the fragment is destroyed.
 */
public abstract class Lifecycle {
  @NonNull public static Lifecycle create() {
    return new LifecycleImpl();
  }

  @NonNull public abstract Observable<Void> onDestroyView();
  @NonNull public abstract Observable<Void> onDestroy();

  /**
   * Should be called at {@link Fragment#onDestroyView()}.
   */
  public abstract void dispatchOnDestroyView();

  /**
   * Should be called at {@link Fragment#onDestroy()}.
   */
  public abstract void dispatchOnDestroy();

  static final class LifecycleImpl extends Lifecycle {
    @Nullable PublishSubject<Void> onDestroyView;
    @Nullable PublishSubject<Void> onDestroy;

    @NonNull @Override public Observable<Void> onDestroyView() {
      if (onDestroyView == null) {
        onDestroyView = PublishSubject.create();
      }

      return onDestroyView.asObservable();
    }

    @NonNull @Override public Observable<Void> onDestroy() {
      if (onDestroy == null) {
        onDestroy = PublishSubject.create();
      }

      return onDestroy.asObservable();
    }

    @Override public void dispatchOnDestroyView() {
      if (onDestroyView != null) {
        onDestroyView.onNext(null);
      }
    }

    @Override public void dispatchOnDestroy() {
      if (onDestroy != null) {
        onDestroy.onNext(null);
      }
    }
  }
}