package com.daxia.wrapcontentwebview.verticalviewpager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.daxia.wrapcontentwebview.R;

public class ContentFragment extends Fragment implements com.daxia.wrapcontentwebview.verticalviewpager.ScrollWebView.EndCallback {

    private ScrollWebView ScrollWebView;
    private ProgressBar ProgressBar_loading;
    private VerticalViewPagerSingleton VVPSInstance;
    private boolean webviewIsLoading = false;
    private Handler handler;


    public static ContentFragment newInstance(int position, String url) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("url", url);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        VVPSInstance = VerticalViewPagerSingleton.getInstance();
        ProgressBar_loading = (ProgressBar) view.findViewById(R.id.ProgressBar_loading);
        ProgressBar_loading.setVisibility(View.VISIBLE);
        ScrollWebView = (ScrollWebView) view.findViewById(R.id.ScrollWebView);
        ScrollWebView.setVisibility(View.GONE);
        ScrollWebView.setEndCallBack(this);
        ScrollWebView.getSettings().setJavaScriptEnabled(true);
        ScrollWebView.setWebViewClient(new WebClient());
        if (getArguments().getInt("position") > 0) {
            ScrollWebView.loadDataWithBaseURL("", getArguments().getString("url"), "text/html", "utf-8", "");
        } else {
            ScrollWebView.loadUrl(getArguments().getString("url"));
        }
        return view;
    }


    @Override
    public void isEnd() {
        if (VVPSInstance.canLoadMore()) {
            VVPSInstance.getFragmentList().add(ContentFragment.newInstance(
                    VVPSInstance.getFragmentList().size() + 1,
                    createTestHtml(VVPSInstance.getFragmentList().size() + 1)));
            VVPSInstance.getmAdapter().notifyDataSetChanged();
//            Log.e("isEnd", "size = " + VVPSInstance.getFragmentList().size());
        }
    }

    public void ScrollToBottom() {
        if (!ScrollWebView.isEnd()) {
            //To Check webview is loaded
            handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (!webviewIsLoading) {
                        ScrollWebView.loadUrl("javascript:window.scrollTo(0,document.body.scrollHeight)");
                    } else {
                        handler.postDelayed(this, 1000);
                    }
                }
            };
            handler.post(runnable);
        }
    }

    private class WebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            webviewIsLoading = true;
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            ProgressBar_loading.setVisibility(View.GONE);
            ScrollWebView.setVisibility(View.VISIBLE);
            webviewIsLoading = false;
            super.onPageFinished(view, url);
        }
    }

    public String getUrl() {
        return getArguments().getString("url");
    }


    private String createTestHtml(int listPostion) {

        String html = " <html>" +
                "<head>" +
                "<style type=\"text/css\"> " +
                "table{" +
                "  border:5px #dbdbdb solid ; margin:0px 0px 50px 0px;" +
                "}" +
                "table tr:last-child td:first-child {" +
                "    border-bottom-left-radius: 10px;" +
                "}" +
                "" +
                "table tr:last-child td:last-child {" +
                "    border-bottom-right-radius: 10px;" +
                "}" +
                "</style> " +
                "</head>" +
                "<body bgcolor=\"#ff00ff\">";
        for (int i = 0; i < 9; i++) {
            html += " <table width = \"100%\"  rules=\"all\"  >" +
                    "  <tr>" +
                    "    <td width = \"20%\" rowspan=\"2\" style=\"text-align:center;\" >Test</td>" +
                    "    <td width = \"40%\" >Page</td>" +
                    "    <td width = \"20%\" >Postion</td>" +
                    "    <td width = \"20%\" >Page + Postion</td>" +
                    "  </tr>" +
                    "  <tr>" +
                    "    <td width = \"40%\" >" + listPostion + "</td>" +
                    "    <td width = \"20%\" >" + i + "</td>" +
                    "    <td width = \"20%\" >" + listPostion + " , " + i + "</td>" +
                    "  </tr>" +
                    "  " +
                    "</table>";
        }
        html += "</body>" +
                " </html>";
        return html;
    }


}
