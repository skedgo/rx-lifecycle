package com.skedgo.android.rx.util;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.SupportMapFragment;

public abstract class RxMapFragment extends SupportMapFragment {
  @Nullable private Lifecycle lifecycle;

  @Override public void onDestroyView() {
    if (lifecycle != null) {
      lifecycle.dispatchOnDestroyView();
    }

    super.onDestroyView();
  }

  @Override public void onDestroy() {
    if (lifecycle != null) {
      lifecycle.dispatchOnDestroy();
    }

    super.onDestroy();
  }

  public final Lifecycle lifecycle() {
    if (lifecycle == null) {
      lifecycle = Lifecycle.create();
    }

    return lifecycle;
  }
}