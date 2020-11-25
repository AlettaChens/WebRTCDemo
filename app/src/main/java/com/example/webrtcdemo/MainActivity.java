package com.example.webrtcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * 基本概念：
 *
 * RTC(Real Time Communication): 实时通信
 * WebRTC: 基于web的实时通信
 * Signaling: 信令, 一些描述媒体或网络的字符串
 * SDP(Session Description Protocol): 会话描述协议, 主要描述媒体信息
 * ICE(Interactive Connectivity Establishment): 交互式连接建立
 * STUN(Session Traversal Utilities for NAT): NAT会话穿透工具
 * TURN(Traversal Using Relays around NAT): 中继穿透NAT
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_local_camera01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_local_camera01 = findViewById(R.id.btn_local_camera01);
        btn_local_camera01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_local_camera01: {
                startActivity(new Intent(this, WebRTCLocalCamera01Activity.class));
                break;
            }
        }
    }
}
