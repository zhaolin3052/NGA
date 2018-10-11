package example.lnkj.com.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
public class MainActivity extends AppCompatActivity {
    AgentWeb mAgentWeb;
    private TextView mTitleTextView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleTextView =  findViewById(R.id.toolbar_title);
        FrameLayout frameLayout = findViewById(R.id.fl_context);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent( frameLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go("http://nga.178.com/");

        webView = mAgentWeb.getWebCreator().getWebView();
        webView.setInitialScale(80);

        findViewById(R.id.iv_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgentWeb.back();
            }
        });
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitleTextView.setText(title);
        }
    };
    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            new AlertDialog.Builder(this)
                    .setTitle("提醒")
                    .setMessage("确定退出马桶吗?")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("取消", null)
                    .show();
        }
    }
}
