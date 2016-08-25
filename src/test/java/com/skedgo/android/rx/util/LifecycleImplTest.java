package com.skedgo.android.rx.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LifecycleImplTest {
  private Lifecycle.LifecycleImpl lifecycle;

  @Before public void before() {
    lifecycle = new Lifecycle.LifecycleImpl();
  }

  @Test public void initializeOnDestroyViewSubjectOnDemand() {
    assertThat(lifecycle.onDestroyView).isNull();
    assertThat(lifecycle.onDestroyView()).isNotInstanceOf(PublishSubject.class);
    assertThat(lifecycle.onDestroyView).isNotNull();
  }

  @Test public void initializeOnDestroySubjectOnDemand() {
    assertThat(lifecycle.onDestroy).isNull();
    assertThat(lifecycle.onDestroy()).isNotInstanceOf(PublishSubject.class);
    assertThat(lifecycle.onDestroy).isNotNull();
  }

  @Test public void dispatchNoOnDestroyView() {
    lifecycle.dispatchOnDestroyView();
    assertThat(lifecycle.onDestroyView).isNull();
  }

  @Test public void dispatchNoOnDestroy() {
    lifecycle.dispatchOnDestroy();
    assertThat(lifecycle.onDestroy).isNull();
  }

  @Test public void dispatchOnDestroyView() {
    final TestSubscriber<Void> subscriber = new TestSubscriber<>();
    lifecycle.onDestroyView().subscribe(subscriber);

    subscriber.assertNoValues();
    lifecycle.dispatchOnDestroyView();
    subscriber.assertValue(null);
  }

  @Test public void dispatchOnDestroy() {
    final TestSubscriber<Void> subscriber = new TestSubscriber<>();
    lifecycle.onDestroy().subscribe(subscriber);

    subscriber.assertNoValues();
    lifecycle.dispatchOnDestroy();
    subscriber.assertValue(null);
  }
}