package com.circlenode.adryanekavandra.kppn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.config.ServerConfig;

public class WebViewFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview,container,false);

        getActivity().setTitle("KPPN - WEB");
        swipeRefreshLayout = view.findViewById(R.id.fragment_swipe_refresh_webview);
        webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyBrowser());
        loadWebview();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebview();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //method goback(),untuk kembali ke halaman sebelumnya
            return true;
        }
        // Jika tidak ada halaman yang pernah dibuka
        // maka akan keluar dari activity (tutup aplikasi)
        return onKeyDown(keyCode, event);
    }

    private void loadWebview() {
        webView.loadUrl("http://www.djpbn.kemenkeu.go.id/kppn/pekanbaru/id/");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
